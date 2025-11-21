package com.modern.tsp.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.config.RedisConfig;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.model.LoginUser;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.tsp.domain.TspEquipment;
import com.modern.tsp.domain.TspEquipmentModel;
import com.modern.tsp.domain.TspEquipmentType;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.mapper.TspEquipmentMapper;
import com.modern.tsp.model.dto.TspEquipmentExcelDTO;
import com.modern.tsp.model.dto.TspEquipmentLikeSelectDTO;
import com.modern.tsp.model.dto.TspEquipmentPageListDTO;
import com.modern.tsp.model.vo.TspEquipmentAddVO;
import com.modern.tsp.model.vo.TspEquipmentPageListVO;
import com.modern.tsp.model.vo.TspEquipmentScrapVO;
import com.modern.tsp.repository.TspEquipmentModelRepository;
import com.modern.tsp.repository.TspEquipmentRepository;
import com.modern.tsp.repository.TspEquipmentTypeRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


/**
 * <p>
 * 摩登 - TSP - 设备 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspEquipmentService extends TspBaseService {
    private final TspEquipmentRepository tspEquipmentRepository;
    private final TspEquipmentMapper tspEquipmentMapper;
    private final TspVehicleRepository tspVehicleRepository;
    private final TspEquipmentTypeRepository tspEquipmentTypeRepository;
    private final TspEquipmentModelRepository tspEquipmentModelRepository;
    private final RedisConnectionFactory redisConnectionFactory;
    RedisConfig redisConfig = new RedisConfig();

    public PageInfo<TspEquipmentPageListDTO> getPageList(TspEquipmentPageListVO vo) {
        QueryWrapper<TspEquipment> listEw = tspEquipmentRepository.getPageListEw(vo);

        IPage<TspEquipmentPageListDTO> pageList = tspEquipmentMapper.getPageList(
                Page.of(vo.getPageNum(), vo.getPageSize()), listEw);

        for (TspEquipmentPageListDTO record : pageList.getRecords()) {
            if (record.getTspVehicleId() == null) {
                record.setBindStatus("未绑定");
            } else {
                record.setBindStatus("已绑定");
            }
            if (record.getTspVehicleVin() != null) {
                String vin = record.getTspVehicleVin();

                // 获取Redis中车辆整车数据
                Map<String, Object> integrateJson = (Map<String, Object>) readListFromCache("VehicleRealtimeData:" + vin, "VehicleIntegrate");
                if (integrateJson != null) {
                    LocalDateTime collectTime = convertToLocalDateTime((JSONArray) integrateJson.get("collectTime"));
                    integrateJson.remove("collectTime");
                    // 获取整车数据
                    List<VehicleIntegrate> vehicleIntegrates = JSONArray.parseArray("[" + integrateJson + "]", VehicleIntegrate.class);
                    if (vehicleIntegrates != null && vehicleIntegrates.size() != 0) {
                        // IsOnline：是否在线1-在线、0-未在线
                        record.setIsOnline(calcVehicleIsOnLine(vehicleIntegrates.get(0), collectTime, LocalDateTime.now()));
                    } else {
                        record.setIsOnline(false);
                    }
                }
            } else {
                record.setIsOnline(false);
            }
        }
        return PageInfo.of(pageList);
    }

    public void add(TspEquipmentAddVO vo) {
        TspEquipment tspEquipment = tspEquipmentRepository.getBySn(vo.getSn());
        if (tspEquipment != null) {
            ErrorEnum.TSP_SN_NOT_UNIQ_ERR.throwErr();
        }
        tspEquipment = tspEquipmentRepository.getByICCID(vo.getIccId());
        if (tspEquipment != null) {
            ErrorEnum.TSP_ICCID_NOT_UNIQ_ERR.throwErr();
        }

        tspEquipment = tspEquipmentRepository.getByIMSI(vo.getImsi());
        if (tspEquipment != null) {
            ErrorEnum.TSP_ISMI_NOT_UNIQ_ERR.throwErr();
        }
        tspEquipment = tspEquipmentRepository.getBySIM(vo.getSim());
        if (tspEquipment != null) {
            ErrorEnum.TSP_SIM_NOT_UNIQ_ERR.throwErr();
        }
        tspEquipment = tspEquipmentRepository.getByIMEI(vo.getImei());
        if (tspEquipment != null) {
            ErrorEnum.TSP_IMEI_NOT_UNIQ_ERR.throwErr();
        }
        tspEquipment = new TspEquipment();
        BeanUtils.copyProperties(vo, tspEquipment);
        tspEquipment.setCreateBy(SecurityUtils.getUsername());
        tspEquipment.setUpdateBy(SecurityUtils.getUsername());
        tspEquipment.setIsScrap(false); //boolean 是否报废1-是、0-否
        tspEquipmentRepository.save(tspEquipment);
    }

    public void edit(TspEquipmentAddVO vo) {
        if (null == vo.getTspEquipmentId()) {
            throw new RuntimeException("设备ID不能为空");
        }
        TspEquipment equipment = tspEquipmentRepository.getById(vo.getTspEquipmentId());
        if (equipment == null) {
            ErrorEnum.TSP_EQUIPMENT_NULL_ERR.throwErr();
        }
        TspEquipment sn = tspEquipmentRepository.getBySn(vo.getSn());
        if (sn != null) {
            if (!sn.getId().equals(equipment.getId())) {
                ErrorEnum.TSP_SN_NOT_UNIQ_ERR.throwErr();
            }
        }
        TspEquipment iccid = tspEquipmentRepository.getByICCID(vo.getIccId());
        if (iccid != null) {
            if (!iccid.getId().equals(equipment.getId())) {
                ErrorEnum.TSP_ICCID_NOT_UNIQ_ERR.throwErr();
            }
        }
        TspEquipment imsi = tspEquipmentRepository.getByIMSI(vo.getImsi());
        if (imsi != null) {
            if (!imsi.getId().equals(equipment.getId())) {
                ErrorEnum.TSP_ISMI_NOT_UNIQ_ERR.throwErr();
            }
        }
        TspEquipment sim = tspEquipmentRepository.getBySIM(vo.getSim());
        if (sim != null) {
            if (!sim.getId().equals(equipment.getId())) {
                ErrorEnum.TSP_SIM_NOT_UNIQ_ERR.throwErr();
            }
        }
        TspEquipment imei = tspEquipmentRepository.getByIMEI(vo.getImei());
        if (imei != null) {
            if (!imei.getId().equals(equipment.getId())) {
                ErrorEnum.TSP_IMEI_NOT_UNIQ_ERR.throwErr();
            }
        }
        BeanUtils.copyProperties(vo, equipment);
        equipment.setUpdateBy(SecurityUtils.getUsername());
        tspEquipmentRepository.updateById(equipment);
    }

    public List<TspEquipmentLikeSelectDTO> likeSelect(Long tspEquipmentId, String modelName) {
        ArrayList<TspEquipmentLikeSelectDTO> dtos = new ArrayList<>();
        tspEquipmentModelRepository.findByTspEquipmentIdLikeName(tspEquipmentId, modelName).forEach(item -> {
            TspEquipmentLikeSelectDTO dto = new TspEquipmentLikeSelectDTO();
            BeanUtils.copyProperties(item, dto);
            dtos.add(dto);
        });
        return dtos;
    }

    /*
    1、如果不配置rollbackFor属性，那么该方法只有在遇到运行时异常（RuntimeException）类型的时候才会回滚。
    2、如果某方法加了@Transactional(rollbackFor=Exception.class)这个注解，那么该方法抛出运行时和非运行时异常（任何Exception异常），都会回滚。
    3、如果某方法加了@Transactional(notRollbackFor=RunTimeException.class)这个注解，那么该方法在运行时异常不会回滚。
    Transactional还是有很多其他参数，如readOnly、timeout、propagation等等
     */
    //任何Exception异常都会回滚
    @Transactional(rollbackFor = RuntimeException.class)
    public void deletes(Long[] tspEquipmentIds) {
        if (tspEquipmentIds.length == 0) {
            ErrorEnum.TSP_EQUIPMENT_NULL_ERR.throwErr();
        }

        // Arrays.asList 它可以将一个数组转换为一个List集合来进行CRUD
        List<TspEquipment> equipments = tspEquipmentRepository.listByIds(Arrays.asList(tspEquipmentIds));
        if (equipments == null) {
            ErrorEnum.TSP_EQUIPMENT_NULL_ERR.throwErr();
        }
        List<TspVehicle> vehicles = tspVehicleRepository.findInTspEquipmentIds(tspEquipmentIds);

        // 设备下存在绑定车辆，无法删除
        if (!CollectionUtils.isEmpty(vehicles)) {
            ErrorEnum.TSP_EQUIPMENT_EQUIPMENT_DELETE_ERR.throwErr();
        }
        // 使用遍历单次删除 代替 批量删除
//        for (Long id : tspEquipmentIds) {
//            boolean result = tspEquipmentRepository.removeById(id);
//            log.info("Deleted {} {}", id, result);
//        }
        tspEquipmentRepository.removeByIds(Arrays.asList(tspEquipmentIds));
    }

    public List<TspEquipmentExcelDTO> exportList(TspEquipmentPageListVO vo) {
        vo.setPageNum(1);
        vo.setPageSize(Integer.MAX_VALUE);
        List<TspEquipmentExcelDTO> dtos = new ArrayList<>();
        PageInfo<TspEquipmentPageListDTO> voList = this.getPageList(vo);
        voList.getList().forEach(item -> {
            TspEquipmentExcelDTO dto = new TspEquipmentExcelDTO();
            BeanUtils.copyProperties(item, dto);
            switch (item.getOperator()) {
                case 1:
                    dto.setOperator("移动");
                    break;
                case 2:
                    dto.setOperator("联通");
                    break;
                case 3:
                    dto.setOperator("电信");
                    break;
                default:
                    dto.setOperator("未知");
            }
            TspEquipmentModel equipmentModel = tspEquipmentModelRepository.getByIdContainsDelete(item.getTspEquipmentModelId());
            if (equipmentModel==null){
                ErrorEnum.TSP_EQUIPMENT_NULL_ERR.throwErr();
            }
            dto.setModelName(equipmentModel.getModelName());
            dtos.add(dto);
        });
        return dtos;
    }

    /*
    MultipartFile是SpringMVC提供简化上传操作的工具类
    @SneakyThrows是来消除频繁的try...catch
     */
    @SneakyThrows
    public String importEquipment(MultipartFile multipartFile, Boolean isUpdateSupport) {
        log.info("导入设备中.......{},{}",multipartFile,isUpdateSupport);
        ExcelUtil<TspEquipmentExcelDTO> util = new ExcelUtil<TspEquipmentExcelDTO>(TspEquipmentExcelDTO.class);
        List<TspEquipmentExcelDTO> dtos = util.importExcel(multipartFile.getInputStream());
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TspEquipmentExcelDTO dto : dtos) {
            try {
                // 数据校验
                Map<String, Object> checkMap = toCheckEquipment(dto, failureMsg, failureNum);
                failureNum = (Integer) checkMap.get("failureNum");
                failureMsg = (StringBuilder) checkMap.get("failureMsg");
                if (failureNum == 0) {
                    TspEquipmentType equipmentType = tspEquipmentTypeRepository.getByNameAndExtraType(dto.getName(), dto.getExtraType());
                    if (equipmentType == null) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、设备分类/设备扩展信息类型 ").append(dto.getName()).append("/").append(dto.getExtraType()).append(" 不存在");
                    } else {
                        TspEquipmentModel equipmentModel = tspEquipmentModelRepository.getByModelNameAndType(dto.getModelName(), equipmentType.getId());
                        if (equipmentModel == null) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、设备型号 ").append(dto.getName()).append(" 不存在");
                        }
                        TspEquipment equipment = tspEquipmentRepository.getByDTO(dto);
                        // 新增
                        if (equipment == null) {
                            equipment = new TspEquipment();
                            BeanUtils.copyProperties(dto, equipment);
                            if ("电信".equals(dto.getOperator())) {
                                equipment.setOperator(3);
                            }
                            if ("移动".equals(dto.getOperator())) {
                                equipment.setOperator(1);
                            }
                            if ("联通".equals(dto.getOperator())) {
                                equipment.setOperator(2);
                            }
                            equipment.setCreateBy(SecurityUtils.getUsername());
                            equipment.setUpdateBy(SecurityUtils.getUsername());
                            equipment.setTspEquipmentModelId(equipmentModel.getId());
                            equipment.setIsScrap(false);
                            equipment.setIsTerminal(true);
                            tspEquipmentRepository.save(equipment);
                            successNum++;
                            successMsg.append("<br/>").append(successNum).append("、设备 ").append(dto.getSn()).append(" 导入成功");
                        } else {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、设备信息已被使用 ");
                        }
                    }
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、设备 " + dto.getName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    private Map<String, Object> toCheckEquipment(TspEquipmentExcelDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        // sn
        String snRegexp = "^.{1,24}$";
        if (dto.getSn() == null || !dto.getSn().matches(snRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备sn ").append(dto.getSn()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 版本号
        String versionRegexp = "^([0-9A-Za-z]){1,10}(-([0-9A-Za-z]){1,10}){2}$";
        if (dto.getVersion() == null || !dto.getVersion().matches(versionRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备版本号 ").append(dto.getVersion()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // ICCID
        String iccidRegexp = "^([A-Za-z0-9]){19}$";
        if (dto.getIccId() == null || !dto.getIccId().matches(iccidRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备ICCID ").append(dto.getIccId()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // IMSI
        String imsiRegexp = "^([0-9]){1,15}$";
        if (dto.getImsi() == null || !dto.getImsi().matches(imsiRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备IMSI ").append(dto.getImsi()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // SIM
        String simRegexp = "^([0-9]){11}$";
        if (dto.getSim() == null || !dto.getSim().matches(simRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备SIM ").append(dto.getSim()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // IMEI
        String imeiRegexp = "^([0-9]){15}$";
        if (dto.getImei() == null || !dto.getImei().matches(imeiRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备IMEI ").append(dto.getImei()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 批次流水号
        if (dto.getSerialNumber() == null || !dto.getSerialNumber().matches(snRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、批次流水号 ").append(dto.getSerialNumber()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 供应商代码
        if (dto.getSupplierCode() == null || "".equals(dto.getSupplierCode())) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、供应商代码 ").append(dto.getSupplierCode()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 运营商
        if (dto.getOperator() == null || ((!dto.getOperator().equals("移动") && !dto.getOperator().equals("联通")) && !dto.getOperator().equals("电信"))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车联网卡运营商 ").append(dto.getOperator()).append(" 值异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", failureNum);
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

    /**
     * 设备报废
     *
     * @param vo
     */
    public void scrap(TspEquipmentScrapVO vo) {
        // 先得到用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 判断密码是否输入正确
        if (!SecurityUtils.matchesPassword(vo.getPassword(), loginUser.getPassword())) {
            ErrorEnum.TSP_VEHICLE_SCRAP_ERR.throwErr();
        }

        for (Long tspEquipmentId : vo.getTspEquipmentIds()) {
            //设备信息
            TspEquipment tspEquipment = tspEquipmentRepository.getById(tspEquipmentId);
            //车辆列表信息
            List<TspVehicle> tspVehicle = tspVehicleRepository.findByTspEquipmentId(tspEquipmentId);
            if (tspVehicle != null && tspVehicle.size() != 0) {
                ErrorEnum.TSP_EQUIPMENT_SCRAP_ERR.throwErr();
            }
            //设备报废时间（当前时间）
            tspEquipment.setScrapTime(LocalDateTime.now());
            //是否报废1-是、0-否"  true：报废
            tspEquipment.setIsScrap(true);
            tspEquipment.setUpdateBy(SecurityUtils.getUsername());
            tspEquipmentRepository.updateById(tspEquipment);
        }
    }

    private Boolean calcVehicleIsOnLine(VehicleIntegrate vehicleIntegrate, LocalDateTime collectTime, LocalDateTime now) {
        if (vehicleIntegrate != null && collectTime != null) {
            Duration duration = Duration.between(collectTime, now);
            //持续时间的毫秒数，可能是负数。
            if (duration.toMillis() > (1000 * 60)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private <T> Object readListFromCache(String key, String hashKey) {
        try {
            Object o = redisConfig.redisTemplate(redisConnectionFactory).opsForHash().get(key, hashKey);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static LocalDateTime convertToLocalDateTime(JSONArray collectTimeJSONArray) {
        log.info("转换本地时间......{}",collectTimeJSONArray);
        try {

            /*
            JSONArray
            JSONArray就是["tom","kate","jerry"];或者[1,2,3]
            同时,这里的数组json通过添加对象json可以变成数组对象json:
            [{"name":"tom"},{"name":"kate"}]
             */
            int h = collectTimeJSONArray.size() > 3 ? (int) collectTimeJSONArray.get(3) : 0;
            int m = collectTimeJSONArray.size() > 4 ? (int) collectTimeJSONArray.get(4) : 0;
            int s = collectTimeJSONArray.size() > 5 ? (int) collectTimeJSONArray.get(5) : 0;
            LocalDateTime collectTime = LocalDateTime.of((int) collectTimeJSONArray.get(0), Month.of((int) collectTimeJSONArray.get(1)), (int) collectTimeJSONArray.get(2),
                    h, m, s, 0);

            return collectTime;
        } catch (Exception e) {
            e.printStackTrace();

            //返回开始时间
            return LocalDateTime.MIN;
        }
    }
}
