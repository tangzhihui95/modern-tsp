package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.domain.TspVehicleModel;
import com.modern.tsp.domain.TspVehicleStdModel;
import com.modern.tsp.enums.TpsVehicleDataKeyEnum;
import com.modern.tsp.mapper.TspVehicleModelMapper;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.TspVehicleModelAddVO;
import com.modern.tsp.model.vo.TspVehicleModelPageListVO;
import com.modern.tsp.model.vo.TspVehiclePageListVO;
import com.modern.tsp.repository.TspVehicleModelRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import com.modern.tsp.repository.TspVehicleStdModeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 摩登 - TSP - 车辆车型 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleModelService extends TspBaseService{
    private final TspVehicleRepository tspVehicleRepository;
    private final TspVehicleModelRepository tspVehicleModelRepository;
    private final TspVehicleModelMapper tspVehicleModelMapper;
    private final TspVehicleStdModeRepository tspVehicleStdModeRepository;
    public PageInfo<TspVehicleModelPageListDTO> getPageList(TspVehicleModelPageListVO vo) {
        // 1.查询车型所有列表并实现分页功能
        // 2.处理要返回的分页数据（三个属性：车辆名称 车辆数量 二级车型关联）
        // 3.处理二级车型对象 并且赋值属性
        // 4.判断值的唯一性，最后将赋值完的属性给最开始的DTO
        // 5.分页数据处理完毕

        //like：车型型号 更新时间降序
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper<>();
        if(Objects.nonNull(vo.getTspVehicleStdModelId())){
            Set<Long> modelIds = tspVehicleStdModeRepository
                    .lambdaQuery()
                    .select(TspVehicleStdModel::getTspVehicleModelId)
                    .eq(TspVehicleStdModel::getId, vo.getTspVehicleStdModelId())
                    .list()
                    .stream()
                    .map(TspVehicleStdModel::getTspVehicleModelId)
                    .collect(Collectors.toSet());
            ew.in(CollectionUtils.isNotEmpty(modelIds),TspVehicleModel.ID,modelIds);
        }
        ew.like(StringUtils.isNotEmpty(vo.getVehicleModelName()),TspVehicleModel.VEHICLE_MODEL_NAME,vo.getVehicleModelName());
        ew.orderByDesc(TspVehicleModel.ID);
        // 实现分页功能
        Page<TspVehicleModel> page = tspVehicleModelRepository.page(Page.of(vo.getPageNum(),vo.getPageSize()),ew);

        //开始遍历整个车型分页数据（车型名称 关联车辆数 二级车型(封装了二级车型对象来关联)
        ArrayList<TspVehicleModelPageListDTO> dtos = new ArrayList<>();
        for (TspVehicleModel model : page.getRecords()) {
            TspVehicleModelPageListDTO dto = new TspVehicleModelPageListDTO();
            BeanUtils.copyProperties(model,dto);

            //开始处理二级车型数据
            ArrayList<TspVehicleStdModePageListDTO> stdModePageListDTOS = new ArrayList<>();
            // 1.先循环遍历所有车型 根据全局唯一id 来匹配tsp_vehicle_model_id 查询所有车型
            List<TspVehicleStdModel> stdModelList = tspVehicleStdModeRepository.getByTspVehicleModelId(model.getId());
            for (TspVehicleStdModel stdModel : stdModelList) {
                // 2.创建二级车型对象 把遍历的车型复制到二级车型对象中
                TspVehicleStdModePageListDTO modePageListDTO = new TspVehicleStdModePageListDTO();
                BeanUtils.copyProperties(stdModel,modePageListDTO);

                // 3.查询二级车型 用全局的车型主键id 来匹配tsp_vehicle_std_model_id 来得到二级车型id
                Integer vehicleCountOfThisStdModel = tspVehicleRepository.countByTspVehicleStdModelId(stdModel.getId());

                // 4.给二级车型对象赋予属性 1.车辆名称 2.二级车型关联车辆数量
                modePageListDTO.setVehicleModelName(model.getVehicleModelName());
                modePageListDTO.setStdModeCount(vehicleCountOfThisStdModel);

                // 5.全局车型id和二级车型id相匹配 那么就把赋予值的属性添加进去（为null和不为null都添加）
                if (StringUtils.isNotNull(vo.getTspVehicleStdModelId())) {
                    if (vo.getTspVehicleStdModelId().equals(stdModel.getId())) {
                        stdModePageListDTOS.add(modePageListDTO);
                    }
                }
                else {
                    stdModePageListDTOS.add(modePageListDTO);
                }
            }
            //二级车型(TspVehicleStdModePageListDTO)处理完毕后，但最后要返回的对象是TspVehicleModelPageListDTO
            int vehicleCountOfThisModel = stdModePageListDTOS.stream().mapToInt(TspVehicleStdModePageListDTO::getStdModeCount).sum();

            // 最后要返回的对象是TspVehicleModelPageListDTO 所以必须给这个类赋予属性 (车辆名称（已经有值）车辆数（count） 二级车型关联（children）)
            if (StringUtils.isNotNull(vo.getTspVehicleStdModelId())) {
                if (CollectionUtils.isNotEmpty(stdModePageListDTOS) && stdModePageListDTOS.size() != 0) {
                    // children：二级车型
                    dto.setChildren(stdModePageListDTOS);
                    // vehicleCount：关联车辆
                    dto.setVehicleCount(vehicleCountOfThisModel);
                    dtos.add(dto);
                }
            }
            else {
                dto.setChildren(stdModePageListDTOS);
                dto.setVehicleCount(vehicleCountOfThisModel);
                dtos.add(dto);
            }
        }
        return PageInfo.of(dtos,page.getCurrent(),page.getSize(),page.getTotal());
    }

    public void add(TspVehicleModelAddVO vo) {
        // 1.查数据 2.判断唯一性 3.新增到数据库（需要创建实体对象） 4.权限认证 5.进行添加save
        log.info("车型添加......vo={}", vo);
        TspVehicleModel vehicleModel = tspVehicleModelRepository.getByVehicleModelName(vo.getVehicleModelName());
        if (vehicleModel != null){
            ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_NOT_NULL_ERR.throwErr();
        }
        vehicleModel = new TspVehicleModel();
        BeanUtils.copyProperties(vo,vehicleModel);
        vehicleModel.setCreateBy(SecurityUtils.getUsername());
        vehicleModel.setUpdateBy(SecurityUtils.getUsername());
        tspVehicleModelRepository.save(vehicleModel);
    }

    public void edit(TspVehicleModelAddVO vo) {
        log.info("车型编辑.......vo={}", vo);
        if (vo.getTspVehicleModelId()==null){
            throw new RuntimeException("车型id不存在");
        }
        // 1.查数据 2.判断唯一性 3.新增到数据库（实体对象已存在，直接获取进行编辑）4.权限认证 5.进行修改updateById
        TspVehicleModel model = tspVehicleModelRepository.getById(vo.getTspVehicleModelId());
        if (model == null){
            ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_NULL_ERR.throwErr();
        }
        TspVehicleModel modelName = tspVehicleModelRepository.getByVehicleModelName(vo.getVehicleModelName());
        if (modelName != null){
            if (!modelName.getId().equals(model.getId())){
                ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_NOT_NULL_ERR.throwErr();
            }
        }
        BeanUtils.copyProperties(vo,model);
        model.setUpdateBy(SecurityUtils.getUsername());
        tspVehicleModelRepository.updateById(model);
    }

    public void delete(Long tspVehicleModelId) {
        log.info("根据车型id进行删除......Id={}", tspVehicleModelId);
        // 1.查数据 2.没有数据，抛出异常 3.车型存在关联的车型无法删除 4，进行删除removeById

        TspVehicleModel model = tspVehicleModelRepository.getById(tspVehicleModelId);
        if (model == null){
            ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_NULL_ERR.throwErr();
        }
        List<TspVehicleStdModel> stdModelList = tspVehicleStdModeRepository.findByTspVehicleModelId(tspVehicleModelId);
        if (!CollectionUtils.isEmpty(stdModelList)){
            //车辆车型下存在型号车辆，无法删除
            ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_DELETE_ERR.throwErr();
        }
        tspVehicleModelRepository.removeById(tspVehicleModelId);
    }
    @SneakyThrows
    public String importVehicleModel(MultipartFile multipartFile, Boolean isUpdateSupport) {
        ExcelUtil<TspVehicleModel> util = new ExcelUtil<>(TspVehicleModel.class);
        List<TspVehicleModel> dtos = util.importExcel(multipartFile.getInputStream());
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TspVehicleModel dto : dtos) {
            try {
                if (dto.getVehicleModelName() == null) {
                    failureNum++;
                    failureMsg.append("<br/>").append(successNum).append("、车辆一级车型名称不能为空 ");
                }
                else {
                    TspVehicleModel tspVehicleModel = tspVehicleModelRepository.getByVehicleModelName(dto.getVehicleModelName());
                    // 新增
                    if (tspVehicleModel == null) {
                        // 一级车型
                        tspVehicleModel = new TspVehicleModel();
                        BeanUtils.copyProperties(dto, tspVehicleModel);
                        tspVehicleModel.setCreateBy(SecurityUtils.getUsername());
                        tspVehicleModel.setUpdateBy(SecurityUtils.getUsername());
                        tspVehicleModelRepository.save(tspVehicleModel);
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、车辆一级车型 ").append(dto.getVehicleModelName()).append(" 导入成功");
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>").append(successNum).append("、车辆一级车型 ").append(dto.getVehicleModelName()).append(" 已存在");
                    }
                }
            }catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、车辆一级车型 " + dto.getVehicleModelName() + " 导入失败：";
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

    @SneakyThrows
    public String importVehicleModelStd(MultipartFile multipartFile, Boolean isUpdateSupport) {
        ExcelUtil<TspVehicleStdModelExportListDTO> util = new ExcelUtil<>(TspVehicleStdModelExportListDTO.class);
        List<TspVehicleStdModelExportListDTO> dtos = util.importExcel(multipartFile.getInputStream());
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TspVehicleStdModelExportListDTO dto : dtos) {
            try {
                // 数据校验
                Map<String,Object> checkMap = toCheckStdModel(dto, failureMsg, failureNum);
                failureNum = (Integer) checkMap.get("failureNum");
                failureMsg = (StringBuilder) checkMap.get("failureMsg");
                if (failureNum == 0) {
                TspVehicleModel tspVehicleModel = tspVehicleModelRepository.getByVehicleModelName(dto.getVehicleModelName());
                TspVehicleStdModel tspVehicleStdModel = tspVehicleStdModeRepository.getByStdModeNameAndModelId(dto.getStdModeName(),tspVehicleModel.getId());
                // 新增
                if (tspVehicleModel == null) {
                    failureNum++;
                    failureMsg.append("<br/>").append(successNum).append("、车辆一级车型 ").append(dto.getVehicleModelName()).append(" 不存在");
                } else {
                    // 二级车型
                    if (tspVehicleStdModel == null) {
                        tspVehicleStdModel = new TspVehicleStdModel();
                        BeanUtils.copyProperties(dto, tspVehicleStdModel);
                        if ("纯电动".equals(dto.getDataType())) {
                            tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.BE_VS);
                        } else if ("混合动力".equals(dto.getDataType())) {
                            tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.HYBRID);
                        } else if ("燃料电池电动".equals(dto.getDataType())) {
                            tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.FUEL_CELL_ELECTRIC);
                        } else if ("插电式混动".equals(dto.getDataType())) {
                            tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.PLUG_IN_HYBRID);
                        } else if ("增程式混动".equals(dto.getDataType())) {
                            tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.INCREMENTAL_HYBRID);
                        }
                        if ((dto.getDataType() != null && !"".equals(dto.getDataType())) && tspVehicleStdModel.getDataKey() == null) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、车辆能源类型 ").append(dto.getDataType()).append(" 值异常");
                        }
                        else {
                            tspVehicleStdModel.setCreateBy(SecurityUtils.getUsername());
                            tspVehicleStdModel.setUpdateBy(SecurityUtils.getUsername());
                            tspVehicleStdModel.setTspVehicleModelId(tspVehicleModel.getId());
                            tspVehicleStdModeRepository.save(tspVehicleStdModel);
                            successNum++;
                            successMsg.append("<br/>").append(successNum).append("、车辆二级车型 ").append(dto.getStdModeName()).append(" 新增成功");
                        }
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、车辆二级车型 ").append(dto.getVehicleModelName()).append(" 已存在");
                    }
                }
                }
            }catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、二级车型 " + dto.getStdModeName() + " 导入失败：";
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

    private Map<String,Object> toCheckStdModel(TspVehicleStdModelExportListDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String,Object> checkMap = new HashMap<>();
        // 一级车型
        if (dto.getVehicleModelName() == null || dto.getVehicleModelName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、一级车型不能为空值 ");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 二级车型
        if (dto.getStdModeName() == null || dto.getStdModeName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、二级车型不能为空值 ");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 公告型号
        if (dto.getNoticeModel() == null || dto.getNoticeModel().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、公告型号不能为空值 ");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 公共批次
        if (dto.getNoticeBatch() == null || dto.getNoticeBatch().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、公共批次不能为空值 ");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum",failureNum);
        checkMap.put("failureMsg",failureMsg);
        return checkMap;
    }

    public List<TspVehicleModel> selectList(Long tspVehicleModelId) {
        return tspVehicleModelRepository.selectList(tspVehicleModelId);
    }

    public void deletes(Long[] tspVehicleModelIds) {
        for (Long tspVehicleModelId : tspVehicleModelIds) {
            TspVehicleModel model = tspVehicleModelRepository.getById(tspVehicleModelId);
            List<TspVehicleStdModel> stdModels = tspVehicleStdModeRepository.getByTspVehicleModelId(model.getId());
            for (TspVehicleStdModel stdModel : stdModels) {
                tspVehicleStdModeRepository.removeById(stdModel.getId());
            }
            tspVehicleModelRepository.removeById(model.getId());
        }
    }

    public List<TspVehicleModelSelectDTO> selectChildrenList(TspVehiclePageListVO vo) {
        return tspVehicleModelRepository.selectChildrenList(vo);
    }

    public List<TspVehicleStdModelExListDTO> exportList(TspVehicleModelPageListVO vo) {
        vo.setPageNum(1);
        vo.setPageSize(Integer.MAX_VALUE);
        QueryWrapper<TspVehicleModel> ew = tspVehicleModelRepository.getStdModelList(vo);
        IPage<TspVehicleStdModelExListDTO> page = tspVehicleModelMapper.getPageList(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        List<TspVehicleStdModelExListDTO> dtos = page.getRecords();
        for (TspVehicleStdModelExListDTO dto : dtos) {
            Integer stdModelCount = tspVehicleRepository.countByTspVehicleStdModelId(dto.getTspStdModelId());
            dto.setVehicleCount(stdModelCount.toString());
            if (dto.getDataKey() != null) {
                dto.setDataType(dto.getDataKey().getKey());
            }
        }
        return dtos;
    }
}
