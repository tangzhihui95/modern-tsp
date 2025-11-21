package com.modern.tsp.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.config.RedisConfig;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.model.LoginUser;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.http.HttpUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.exInterface.cache.VehicleRedisCache;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.exInterface.entity.command.VehicleCommand;
import com.modern.exInterface.repository.VehicleAlertRepository;
import com.modern.tsp.domain.*;
import com.modern.tsp.enums.TspVehicleStateEnum;
import com.modern.tsp.mapper.*;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.*;
import com.modern.tsp.repository.*;
import com.modern.web.mapper.system.SysUserRoleMapper;
import com.modern.xtsp.service.XTspVehicleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 摩登 - TSP - 车辆信息 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@SuppressWarnings("SuspiciousIndentAfterControlStatement")
@Slf4j
@Service
@PropertySource(value = {"classpath:application.yml"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleService extends TspBaseService {

    private final TspVehicleMapper tspVehicleMapper;

    private final TspUserRepository tspUserRepository;

    private final TspVehicleLicenseRecordService tspVehicleLicenseRecordService;

    private final TspUseVehicleRecordService tspUseVehicleRecordService;

    private final TspEquipmentRepository tspEquipmentRepository;

    private final TspVehicleAuditService tspVehicleAuditService;

    private final TspVehicleAuditRepository tspVehicleAuditRepository;

    private final TspVehicleRepository tspVehicleRepository;

    private final TspVehicleMarketRepository tspVehicleMarketRepository;

    private final TspVehicleLicenseRepository tspVehicleLicenseRepository;

    private final TspVehicleStdModeRepository tspVehicleStdModeRepository;

    private final TspVehicleModelRepository tspVehicleModelRepository;

    private final VehicleAlertRepository vehicleAlertRepository;

    private final TspVehicleLicenseRecordRepository tspVehicleLicenseRecordRepository;

    private final TspVehicleIdentificationService tspVehicleIdentificationService;

    private final TspVehicleOtherRepository tspVehicleOtherRepository;

    private final TspVehicleEquipmentRepository tspVehicleEquipmentRepository;

    private final TspVehicleEquipmentMapper tspVehicleEquipmentMapper;

    private final TspUserVehicleRepository tspUserVehicleRepository;

    private final TspVehicleStdModeMapper tspVehicleStdModeMapper;

    private final TspTagRepository tspTagRepository;

    private final TspDealerMapper tspDealerMapper;

    private final TspEquipmentMapper tspEquipmentMapper;

    private final RedisConnectionFactory redisConnectionFactory;

    private final VehicleRedisCache vehicleRedisCache;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final XTspVehicleService xTspVehicleService;

    private final TspVehicleIdentificationReceiveRepository tspvehicleIdentificationReceiveRepository;

    RedisConfig redisConfig = new RedisConfig();

    @Value("${my.command-server-http-address}")
    private String commandServerHttpAddress;

    private static LocalDateTime convertToLocalDateTime(JSONArray collectTimeJSONArray) {
        try {
            int h = collectTimeJSONArray.size() > 3 ? (int) collectTimeJSONArray.get(3) : 0;
            int m = collectTimeJSONArray.size() > 4 ? (int) collectTimeJSONArray.get(4) : 0;
            int s = collectTimeJSONArray.size() > 5 ? (int) collectTimeJSONArray.get(5) : 0;
            LocalDateTime collectTime = LocalDateTime.of((int) collectTimeJSONArray.get(0), Month.of((int) collectTimeJSONArray.get(1)), (int) collectTimeJSONArray.get(2),
                    h, m, s, 0);

            return collectTime;
        } catch (Exception e) {
            e.printStackTrace();

            return LocalDateTime.MIN;
        }
    }

    //    @DataScope(userAlias = "sysu",deptAlias = "sysd")
    public PageInfo<TspVehiclePageListDTO> getPageList(TspVehiclePageListVO vo) {
        if (!SecurityUtils.isAdmin()) {
            List<TspVehicle> allVehiclesByCurrentUser = xTspVehicleService.getAllVehiclesByCurrentUser();
            List<Long> vehicleIds = allVehiclesByCurrentUser.stream().map(s -> s.getId()).collect(Collectors.toList());
            vo.setCarIds(vehicleIds);
        }

        // 查询绑定设备的所有信息 包括（车辆信息）（条件是不是报废，也没删除（车辆和设备））
        List<Long> bindEquipmentIds = tspEquipmentMapper.getBindEquipments();

        // 将绑定设备信息多个id赋值到TspEquipmentIds属性中（list）
        vo.setTspEquipmentIds(bindEquipmentIds);

        QueryWrapper<TspVehicle> ew = tspVehicleRepository.getPageListEw(vo);
        Page<TspVehiclePageListDTO> p = Page.of(vo.getPageNum(), vo.getPageSize());
        p.setOptimizeCountSql(false);  //关闭分页查询优化，否则复杂查询语句可能导致分页查询的Total与实际总数不一致
        //获取分页数据 getPageNum(), vo.getPageSize()), ew
        IPage<TspVehiclePageListDTO> page = tspVehicleMapper.getPageList(p, ew);
        for (TspVehiclePageListDTO record : page.getRecords()) {

            TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(record.getId());
            if (license == null) {
                license = tspVehicleLicenseRepository.getByPlateCode(record.getPlateCode());
            }
            if (license != null) {
                record.setPlateCode(license.getPlateCode() == null ? null : license.getPlateCode());
            }
            TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(record.getTspVehicleStdModelId());
            if (stdModel != null) {
                record.setStdModeName(stdModel.getStdModeName() == null ? null : stdModel.getStdModeName());
            }

            TspVehicleIdentificationReceive tspVehicleIdentificationReceive = tspvehicleIdentificationReceiveRepository.getByVin(record.getVin());
            if (tspVehicleIdentificationReceive != null && tspVehicleIdentificationReceive.getStatus().equals("true")) {
                record.setIdentificationStatus("已实名认证");
            } else {
                record.setIdentificationStatus("未实名认证");
            }
//            TspEquipment tspEquipment = new TspEquipment();
//            // 根据record的id 返回 车辆信息
//            List<TspVehicle> vehicleList = tspVehicleRepository.findByTspEquipmentId(tspEquipment.getId());
//            // 车辆数据不存在，未绑定！！！！！
//            if (vehicleList.size() == 0 || vehicleList == null ) {
//                record.setBindStatus("未绑定");
//                // 否则 已绑定！！！！！
//            } else {
//                record.setBindStatus("已绑定");
//            }

//            List<TspVehicle> vehicleList = tspVehicleRepository.findByTspEquipmentId(record.getId());
            if (Objects.isNull(record.getTspEquipmentId())) {
                record.setBindStatus("未绑定");
            } else {
                record.setBindStatus("已绑定");
            }

        }
        return PageInfo.of(page);
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    @Transactional(rollbackFor = ServiceException.class)
    public TspVehicleInfoDTO add(TspVehicleAddVO vo) {
        //        TspVehicle tspVehicle = tspVehicleRepository.getByConfigureName(vo.getConfigureName());
        //        if (tspVehicle != null) {
        //            ErrorEnum.TSP_VEHICLE_VEHICLE_CONFIGURE_NAME_NOT_NULL_ERR.throwErr();
        //        }

        //
        //        TspEquipment tspEquipment = tspEquipmentRepository.getBySn(vo.getSn());
        //        if (tspEquipment != null) {
        //            ErrorEnum.TSP_SN_NOT_UNIQ_ERR.throwErr();
        //        }
        //        tspEquipment = tspEquipmentRepository.getByICCID(vo.getIccId());
        //        if (tspEquipment != null) {
        //            ErrorEnum.TSP_ICCID_NOT_UNIQ_ERR.throwErr();
        //        }
        log.debug("新增车辆中.....");
        log.debug("TspVehicleAddVO {}", vo);
        TspVehicle tspVehicle = tspVehicleRepository.getByVin(vo.getVin());
        if (tspVehicle != null) {
            ErrorEnum.TSP_VEHICLE_VIN_EXIST.throwErr();
        }

        //查看车辆已绑定的设备
        if (vo.getTspEquipmentId() != null) {
            Long tspVehicleId = tspVehicleMapper.getByEquipmentId(vo.getTspEquipmentId());
            if (tspVehicleId != null) {
                ErrorEnum.TSP_VEHICLE_EQUIPMENT_EXIST.throwErr();
            }
        }

        //检查销售信息
        tspVehicleRepository.isProgressCheck(vo);
        //检查用户信息
        tspVehicleRepository.isUserCheck(vo);

        //给车辆属性赋值  但最终要再domain类里面 所以需要同名属性转换
        tspVehicle = new TspVehicle();
        tspVehicle.setUpdateBy(SecurityUtils.getUsername());
        tspVehicle.setCreateBy(SecurityUtils.getUsername());
        tspVehicle.setState(TspVehicleStateEnum.CREATED);
        BeanUtils.copyProperties(vo, tspVehicle);

        //判断标签是否为null，不为null且不等于0  把每个标签id循环遍历 根据id查询标签信息
        if (vo.getLabel() != null && vo.getLabel().size() != 0) {
            for (Long tspTagId : vo.getLabel()) {
                TspTag tspTag = tspTagRepository.getById(tspTagId);

                //把标签管理对象set关联数量赋值   传入：关联的数量+1
                tspTag.setTagCount(tspTag.getTagCount() + 1);
                //修改标签数量
                tspTagRepository.updateById(tspTag);
            }
            //车辆中set标签
            tspVehicle.setLabel(vo.getLabel().toString());
        }

        //新增车辆信息
        tspVehicleRepository.save(tspVehicle);

        //车狼信息记录表
        if (tspVehicle.getTspEquipmentId() != null && tspVehicle.getId() != null) {
            TspVehicleEquipment vehicleEquipment = new TspVehicleEquipment();
            vehicleEquipment.setTspVehicleId(tspVehicle.getId());
            vehicleEquipment.setTspEquipmentId(tspVehicle.getTspEquipmentId());
            //新增完毕
            tspVehicleEquipmentRepository.save(vehicleEquipment);
        }


        // 车牌注册判断
        TspVehicleLicense tspVehicleLicense = tspVehicleLicenseRepository.getByPlateCode(vo.getPlateCode());
        if (tspVehicleLicense != null) {
            // 已注册 抛出异常
            ErrorEnum.TSP_VEHICLE_PLATE_CODE_EXIST.throwErr();
        } else {

            // 未注册 生成上牌记录
            TspVehicleLicenseRecordAddVO recordAddVO = new TspVehicleLicenseRecordAddVO();
            BeanUtils.copyProperties(vo, recordAddVO);
            recordAddVO.setTspVehicleId(tspVehicle.getId());
            //                    recordAddVO.setByTspUserId(tspUserId);
            log.debug("生成上牌记录");
            tspVehicleLicenseRecordService.add(recordAddVO);
//            TspVehicleLicense license = new TspVehicleLicense();
//            BeanUtils.copyProperties(vo, license);
//            license.setPlateCode(vo.getPlateCode());
//           tspVehicleLicenseRepository.save(license);
        }


        //同名属性转换
        TspVehicleInfoDTO dto = new TspVehicleInfoDTO();
        BeanUtils.copyProperties(tspVehicle, dto);
        //记得返回！！！！！
        return dto;
        // TODO 生成出入库记录，暂定
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void edit(TspVehicleAddVO vo) {
        log.info("车辆编辑中......");
        log.info("spVehicleAddVO {}", vo);
        //没有获取到车辆id就抛出异常 车辆不存在 请检查
        if (vo.getTspVehicleId() == null) {
            //车辆id为null抛出异常
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }
        //查询车绑定的设备的车辆信息
        Long tspVehicleId = tspVehicleMapper.getByEquipmentId(vo.getTspEquipmentId());

        //该设备被其他车辆绑定的条件：1.车绑定的设备必须是存在的 2.请求的车辆id与车辆设备绑定的车狼信息id不同
        if (tspVehicleId != null && !vo.getTspVehicleId().equals(tspVehicleId)) {
            ErrorEnum.TSP_VEHICLE_EQUIPMENT_EXIST.throwErr();
        }
        //车辆不存在，请检查！！！
        TspVehicle vehicle = tspVehicleRepository.getById(vo.getTspVehicleId());
        if (vehicle == null) {
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }

        //获取经销商id 暂时注释
        //        if(Objects.isNull(vo.getDealerId())&&!vo.getDealerId().equals(vehicle.getDealerId())){
        //            vehicle.setDealerId(vo.getDealerId());
        //        }

        //车辆信息记录表的信息
        List<TspVehicleEquipment> tspEquipmentIds = tspVehicleEquipmentRepository.getByVehicleId(vehicle.getId());
        // 最基本的条件：设备id和设备记录一定存在
        if (vo.getTspEquipmentId() != null && (tspEquipmentIds != null && tspEquipmentIds.size() != 0)) {
            //必须是已推送
            if (vehicle.getSendStatus() == 1) {

                //请求的设备id一定不能和第一个设备的id相同
                if (!vo.getTspEquipmentId().equals(tspEquipmentIds.get(0).getTspEquipmentId())) {

                    //一定是这车是绑定设备的了
                    if (vehicle.getTspEquipmentId() != null) {
                        TspVehicleEquipment update = tspEquipmentIds.get(0);
                        //解绑时间为当前时间
                        update.setUnBindTime(LocalDateUtils.getCurrentTime());
                        tspVehicleEquipmentRepository.updateById(update);
                    }
                }
            }
        }


        //请求的设备一定存在并且记录是不存在或者解绑时间必须存在
        if (vo.getTspEquipmentId() != null && (tspEquipmentIds == null || tspEquipmentIds.size() == 0 || tspEquipmentIds.get(0).getUnBindTime() != null)) {

            // 根据车辆id 从车辆设备对象set车辆id值  前端传入的设备id set到车辆设备对象的设备id里面
            TspVehicleEquipment tspVehicleEquipment = new TspVehicleEquipment();
            tspVehicleEquipment.setTspVehicleId(vehicle.getId());
            tspVehicleEquipment.setTspEquipmentId(vo.getTspEquipmentId());
            tspVehicleEquipmentRepository.save(tspVehicleEquipment);
        }
        // 得到标签id
        String labelStr = vehicle.getLabel();
        //标签必须存在并且不能是"" 和 []
        if ((labelStr != null && !"".equals(labelStr)) && !"[]".equals(labelStr)) {
            List<String> strings = Arrays.asList(labelStr.split(","));

            for (String string : strings) {
                if (string.contains("[")) {
                    string = string.replace("[", "");
                }
                if (string.contains("]")) {
                    string = string.replace("]", "");
                }
                if (string.contains(" ")) {
                    string = string.replace(" ", "");
                }
                // 将原来的标签绑定值全部减一全部
                Long tagId = Long.valueOf(string);
                TspTag oldTag = tspTagRepository.getById(tagId);
                oldTag.setTagCount(oldTag.getTagCount() - 1);
                oldTag.setUpdateBy(SecurityUtils.getUsername());
                tspTagRepository.updateById(oldTag);
            }
        }
        List<Long> newLabelList = vo.getLabel();

        //请求的标签必须存在 将标签循环的赋值
        if (newLabelList != null && newLabelList.size() != 0) {

            for (Long newLabel : newLabelList) {
                TspTag newTag = tspTagRepository.getById(newLabel);
                newTag.setTagCount(newTag.getTagCount() + 1);
                newTag.setUpdateBy(SecurityUtils.getUsername());
                tspTagRepository.updateById(newTag);
            }
        }

        //出入库记录
        if (vehicle.getProgress() >= 5) {
            vo.setProgress(5);
            vo.setIsComplete(true);
        }
        //验证销售和用户信息不能为null
        tspVehicleRepository.isProgressCheck(vo);
        tspVehicleRepository.isUserCheck(vo);
        BeanUtils.copyProperties(vo, vehicle);
        vehicle.setLabel(vo.getLabel() == null ? null : vo.getLabel().toString());
        switch (vo.getProgress()) {
            case 2:
                //车辆销售信息
                TspVehicleMarket market = tspVehicleMarketRepository.getByTspVehicleId(vo.getTspVehicleId());
                //车辆推送补充信息
                TspVehicleOther other = tspVehicleOtherRepository.getByTspVehicleId(vo.getTspVehicleId());
                if (market != null) {
                    //状态1-已创建、2-已销售、3-已绑定、4-已解绑、5-已报废、6-已注册
                    //在已创建的情况下 才能为销售
                    if (vehicle.getState().getValue() < TspVehicleStateEnum.SOLD.getValue()) {
                        vehicle.setState(TspVehicleStateEnum.SOLD);
                    }
                    BeanUtils.copyProperties(vo, market);
                    market.setUpdateBy(SecurityUtils.getUsername());
                    tspVehicleMarketRepository.updateById(market);
                } else {
                    market = new TspVehicleMarket();
                    BeanUtils.copyProperties(vo, market);
                    market.setCreateBy(SecurityUtils.getUsername());
                    market.setUpdateBy(SecurityUtils.getUsername());
                    tspVehicleMarketRepository.save(market);
                }
                if (other != null) {
                    BeanUtils.copyProperties(vo, other);
                    other.setUpdateBy(SecurityUtils.getUsername());
                    tspVehicleOtherRepository.updateById(other);
                } else {
                    other = new TspVehicleOther();
                    BeanUtils.copyProperties(vo, other);
                    other.setCreateBy(SecurityUtils.getUsername());
                    other.setUpdateBy(SecurityUtils.getUsername());
                    tspVehicleOtherRepository.save(other);
                }
                break;
            case 3:
                TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(vo.getTspVehicleId());
                String placeCode;
                placeCode = StringUtils.isBlank(vo.getPlateCodeName()) ? "" : vo.getPlateCodeName();
                placeCode += (StringUtils.isBlank(vo.getPlateCode()) ? "" : vo.getPlateCode());
                placeCode = StringUtils.isBlank(placeCode) ? null : placeCode;
                //拿到当前入参的车牌信息和车辆ID信息
//                plate_code == placeCode and tsp_vehicle_id != vo.getTspVehicleId()
                //查询
                List<TspVehicleLicense> existLicenseList = tspVehicleLicenseRepository
                        .lambdaQuery()
                        .ne(TspVehicleLicense::getTspVehicleId, vo.getTspVehicleId())
                        .eq(TspVehicleLicense::getPlateCode, placeCode)
                        .list();


                //如果查询出数据，代表当前车牌重复了。
                if (placeCode != null && CollectionUtil.isNotEmpty(existLicenseList)) {
                    ErrorEnum.TSP_VEHICLE_PLATE_CODE_EXIST.throwErr();
                }

                if (license != null) {
                    BeanUtils.copyProperties(vo, license);
                    license.setUpdateBy(SecurityUtils.getUsername());
//                    (StringUtils.isBlank(vo.getPlateCodeName())?"":vo.getPlateCodeName()) +
//                            (StringUtils.isBlank(license.getPlateCode())?"":license.getPlateCode())
                    license.setPlateCode(placeCode);
                    tspVehicleLicenseRepository.saveOrUpdate(license);
                    // 生成上牌记录
                    TspVehicleLicenseRecordAddVO recordAddVO = new TspVehicleLicenseRecordAddVO();
                    BeanUtils.copyProperties(vo, recordAddVO);
                    //                    recordAddVO.setByTspUserId(tspUserId);
                    tspVehicleLicenseRecordService.add(recordAddVO);
                } else {
                    license = new TspVehicleLicense();
                    BeanUtils.copyProperties(vo, license);
                    license.setUpdateBy(SecurityUtils.getUsername());
                    license.setCreateBy(SecurityUtils.getUsername());
                    license.setPlateCode(placeCode);
                    tspVehicleLicenseRepository.save(license);
                    // 生成上牌记录
                    TspVehicleLicenseRecordAddVO recordAddVO = new TspVehicleLicenseRecordAddVO();
                    BeanUtils.copyProperties(vo, recordAddVO);
                    tspVehicleLicenseRecordService.add(recordAddVO);
                }
                break;
            case 4:
//                TspUser byMobile = tspUserRepository.getByMobile(vo.getMobile());
                // 调用实名认证
                //                TspVehicleIdentificationVO identificationVO = new TspVehicleIdentificationVO();
                //                identificationVO.setTspVehicleId(vehicle.getId());
                //                Map<String, Object> resultMap = tspVehicleIdentificationService.getRealName(identificationVO);
                //                Object code = resultMap.get("Code");
                //                if (code == null) {
                //                    ErrorEnum.IDENTIFICATION_FAIL_ERROR.throwErr();
                //                }
                //                if ("510".equals(code)) {
                //                    ErrorEnum.IDENTIFICATION_510_ERROR.throwErr();
                //                }
                //                if ("520".equals(code)) {
                //                    ErrorEnum.IDENTIFICATION_520_ERROR.throwErr();
                //                }
                //                if ("530".equals(code)) {
                //                    ErrorEnum.IDENTIFICATION_ICCID_ERROR.throwErr();
                //                }
                //                if ("200".equals(code)) {
                //                    // 实名认证结果集
                //                    List<Map<String,Object>> resultList = (List<Map<String, Object>>) resultMap.get("ResultList");
                //                    // 填写的真实姓名
                //                    String realName = vo.getRealName();
                //                    // 填写的身份证号
                //                    String idCard = vo.getIdCard();
                //                    Map<String, Object> map = resultList.get(0);
                //                    Object authResult = map.get("AuthResult");
                //                    if (authResult != null && "0".equals(String.valueOf(authResult))) {
                //                        ErrorEnum.IDENTIFICATION_NOT_EXIST_ERROR.throwErr();
                //                    }
                //                    // 实名结果
                //                    String certNum = (String) map.get("CertNum");
                //                    String partyName = (String) map.get("PartyName");
                ////                    String certNum = "43018120001028000X";
                ////                    String partyName = "彭新元";
                //                    // 名称与认证结果不符
                //                    if (!partyName.equals(realName)) {
                //                        ErrorEnum.IDENTIFICATION_NAME_ERROR.throwErr();
                //                    }
                //                    // 身份证与认证结果不符
                //                    if (!certNum.equals(idCard)) {
                //                        ErrorEnum.IDENTIFICATION_CARD_ERROR.throwErr();
                //                    }
                // 身份证和姓名全部认证成功
                //                    if (certNum.equals(idCard) && partyName.equals(realName)) {
//                if (byMobile == null) {

//                } else {
//                    // 更新用户信息
//                    TspUserAddVO userAddVO = new TspUserAddVO();
//                    BeanUtils.copyProperties(vo, userAddVO);
//                    userAddVO.setTspUserId(byMobile.getId());
//                    tspUserService.edit(userAddVO);
//                }
                TspUser byMobile = new TspUser();
                BeanUtils.copyProperties(vo, byMobile);
                TspUser one = tspUserRepository.lambdaQuery().orderByDesc(TspUser::getCreateTime).last("limit 1").one();
                if (one == null || !Objects.equals(one.getRealName(), byMobile.getRealName()) || !Objects.equals(one.getMobile(), byMobile.getMobile()) || !Objects.equals(one.getIdCard(), byMobile.getIdCard())) {
                    tspUserRepository.save(byMobile);
                } else {
                    byMobile.setId(one.getId());
                }
                //                if (tspVehicle.getCurrentBindTime() == null) {
//                if (tspVehicle.getTspUserId() == null || !tspVehicle.getTspUserId().equals(byMobile.getId())) {
                // 生成审核信息
                TspVehicleAudit vehicleAudit = tspVehicleAuditRepository.getById(vo.getTspVehicleAuditId());
                if (vehicleAudit == null) {
                    vehicleAudit = new TspVehicleAudit();
                    BeanUtils.copyProperties(vo, vehicleAudit);
                    vehicleAudit.setTspUserId(byMobile.getId());
                    vehicleAudit.setApplyTime(LocalDateUtils.getCurrentTime());
                    vehicleAudit.setCreateBy(SecurityUtils.getUsername());
                    vehicleAudit.setUpdateBy(SecurityUtils.getUsername());
                    tspVehicleAuditRepository.save(vehicleAudit);
                } else {
                    BeanUtils.copyProperties(vo, vehicleAudit);
                    vehicleAudit.setUpdateBy(SecurityUtils.getUsername());
                    tspVehicleAuditRepository.updateById(vehicleAudit);
                }
                TspUseVehicleRecordAddVO vehicleRecordAddVO = new TspUseVehicleRecordAddVO();
                BeanUtils.copyProperties(vo, vehicleRecordAddVO);
                // 生成绑定记录
                vehicleRecordAddVO.setTspUserId(byMobile.getId());
                if (Objects.isNull(one) || !Objects.equals(one.getId(), byMobile.getId())) {
                    tspUseVehicleRecordService.add(vehicleRecordAddVO);
                }
                if (vehicle.getState().getValue() < TspVehicleStateEnum.BOUND.getValue()) {
                    vehicle.setState(TspVehicleStateEnum.BOUND);
                }
                vehicle.setTspUserId(byMobile.getId());
                vehicle.setIsComplete(true);
                vehicle.setUpdateBy(SecurityUtils.getUsername());
                vehicle.setCurrentBindTime(LocalDateUtils.getCurrentTime());
                tspVehicleRepository.updateById(vehicle);
//                }
                //                }
                //                // 如果车辆与用户绑定过，则不新增绑定记录
                //                TspUserVehicle tspUserVehicle = tspUserVehicleRepository.getByUserVehicle(byMobile.getId(), vehicle.getId());
                //                if (tspUserVehicle == null) {
                //                    tspUserVehicle = new TspUserVehicle();
                //                    tspUserVehicle.setTspVehicleId(vehicle.getId());
                //                    tspUserVehicle.setTspUserId(byMobile.getId());
                //                    tspUserVehicle.setCreateBy(SecurityUtils.getUsername());
                //                    tspUserVehicle.setUpdateBy(SecurityUtils.getUsername());
                //                    tspUserVehicleRepository.save(tspUserVehicle);
                //                }
                //                    }
                //                }
                break;
            default:
                break;

        }
        tspVehicleRepository.updateById(vehicle);
        // TODO 生成出入库记录，暂定
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long tspVehicleId) {
        log.info("根据车辆id正在进行删除中......");
        log.info("tspVehicleId {}", tspVehicleId);
        //从mybatis-plus框架 通过getById的方法  比如以下通过传入车辆id可以获取到车辆信息
        TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);

        ////车辆不存在，请检查！！！！！！！
        if (tspVehicle == null) {
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }

        Long equipmentId = tspVehicleMapper.getByVehicleId(tspVehicleId);

        if (StringUtils.isNotNull(equipmentId)) {
            ErrorEnum.TSP_VEHICLE_EQUIPMENT_BIND_DELETE_ERR.throwErr();
        }

        if (!Objects.equals(TspVehicleStateEnum.ALL.getValue(), tspVehicle.getState().getValue())) {
            if (
                    Objects.equals(TspVehicleStateEnum.SOLD.getValue(), tspVehicle.getState().getValue()) ||
                            Objects.equals(TspVehicleStateEnum.BOUND.getValue(), tspVehicle.getState().getValue()) ||
                            Objects.equals(TspVehicleStateEnum.SCRAPPED.getValue(), tspVehicle.getState().getValue()) ||
                            Objects.equals(TspVehicleStateEnum.ALREADY.getValue(), tspVehicle.getState().getValue())

            ) {
                ErrorEnum.TSP_VEHICLE_DELETE_STATE_ERR.throwErr();
            }

        }
        //删除之前进行审查
        //通过车辆id来获取车辆审查表中的信息
        TspVehicleAudit vehicleAudit = tspVehicleAuditService.getByTspVehicleId(tspVehicleId);
        //如果审查信息不为null，那么根据审查表的车辆id进行删除
        if (vehicleAudit != null) {
            tspVehicleAuditRepository.removeById(vehicleAudit);
        }
        //从车辆表中根据车辆id删除
        tspVehicleRepository.removeById(tspVehicleId);

        //从车辆许可证表中的车辆id获取信息
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicle.getId());
        //不为null，那么根据车辆许可证表中的许可证id进行删除
        if (license != null) {
            tspVehicleLicenseRepository.removeById(license.getId());
        }
        // 得到标签id
        String labelStr = tspVehicle.getLabel();
        if ((labelStr != null && !"".equals(labelStr)) && !"[]".equals(labelStr)) {
            List<String> strings = Arrays.asList(labelStr.split(","));
            for (String string : strings) {
                if (string.contains("[")) {
                    string = string.replace("[", "");
                }
                if (string.contains("]")) {
                    string = string.replace("]", "");
                }
                if (string.contains(" ")) {
                    string = string.replace(" ", "");
                }
                // 将原来的标签绑定值全部减一全部
                Long tagId = Long.valueOf(string);
                TspTag oldTag = tspTagRepository.getById(tagId);
                oldTag.setTagCount(oldTag.getTagCount() - 1);
                oldTag.setUpdateBy(SecurityUtils.getUsername());
                tspTagRepository.updateById(oldTag);
            }
        }
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void deletes(Long[] tspVehicleIds) {
        log.info("车辆批量删除中....");
        log.info("tspVehicleIds {}", tspVehicleIds);

        //循环所有车辆ids
        for (Long tspVehicleId : tspVehicleIds) {
            //将id 传入到tsp车辆表中 进行select获取
            TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);
            //为null,车辆不存在，请检查！！！！！！
            if (tspVehicle == null) {
                ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
            }

            Long equipmentId = tspVehicleMapper.getByVehicleId(tspVehicleId);

            if (StringUtils.isNotNull(equipmentId)) {
                ErrorEnum.TSP_VEHICLE_EQUIPMENT_BIND_DELETES_ERR.throwErr();
            }


            if (!Objects.equals(TspVehicleStateEnum.ALL.getValue(), tspVehicle.getState().getValue())) {
                if (
                        Objects.equals(TspVehicleStateEnum.CREATED.getValue(), tspVehicle.getState().getValue()) ||
                                Objects.equals(TspVehicleStateEnum.SOLD.getValue(), tspVehicle.getState().getValue()) ||
                                Objects.equals(TspVehicleStateEnum.BOUND.getValue(), tspVehicle.getState().getValue()) ||
                                Objects.equals(TspVehicleStateEnum.SCRAPPED.getValue(), tspVehicle.getState().getValue()) ||
                                Objects.equals(TspVehicleStateEnum.ALREADY.getValue(), tspVehicle.getState().getValue())
                ) {
                    ErrorEnum.TSP_VEHICLE_DELETES_STATE_ERR.throwErr();
                }
            }

            //用户id 车辆id 身份证反面等等   从QueryWrapper<TspVehicleAudit> ew = new QueryWrapper<>();获取信息
            TspVehicleAudit vehicleAudit = tspVehicleAuditService.getByTspVehicleId(tspVehicleId);
            //获取信息之后，进行判断 如果不为null，通过审查表的id 进行删除
            if (vehicleAudit != null) {
                tspVehicleAuditRepository.removeById(vehicleAudit);
            }

            // 解绑
            //车辆的用户id 不为null的前提 获取用户id 然后通过用户id 从user表获取信息
            if (tspVehicle.getTspUserId() != null) {
                Long userId = tspVehicle.getTspUserId();
                TspUser tspUser = tspUserRepository.getById(userId);
                // 判断是否存在绑定其他车辆
                //通过用户id 查询车辆表list信息
                List<TspVehicle> vehicles = tspVehicleRepository.findByTspUserId(userId);

                //如果车辆为0或小于0
                //用户是否绑定有车辆 0：没有 1：有  （默认为 0）
                if (vehicles.size() <= 0) {
                    //将用户表中的 是否绑定的值set 0   因为0是为没有绑定
                    tspUser.setHasBind(0);

                    //安全服务工具类（SecurityUtils） 如果没有获取到 就返回401
                    tspUser.setUpdateBy(SecurityUtils.getUsername());
                    //没有绑定的用户是谁！！！
                    //<=0 的话 进行了赋值  如：没有绑定setHasBind(0)  更新者：getUsername  用sava进行存储
                    tspUserRepository.save(tspUser);
                }
            }
            TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicle.getId());
            if (license != null) {
                tspVehicleLicenseRepository.removeById(license.getId());
            }
            tspVehicleRepository.removeById(tspVehicleId);
            // 得到标签id
            String labelStr = tspVehicle.getLabel();
            if ((labelStr != null && !"".equals(labelStr)) && !"[]".equals(labelStr)) {
                //Arrays.asList 数组化为list
                List<String> strings = Arrays.asList(labelStr.split(","));
                for (String string : strings) {
                    if (string.contains("[")) {
                        string = string.replace("[", "");
                    }
                    if (string.contains("]")) {
                        string = string.replace("]", "");
                    }
                    if (string.contains(" ")) {
                        string = string.replace(" ", "");
                    }
                    // 将原来的标签绑定值全部减一全部
                    Long tagId = Long.valueOf(string);
                    TspTag oldTag = tspTagRepository.getById(tagId);
                    oldTag.setTagCount(oldTag.getTagCount() - 1);
                    //安全获取用户信息
                    oldTag.setUpdateBy(SecurityUtils.getUsername());
                    tspTagRepository.updateById(oldTag);
                }
            }
        }
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    public void bind(Long tspVehicleId, Long tspUserId) {
        log.info("车辆正在绑定中......");
        log.info("车辆id,用户id{},{}", tspVehicleId, tspUserId);
        //获取车辆信息
        TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);
        //获取用户信息
        TspUser tspUser = tspUserRepository.getById(tspUserId);
        //如果为null，未找到用户
        if (tspUser == null) {
            ErrorEnum.TSP_USER_USER_NULL_ERR.throwErr();
        }
        //如果为null，车辆不存在，请检查
        if (tspVehicle == null) {
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }

        //"状态1-已创建、2-已销售、3-已绑定、4-已解绑、5-已报废、6-已注册"
        //SOLD:销售 2
        //如果车辆的状态不是销售为2的话，未找到车辆销售信息,无法绑定
        if (!tspVehicle.getState().equals(TspVehicleStateEnum.SOLD)) {
            ErrorEnum.TSP_VEHICLE_SOLD_NULL_ERR.throwErr();
        }

        log.info("开始进行绑定中......");
        //把车辆的state赋值：BOUND("已绑定",3),
        tspVehicle.setState(TspVehicleStateEnum.BOUND);
        //把车辆的用户id赋值
        tspVehicle.setTspUserId(tspUserId);
        //把车辆的当前时间赋值
        tspVehicle.setCurrentBindTime(LocalDateUtils.getCurrentTime());

        log.info("开始生成绑定记录中......");
        //生成绑定记录
        TspUseVehicleRecordAddVO recordAddVO = new TspUseVehicleRecordAddVO();
        //同名属性转换 copy到这个TspUseVehicleRecordAddVO类中
        BeanUtils.copyProperties(tspUser, recordAddVO);
        //把用户信息tspUser 新增到用户车辆记录中
        tspUseVehicleRecordService.add(recordAddVO);

        //把用户车辆对象进行赋值
        TspUserVehicle tspUserVehicle = new TspUserVehicle();
        //车辆id
        tspUserVehicle.setTspVehicleId(tspVehicleId);
        //用户id
        tspUserVehicle.setTspUserId(tspUserId);
        //创建者（安全获取用户信息）
        tspUserVehicle.setCreateBy(SecurityUtils.getUsername());
        //更新者（安全获取用户信息）
        tspUserVehicle.setUpdateBy(SecurityUtils.getUsername());
        tspUserVehicleRepository.save(tspUserVehicle);
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    public void scrap(TspVehicleScrapVO vo) {
        log.info("开始处理报废......");
        log.info("TspVehicleScrapVO {}", vo);
        //安全获取用户登录信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //判断：如果在安全工具类中没有匹配正确的密码，就提示密码输入错误,请重新输入！！！！！
        //vo传入的密码和安全工具处理的密码是否匹配
        if (!SecurityUtils.matchesPassword(vo.getPassword(), loginUser.getPassword())) {
            ErrorEnum.TSP_VEHICLE_SCRAP_ERR.throwErr();
        }
        //循环得到的所有车辆！！！
        for (Long tspVehicleId : vo.getTspVehicleIds()) {
            //从数据库里面 根据车辆id获取车辆信息
            TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);
            //如果车辆中的设备id不等于null 就提示未解绑车辆不可进行报废操作,请先进行解绑
            if (tspVehicle.getTspEquipmentId() != null) {
                ErrorEnum.TSP_EQUIPMENT_SCRAP_ERR.throwErr();
            }
            //如果为null就进行赋值
            //赋值报废时间（当前时间）
            tspVehicle.setScrapTime(LocalDateTime.now());
            //状态1-已创建、2-已销售、3-已绑定、4-已解绑、5-已报废、6-已注册\
            //赋值状态:SCRAPPED:5-已报废
            tspVehicle.setState(TspVehicleStateEnum.SCRAPPED);
            //赋值更新者：根据安全工具的用户名 获取用户账户
            tspVehicle.setUpdateBy(SecurityUtils.getUsername());

            //这个TspVehicle对象 赋值后 更新者通过此对象id 进行更新报废操作
            tspVehicleRepository.updateById(tspVehicle);
        }
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    //使用MultipartFile这个类主要是来实现以表单的形式进行文件上传功能。
    public String importVehicle(MultipartFile multipartFile, Boolean isUpdateSupport) {
        log.info("正在开始导入车辆中......");
        log.info("MultipartFile {}", multipartFile);
        //ExcelUtil工具导入导出
        ExcelUtil<TspVehicleExFactoryTemplateDTO> util = new ExcelUtil<>(TspVehicleExFactoryTemplateDTO.class);
        //从ExcelUtil对象中调用importExcel方法获取输入流  返回类型为list集合  导入车辆数据
        List<TspVehicleExFactoryTemplateDTO> dtos = util.importExcel(multipartFile.getInputStream());

        //利用字符串工具类 判断导入的车辆是否为空，为null提示导入数据不能为空
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        //成功的数
        int successNum = 0;
        //失败的数
        int failureNum = 0;
        //先定义好StringBuilder对象  以备后面追加
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        //把导入的数据进行循环
        for (TspVehicleExFactoryTemplateDTO dto : dtos) {
            try {
                log.info("数据校验中......");
                // 数据校验
                //专门定义一个toCheckExport的方法 参数为（导入车辆数据，失败信息，失败数）
                Map<String, Object> checkMap = toCheckExport(dto, failureMsg, failureNum);

                //检查failureNum
                failureNum = (Integer) checkMap.get("failureNum");
                //检查failureMsg
                failureMsg = (StringBuilder) checkMap.get("failureMsg");

                if (failureNum == 0) {

                    //从TspVehicleExFactoryTemplateDTO对象中获取设备sn 获取设备信息
                    TspEquipment tspEquipment = tspEquipmentRepository.getByName(dto.getSn());
                    //获取设备信息，进行判断 设备信息等于null  （车辆Excel出厂的Sn不等于null，并且获取的Sn不为空字符时）
                    if (tspEquipment == null && (dto.getSn() != null && !dto.getSn().equals(""))) {
                        //自增失败数 ++
                        failureNum++;
                        //从失败信息追加 出厂信息设备 不存在
                        failureMsg.append("<br/>").append(failureNum).append("出厂信息设备 ").append(dto.getSn()).append(" 不存在");
                    } else {
                        if (tspEquipment != null) {
                            //有设备信息的话 从数据库中 通过设备id查找设备信息
                            List<TspVehicle> byTspEquipmentId = tspVehicleRepository.findByTspEquipmentId(tspEquipment.getId());
                            //如果从数据库查有值，失败数自增  失败信息追加为 该设备 以被车辆绑定
                            if (byTspEquipmentId != null && byTspEquipmentId.size() != 0) {
                                failureNum++;
                                failureMsg.append("<br/>").append(failureNum).append("、该设备 ").append(dto.getSn()).append(" 已被车辆绑定");
                            }
                            //boolean 是否报废1-是、0-否
                            //如果报废 自增失败数  该设备为报废状态不可绑定
                            if (tspEquipment.getIsScrap() == true) {
                                failureNum++;
                                failureMsg.append("<br/>").append(failureNum).append("、该设备 ").append(dto.getSn()).append(" 为报废状态，不可绑定");
                            }
                        }
                        // 新增
                        TspVehicle tspVehicle = tspVehicleRepository.getByVin(dto.getVin());
                        // 车型
                        Wrapper<TspVehicleStdModel> ew = tspVehicleStdModeRepository.getByStdModeName(dto.getStdModelName(), dto.getVehicleModelName());
                        //得到stdModel的数据
                        TspVehicleStdModel stdModel = tspVehicleStdModeMapper.getByStdModeName(ew);
                        //判断stdModel 为null 自增失败数 失败信息追加为 无法匹配到已有的二级车型
                        if (stdModel == null) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、无法匹配到已有的二级车型 ");
                        } else {
                            //车辆信息为null，把dto同名属性复制
                            if (tspVehicle == null) {
                                tspVehicle = new TspVehicle();
                                BeanUtils.copyProperties(dto, tspVehicle);
                                //如果有标签 就根据标签来获取
                                if (dto.getLabel() != null && !"".equals(dto.getLabel())) {
                                    TspTag tag = tspTagRepository.getByDealerName(dto.getLabel());
                                    //标签多 先定义一个list
                                    List<Long> label = new ArrayList<>();
                                    //有标签 关联数数量加1
                                    if (tag != null) {
                                        tag.setTagCount(tag.getTagCount() + 1);
                                        tspTagRepository.updateById(tag);
                                        label.add(tag.getId());
                                        //在车辆标签进行赋值
                                        tspVehicle.setLabel(label.toString());
                                    } else {
                                        tspVehicle.setLabel(null);
                                    }
                                }

                                //车辆的创建者
                                tspVehicle.setCreateBy(SecurityUtils.getUsername());
                                //车辆的更新者
                                tspVehicle.setUpdateBy(SecurityUtils.getUsername());

                                //出厂时期
                                LocalDate exDate = LocalDate.parse(dto.getExFactoryDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                LocalDate operateDate = LocalDate.parse(dto.getOperateDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                //                                if (exDate.isAfter(operateDate)) {
                                //                                    failureNum++;
                                //                                    failureMsg.append("<br/>").append(failureNum).append("、出厂日期不能大于运营日期");
                                //                                }
                                //                                else {

                                //赋值出厂时期
                                tspVehicle.setExFactoryDate(exDate);
                                //赋值操作时期
                                tspVehicle.setOperateDate(operateDate);
                                //赋值信息完成进度
                                //信息完成进度0-基本信息、1-出厂信息、2-销售信息、3-上牌信息、4-绑定及MNO信息、5-出入库记录
                                tspVehicle.setProgress(1); //出厂信息
                                //判断下设备信息，将车辆的赋值设备id！
                                if (tspEquipment != null) {
                                    tspVehicle.setTspEquipmentId(tspEquipment.getId());
                                }
                                //将车辆的id 赋值车辆stdModel的id
                                tspVehicle.setTspVehicleStdModelId(stdModel.getId());
                                //赋值完毕，再去存储到车辆中
                                tspVehicleRepository.save(tspVehicle);
                                //设备存在，进行赋值 然后新增到车辆设备记录表中
                                if (tspEquipment != null) {
                                    TspVehicleEquipment tspVehicleEquipment = new TspVehicleEquipment();
                                    tspVehicleEquipment.setTspEquipmentId(tspEquipment.getId());
                                    tspVehicleEquipment.setTspVehicleId(tspVehicle.getId());
                                    tspVehicleEquipmentRepository.save(tspVehicleEquipment);
                                }
                                successNum++;
                                successMsg.append("<br/>").append(successNum).append("、出厂信息 ").append(dto.getVin()).append(" 新增成功");
                                //                                }
                            } else {
                                failureNum++;
                                failureMsg.append("<br/>").append(failureNum).append("、车辆信息 ").append(dto.getVin()).append(" 已存在");
                            }
                        }
                    }
                }
                //捕捉异常，导入失败
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、出厂信息 " + dto.getVin() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        //只要failureNum大于0 表示导入失败
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            //不大于0 那么导入成功！
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public String importSales(MultipartFile multipartFile, Boolean isUpdateSupport) {
        log.info("导入销售中.......");
        ExcelUtil<TspVehicleSaleTemplateDTO> util = new ExcelUtil<>(TspVehicleSaleTemplateDTO.class);
        List<TspVehicleSaleTemplateDTO> dtos = util.importExcel(multipartFile.getInputStream());
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TspVehicleSaleTemplateDTO dto : dtos) {
            try {
                // 数据校验
                Map<String, Object> checkMap = toCheckSales(dto, failureMsg, failureNum);
                failureNum = (Integer) checkMap.get("failureNum");
                failureMsg = (StringBuilder) checkMap.get("failureMsg");
                if (failureNum == 0) {
                    if (dto.getPurchaser() == null || dto.getChannelType() == null) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、必填字段数据缺失 ").append(dto.getVin()).append(" 导入失败");
                        return failureMsg.toString();
                    }
                    // 新增
                    TspVehicle tspVehicle = tspVehicleRepository.getByVin(dto.getVin());
                    if (tspVehicle == null) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、车辆 ").append(dto.getVin()).append(" 不存在");
                    } else if (tspVehicle.getProgress() == 1) {
                        //user
                        tspVehicle.setUpdateBy(SecurityUtils.getUsername());
                        //已销售
                        tspVehicle.setState(TspVehicleStateEnum.SOLD);
                        //用途
                        tspVehicle.setPurpose(dto.getPurpose());
                        //信息完成进度
                        tspVehicle.setProgress(2);

                        //车辆表中更新
                        tspVehicleRepository.updateById(tspVehicle);
                        TspVehicleMarket tspVehicleMarket = new TspVehicleMarket();
                        //车卡信息推送补充数据表 如车辆id 车辆产地 销售渠道（4s店） 办理员工姓名  发动机号码
                        TspVehicleOther tspVehicleOther = new TspVehicleOther();
                        BeanUtils.copyProperties(dto, tspVehicleMarket);
                        BeanUtils.copyProperties(dto, tspVehicleOther);

                        //"购买领域1-私人用车、2-单位用车、0-未知"
                        if (dto.getPurchaserState().equals("单位用车")) {
                            tspVehicleMarket.setPurchaserState(2);
                        }
                        if (dto.getPurchaserState().equals("私人用车")) {
                            tspVehicleMarket.setPurchaserState(1);
                        }

                        //车辆信息
                        tspVehicleMarket.setTspVehicleId(tspVehicle.getId());
                        //创建者信息
                        tspVehicleMarket.setCreateBy(SecurityUtils.getUsername());
                        //更新者信息
                        tspVehicleMarket.setUpdateBy(SecurityUtils.getUsername());
                        //开票日期tspVehicle.getId()
                        tspVehicleMarket.setInvoicingDate(LocalDate.parse(dto.getInvoicingDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        TspVehicleMarket oneMarket = tspVehicleMarketRepository
                                .lambdaQuery()
                                .eq(TspVehicleMarket::getTspVehicleId, tspVehicle.getId())
                                .one();
                        if (Objects.nonNull(oneMarket)) {
                            tspVehicleMarket.setId(oneMarket.getId());
                        }
                        //新增以上信息到数据库表中
                        tspVehicleMarketRepository.saveOrUpdate(tspVehicleMarket);

                        //车卡信息推送赋值
                        //车辆信息
                        tspVehicleOther.setTspVehicleId(tspVehicle.getId());
                        //创建者
                        tspVehicleOther.setCreateBy(SecurityUtils.getUsername());
                        //更新者
                        tspVehicleOther.setUpdateBy(SecurityUtils.getUsername());
                        TspVehicleOther otherOne = tspVehicleOtherRepository
                                .lambdaQuery()
                                .eq(TspVehicleOther::getTspVehicleId, tspVehicle.getId())
                                .one();
                        if (Objects.nonNull(otherOne)) {
                            tspVehicleOther.setId(otherOne.getId());
                        }
                        //赋值完进行新增
                        tspVehicleOtherRepository.saveOrUpdate(tspVehicleOther);
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、销售信息 ").append(dto.getVin()).append(" 新增成功");
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、销售信息 ").append(dto.getVin()).append(" 已存在");
                    }
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、销售信息 " + dto.getVin() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            return failureMsg.toString();
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    public List<TspVehicleExportListDTO> exportList(TspVehiclePageListVO vo) {
        log.info("正在开始导出列表中......");
        //参数为TspVehiclePageListVO vo  车辆分页列表

        vo.setPageNum(1);
        vo.setPageSize(Integer.MAX_VALUE);
        List<TspVehicleExportListDTO> dtos = new ArrayList<>();
//        for (TspVehiclePageListDTO pageListDTO : this.getPageList(vo).getList()) {
//            TspVehicleExportListDTO dto = new TspVehicleExportListDTO();
//            BeanUtils.copyProperties(pageListDTO, dto);
//            dto.setCertificationState(pageListDTO.getCertificationState().getValue());
//            dto.setState(pageListDTO.getState().getValue());
//            dto.setSendStatus(pageListDTO.getSendStatus().getValue());
//            dto.setCreateTime(pageListDTO.getCreateTime().toString().replace("T", " "));
//            dtos.add(dto);
//        }
        this.getPageList(vo).getList().forEach(pageListDTO -> {
            TspVehicleExportListDTO dto = new TspVehicleExportListDTO();
            BeanUtils.copyProperties(pageListDTO, dto);
            dto.setCertificationState(pageListDTO.getCertificationState().getValue());
            dto.setState(pageListDTO.getState().getValue());
            dto.setSendStatus(pageListDTO.getSendStatus().getValue());
//          dto.setCreateTime(pageListDTO.getCreateTime().toString().replace("T", " "));
            dto.setCreateTime(pageListDTO.getCreateTime());
            dtos.add(dto);
        });
        return dtos;
    }

    public TspVehicleInfoDTO get(Long tspVehicleId) {
        log.info("通过车辆id获取车辆信息{}", tspVehicleId);
        //车辆信息
        TspVehicle vehicle = tspVehicleRepository.getById(tspVehicleId);
        //车辆用户信息
        TspUser tspUser = tspUserRepository.getById(vehicle.getTspUserId());
        //车辆认证审核 审查到的车辆信息
        TspVehicleAudit audit = tspVehicleAuditService.getByTspVehicleId(tspVehicleId);
        //车辆销售信息
        TspVehicleMarket market = tspVehicleMarketRepository.getByTspVehicleId(tspVehicleId);
        //车辆车牌信息
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicleId);
        //车卡信息推送补充数据表
        TspVehicleOther tspVehicleOther = tspVehicleOtherRepository.getByTspVehicleId(tspVehicleId);
        //车辆型号
        TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(vehicle.getTspVehicleStdModelId());
        TspVehicleModel model = null;
        if (stdModel != null) {
            //不为null 从车辆型号的id 赋值给model
            model = tspVehicleModelRepository.getById(stdModel.getTspVehicleModelId());
        }
        TspVehicleInfoDTO dto = new TspVehicleInfoDTO();
        if (audit != null) {//审核信息
            BeanUtils.copyProperties(audit, dto);
            dto.setTspVehicleAuditId(audit.getId());
        }
        if (tspUser != null) {//用户信息
            BeanUtils.copyProperties(tspUser, dto);
        }
        if (market != null) {//销售信息
            BeanUtils.copyProperties(market, dto);
        }
        if (license != null) {//车牌信息
            BeanUtils.copyProperties(license, dto);
            if (StringUtils.isNotBlank(dto.getPlateCode())) {
                String substring = dto.getPlateCode().substring(0, 1);
                dto.setPlateCodeName(substring);
                int len = dto.getPlateCode().length();
                dto.setPlateCode(dto.getPlateCode().substring(1, len));
            }
        }
        if (tspVehicleOther != null) {//车卡信息推送补充数据表
            BeanUtils.copyProperties(tspVehicleOther, dto);
        }
        if (model != null) {//车辆车型信息
            BeanUtils.copyProperties(model, dto);
        }
        if (stdModel != null) {//车辆型号信息
            BeanUtils.copyProperties(stdModel, dto);
        }
        BeanUtils.copyProperties(vehicle, dto);
        // 得到标签id
        String label = vehicle.getLabel();
        if ((label != null && !"".equals(label)) && !"[]".equals(label)) {
            List<String> strings = Arrays.asList(label.split(","));
            List<Long> labelLong = new ArrayList<>();
            for (String string : strings) {
                if (string.contains("[")) {
                    string = string.replace("[", "");
                }
                if (string.contains("]")) {
                    string = string.replace("]", "");
                }
                if (string.contains(" ")) {
                    string = string.replace(" ", "");
                }
                labelLong.add(Long.valueOf(string));
            }
            dto.setLabel(labelLong);
        }
        dto.setTspVehicleId(tspVehicleId);
        //        log.debug(JSONObject.valueToString(dto));
        return dto;
    }

    public TspVehicleAuditInfoDTO getAuditInfo(Long tspVehicleId) {
        log.info("获取审核信息中......{}", tspVehicleId);
        //获取审核数据
        TspVehicleAudit audit = tspVehicleAuditService.getByTspVehicleId(tspVehicleId);
        //不存在：未找到该车辆认证信息
        if (audit == null) {
            ErrorEnum.TSP_VEHICLE_NULL_AUDIT.throwErr();
        }
        //根据车辆id查找车辆信息
        TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);
        //根据车辆信息的用户id 获取用户信息
        TspUser tspUser = tspUserRepository.getById(tspVehicle.getTspUserId());
        //根据车辆id获取车牌信息
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicleId);

        //车辆信息 - 传输对象 - 认证信息   比如：身份证正反面  认证状态 不通过的原因 用户信息 车牌号等等
        TspVehicleAuditInfoDTO dto = new TspVehicleAuditInfoDTO();
        //将审核的信息同名属性copy到车辆信息-传输-认证信息中
        BeanUtils.copyProperties(audit, dto);
        //如果有车牌号 从车牌号获取 赋值到这个TspVehicleAuditInfoDTO对象里面的plateCode（车牌号）
        if (license != null) {
            dto.setPlateCode(license.getPlateCode());
        }
        //用户信息
        dto.setTspUser(tspUser);
        //车辆及用户信息
        dto.setTspVehicle(tspVehicle);
        return dto;
    }

    //TspVehicleRelationMobileOptionDTO
    // 车辆信息 - 数据传输对象 - 关联账号下拉列表
    public List<TspVehicleRelationMobileOptionDTO> relationMobileOption() {
        return tspVehicleRepository.relationMobileOption();
    }

    public TspVehicleDataDTO data() {
        TspVehicleDataDTO dto = new TspVehicleDataDTO();
        //开始赋值
        //1.在线车辆
        dto.setOnLineCount(tspVehicleRepository.countByOnLine());
        //2.离线车辆
        dto.setOffLineCount(tspVehicleRepository.countByOffLine());
        //3.总车辆
        dto.setTotalCount(tspVehicleRepository.countByAll());
        //4.报警车辆
        dto.setAlertCount(vehicleAlertRepository.countByAlert());
        return dto;
    }

    public List<TspVehicleVolumeDataDTO> volumeData(TspVehicleVolumeDataVO vo) {
        return super.dataStartTimeAndEndTime(vo);
    }

    @SneakyThrows
    //@SneakyThrows就是使用该注解后不需要担心Exception的异常处理
    //下行
    public void downloadPackage(TspDownloadPackageDTO dto) {
        //        Map<String, Object> map = new HashMap<>();
        //        map.put("topic", dto.getTopic());
        //        map.put("qos", dto.getQos());
        //        map.put("message", dto.getImei() + dto.getMessage());
        HttpUtils.doPost(commandServerHttpAddress + "/command/send", JSON.toJSONString(dto));
    }

    //下发指令
    public void sendCommand(TspVehicleCommandDTO tspVehicleCommandDTO) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("GMT+8"));

        //使用雪花算法，来保证id的全局唯一性
        Long id = new Snowflake().nextId();
        //当前时间和自增id
        tspVehicleCommandDTO.setTime(time);
        tspVehicleCommandDTO.setId(id);
        HttpUtils.doPost(commandServerHttpAddress + "/command/send", JSON.toJSONString(tspVehicleCommandDTO));
    }

    /**
     * 获取车辆vin最后一条指令执行结果
     */
    public VehicleCommand getCommandExecuteResult(String vin) {
        // 读取redis中的数据
        VehicleCommand vehicleCommand = vehicleRedisCache.readVehicleCommandCache(vin);

        // TODO:若redis中没有则？

        return vehicleCommand;
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    public int dealEquipment(Long tspEquipmentId) {
        log.info("处理设备中.....{}", tspEquipmentId);
        //通过设备id 查询返回（list）车辆信息
        Long tspVehicleId = tspVehicleMapper.getByEquipmentId(tspEquipmentId);
        //车辆数据为null 车辆不存在,请检查！！！
        if (tspVehicleId == null) {
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }

        //在车辆设备数据记录表中 根据设备id 和某个车辆的设备id 查找数据
        List<TspVehicleEquipment> tspVehicleEquipments = tspVehicleEquipmentRepository.getByEquipmentId(tspVehicleId, tspEquipmentId);
        if (tspVehicleEquipments != null && tspVehicleEquipments.size() != 0) {
            //获取
            TspVehicleEquipment tspVehicleEquipment = tspVehicleEquipments.get(0);
            //绑定时间为当前时间
            tspVehicleEquipment.setUnBindTime(LocalDateUtils.getCurrentTime());
            //将车辆设备记录
            tspVehicleEquipmentRepository.updateById(tspVehicleEquipment);
        }
        Integer state = TspVehicleStateEnum.UNBOUND.getValue();
        tspVehicleMapper.updateSetState(state, tspVehicleId);
        return tspVehicleMapper.updateSetNull(tspVehicleId);
    }

    /**
     * 历史绑定设备
     */
    public PageInfo<TspEquipmentPageListDTO> equipmentHistory(Long tspVehicleId) {
        log.info("查找历史绑定记录id....{}", tspVehicleId);
        QueryWrapper<TspVehicleEquipment> listEw = tspVehicleEquipmentRepository.getPageListEw(tspVehicleId);
        IPage<TspEquipmentPageListDTO> pageList = tspVehicleEquipmentMapper.getPageList(Page.of(1, 10), listEw);
        return PageInfo.of(pageList);
    }

    /**
     * 当前绑定设备
     */
    public PageInfo<TspEquipmentPageListDTO> equipmentNow(Long tspEquipmentId) {
        log.info("当前设备绑定id.....{}", tspEquipmentId);
        QueryWrapper<TspEquipment> listEw = tspEquipmentRepository.getNowEquipment(tspEquipmentId);
        IPage<TspEquipmentPageListDTO> pageList = tspEquipmentMapper.getNowPageList(Page.of(1, 10), listEw);
        return PageInfo.of(pageList);
    }

    //车辆信息 - 数据传输对象 - 出厂信息模板 TspVehicleExFactoryTemplateDTO
    public List<TspVehicleExFactoryTemplateDTO> exportExFactory(TspVehiclePageListVO vo) {
        List<TspVehicleExFactoryTemplateDTO> list = null;
        try {
            vo.setPageNum(1);
            vo.setPageSize(Integer.MAX_VALUE);
            QueryWrapper<TspVehicle> ew = tspVehicleRepository.getPageListEw(vo);
            list = tspVehicleMapper.getExFactoryList(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
            for (TspVehicleExFactoryTemplateDTO dto : list) {
                String label = dto.getLabel();
                List<String> labelList = new ArrayList<>();
                if ((label != null && !"".equals(label)) && !"[]".equals(label)) {
                    List<String> strings = Arrays.asList(label.split(","));
                    for (String string : strings) {
                        if (string.contains("[")) {
                            string = string.replace("[", "");
                        }
                        if (string.contains("]")) {
                            string = string.replace("]", "");
                        }
                        if (string.contains(" ")) {
                            string = string.replace(" ", "");
                        }
                        TspTag tag = tspTagRepository.getById(Long.valueOf(string));
                        labelList.add(tag.getTagName());
                    }
                }
                if (labelList.size() > 0) {
                    dto.setLabel(String.valueOf(labelList));
                }
                if ("[]".equals(dto.getLabel())) {
                    dto.setLabel("");
                }
            }
        } catch (Exception e) {
            String msg = "导出出厂信息失败";
            log.error(msg, e);
        }
        return list;
    }

    public List<TspVehicleSaleTemplateDTO> exportSales(TspVehiclePageListVO vo) {
        log.info("导出销售信息.....{}", vo);
        List<TspVehicleSaleTemplateDTO> list = null;
        try {
            vo.setPageNum(1);//当前页
            vo.setPageSize(Integer.MAX_VALUE);//最大页数
            QueryWrapper<TspVehicle> ew = tspVehicleRepository.getPageListEw(vo);
            list = tspVehicleMapper.getSalesList(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
            for (TspVehicleSaleTemplateDTO dto : list) {
                if ("1".equals(dto.getPurchaserState())) {
                    dto.setPurchaserState("私人用车");
                } else if ("2".equals(dto.getPurchaserState())) {
                    dto.setPurchaserState("单位用车");
                } else if ("0".equals(dto.getPurchaserState())) {
                    dto.setPurchaserState("");
                }
            }
        } catch (Exception e) {
            String msg = "导出销售信息失败";
            log.error(msg, e);
        }
        return list;
    }

    /**
     * 获取实名认证详情
     */
    public Map<String, Object> getRealNameResult(Long tspVehicleId) {
        log.info("获取实名认证详情id......{}", tspVehicleId);
        // 调用实名认证 (实名认证 - 请求对象 - 实名认证前端请求参数)
        TspVehicleIdentificationVO identificationVO = new TspVehicleIdentificationVO();
        identificationVO.setTspVehicleId(tspVehicleId);
        Map<String, Object> resultMap = tspVehicleIdentificationService.getRealName(identificationVO);
        Object code = resultMap.get("Code");
        if (code == null) {
            //实名认证失败
            throw new ServiceException(ErrorEnum.IDENTIFICATION_FAIL_ERROR.getMsg() + "：" + resultMap.get("FailureCause"),
                    ErrorEnum.IDENTIFICATION_FAIL_ERROR.getCode());
        }
        // 实名认证结果集
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("ResultList");
        Map<String, Object> result = tspVehicleMapper.getRealNameMessage(tspVehicleId);
        if ("510".equals(code)) {
            ErrorEnum.IDENTIFICATION_510_ERROR.throwErr();
        }
        if ("520".equals(code)) {
            ErrorEnum.IDENTIFICATION_520_ERROR.throwErr();
        }
        if ("530".equals(code)) {
            ErrorEnum.IDENTIFICATION_ICCID_ERROR.throwErr();
        }
        if ("200".equals(code)) {
            // 实名认证结果集
            Map<String, Object> map = resultList.get(0);
            Object authResult = map.get("AuthResult");
            if (authResult != null && "0".equals(String.valueOf(authResult))) {
                return resultMap;
                //                ErrorEnum.IDENTIFICATION_NOT_EXIST_ERROR.throwErr();
            } else {
                // 实名结果
                String certNum = (String) map.get("CertNum");
                String partyName = (String) map.get("PartyName");
                result.put("certNum", certNum);
                result.put("partyName", partyName);
                result.put("resultList", resultList);
            }
        }
        return result;
    }

    public PageInfo<TspVehiclePageListDTO> listVehicle(TspVehiclePageListVO vo) {
        log.info("获取车辆列表.....{}", vo);
        //获取车辆分页列表
        QueryWrapper<TspVehicle> ew = tspVehicleRepository.getPageListEw(vo);
        Integer count = tspVehicleMapper.getCount(ew);
        if (count <= 10) {
            vo.setPageNum(1); //当前页
        }
        //拿取分页列表数据
        IPage<TspVehiclePageListDTO> page = tspVehicleMapper.getPageList(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        //循环遍历页的记录
        for (TspVehiclePageListDTO record : page.getRecords()) {
            // 读取redis中的实时数据
            RedisConfig redisConfig = new RedisConfig();
            RedisTemplate<Object, Object> redisTemplate = redisConfig.redisTemplate(redisConnectionFactory);
            HashOperations<Object, Object, Object> hashOps = redisTemplate.opsForHash();
            List<Object> values = hashOps.values("VehicleRealtimeData:" + record.getVin());
            // 读取redis中的所有数据
            if (values != null && values.size() != 0) {
                LocalDateTime collectTime = null;
                // 整车数据
                log.info("获取整车数据中.......");
                Map<String, Object> integrateJson = (Map<String, Object>) readListFromCache("VehicleRealtimeData:" + record.getVin(), "VehicleIntegrate");
                if (integrateJson != null) {
                    collectTime = convertToLocalDateTime((JSONArray) integrateJson.get("collectTime"));
                    integrateJson.remove("collectTime");
                    List<VehicleIntegrate> vehicleIntegrates = JSONArray.parseArray("[" + integrateJson + "]", VehicleIntegrate.class);
                    if (vehicleIntegrates != null && vehicleIntegrates.size() != 0) {
                        record.setIsOnline(calcVehicleStateExt(vehicleIntegrates.get(0), collectTime, LocalDateTime.now()));
                    } else {
                        record.setIsOnline(false);
                    }
                }
            } else {
                record.setIsOnline(false);
            }
        }
        return PageInfo.of(page);
    }

    /**
     * 获取绑定信息详情
     */
    public List<Map<String, Object>> getBind(Long tspVehicleId) {

        return tspVehicleMapper.getBind(tspVehicleId);
    }

    public List<Map<String, String>> saleNameList() {
        return tspDealerMapper.saleNameList();
    }

    public List<TspDealer> saleNameListByLikeAddress(String address) {
        return tspDealerMapper.saleNameListByLikeAddress(address);
    }

    public Map<String, String> saleNameGetAddress(String dealerName) {
        return tspDealerMapper.saleNameGetAddress(dealerName);
    }

    /**
     * 查询所有有效的车辆vin
     */
    public List<String> selectAllVinList() {
        List<TspVehicle> allVehicles = xTspVehicleService.getAllVehicles();
        return allVehicles.stream().map(s -> s.getVin()).collect(Collectors.toList());
    }

    private Map<String, Object> toCheckExport(TspVehicleExFactoryTemplateDTO dto, StringBuilder failureMsg, int failureNum) {
        log.info("正在检查导出信息......");
        //定一个key value容器  来将失败数和失败信息put
        Map<String, Object> checkMap = new HashMap<>();
        // VIN
        String versionRegexp = "^([a-zA-Z0-9]){17}$";
        if (dto.getVin() == null || !dto.getVin().matches(versionRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、出厂信息VIN ").append(dto.getVin()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 车辆厂商
        if (dto.getProviderName() == null || dto.getProviderName().equals("") || !dto.getProviderName().equals("摩登汽车有限公司")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆厂商 ").append(dto.getProviderName()).append(" 名称异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 车辆一级型号
        if (dto.getVehicleModelName() == null || dto.getVehicleModelName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆一级型号 ").append(dto.getVehicleModelName()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 车辆二级型号
        if (dto.getStdModelName() == null || dto.getStdModelName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆二级型号 ").append(dto.getStdModelName()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 车辆配置名称
        if (dto.getConfigureName() == null || dto.getConfigureName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆配置名称 ").append(dto.getConfigureName()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 颜色
        if (dto.getColor() == null || dto.getColor().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、外观颜色 ").append(dto.getColor()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 车辆批次号
        if (dto.getBatchNo() == null || dto.getBatchNo().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆批次号 ").append(dto.getBatchNo()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 出厂日期
        String dateRegexp = "^([0-9]{4})(-([0-1][0-9]))(-[0-3][0-9])$";
        if (dto.getExFactoryDate() == null || !dto.getExFactoryDate().matches(dateRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、出厂日期 ").append(dto.getExFactoryDate()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 运营日期
        if (dto.getOperateDate() == null || !dto.getOperateDate().matches(dateRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、下线日期 ").append(dto.getOperateDate()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // CDN序列号
//        String cdnRegexp = "^([a-zA-Z0-9]){11}$";
//        if (dto.getCduNum() == null || !dto.getCduNum().matches(cdnRegexp)) {
//            failureNum++;
//            failureMsg.append("<br/>").append(failureNum).append("、CDN序列号 ").append(dto.getCduNum()).append(" 格式异常");
//            checkMap.put("failureNum", failureNum);
//            checkMap.put("failureMsg", failureMsg);
//            return checkMap;
//        }
        // 电池包规格
        if (dto.getEssModel() == null || dto.getEssModel().equals("") || ((!dto.getEssModel().equals("73度中航大包") && !dto.getEssModel().equals("80度中航大包")) && !dto.getEssModel().equals("53度中航小包"))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、电池包规格 ").append(dto.getEssModel()).append(" 值异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 电池包编号
        String essRegexp = "^([a-zA-Z0-9]){24}$";
        if (dto.getEssNum() == null || !dto.getEssNum().matches(essRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、电池包编号 ").append(dto.getEssNum()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 电动机品牌
        if (dto.getMotorBrand() == null || dto.getMotorBrand().equals("") || (!dto.getMotorBrand().equals("青山") && !dto.getMotorBrand().equals("上汽齿"))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、电动机品牌 ").append(dto.getMotorBrand()).append(" 值异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 电动机序列号
        String motorRegexp = "^.{1,27}$";
        if (dto.getMotorNum() == null || !dto.getMotorNum().matches(motorRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、电动机序列号 ").append(dto.getMotorNum()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", failureNum);
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

    private Map<String, Object> toCheckSales(TspVehicleSaleTemplateDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        // 购买领域
        if ((dto.getPurchaserState() != null && !"".equals(dto.getPurchaserState())) && (!dto.getPurchaserState().equals("私人用车") && !dto.getPurchaserState().equals("单位用车"))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、购买领域 ").append(dto.getPurchaserState()).append(" 值异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 车辆用途
        if ((dto.getPurpose() == null || "".equals(dto.getPurpose())) || ((!dto.getPurpose().equals("私人乘用车") && !dto.getPurpose().equals("公务乘用车")) && (!dto.getPurpose().equals("私人运营车") && !dto.getPurpose().equals("公务运营车")))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆用途 ").append(dto.getPurpose()).append(" 值异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // VIN
        String versionRegexp = "^([a-zA-Z0-9]){17}$";
        if (dto.getVin() == null || !dto.getVin().matches(versionRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、出厂信息VIN ").append(dto.getVin()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 办理员工姓名
        if (dto.getEmployeeName() == null || dto.getEmployeeName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、办理员工姓名 ").append(dto.getEmployeeName()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 购买方姓名
        if (dto.getPurchaser() == null || dto.getPurchaser().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、购买方 ").append(dto.getPurchaser()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 身份证号
        String idRegexp = "^(\\d{17})(\\d|X|x)$";
        if (dto.getVehicleIdCard() == null || !dto.getVehicleIdCard().matches(idRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、身份证号 ").append(dto.getVehicleIdCard()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 发票号码
        String invoiceRegexp = "^\\d{8}$";
        if (dto.getInvoiceNo() == null || !dto.getInvoiceNo().matches(invoiceRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、发票号码 ").append(dto.getInvoiceNo()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 开票日期
        String dateRegexp = "^([0-9]{4})(-([0-1][0-9]))(-[0-3][0-9])$";
        if (dto.getInvoicingDate() == null || !dto.getInvoicingDate().matches(dateRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、开票日期 ").append(dto.getInvoicingDate()).append(" 格式异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 销货单位名称
        if (dto.getSalesUnitName() == null || dto.getSalesUnitName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、销货单位名称 ").append(dto.getSalesUnitName()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 销货单位地址
        if (dto.getSalesUnitAddress() == null || dto.getSalesUnitAddress().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、销货单位地址 ").append(dto.getSalesUnitAddress()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 销货渠道名称
        if (dto.getSalesChannel() == null || dto.getSalesChannel().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、销货渠道名称 ").append(dto.getSalesChannel()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 车辆状态
        if (dto.getVehicleStatus() == null || dto.getVehicleStatus().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆状态 ").append(dto.getVehicleStatus()).append(" 值异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 销售渠道类型
        if (dto.getChannelType() == null || dto.getChannelType().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、销售渠道类型 ").append(dto.getChannelType()).append(" 值异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 是否新车
        if (dto.getNewVehicleFlag() == null || dto.getNewVehicleFlag().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、是否新车 ").append(dto.getNewVehicleFlag()).append(" 值异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", failureNum);
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

    private Boolean calcVehicleStateExt(VehicleIntegrate dto, LocalDateTime collectTime, LocalDateTime now) {
        log.info("车辆状态,当前时间{}，{}", dto, collectTime, now);
        if (dto != null && collectTime != null) {
            Duration duration = Duration.between(collectTime, now);
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
}
