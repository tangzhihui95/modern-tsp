package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.FrontQuery;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.domain.TspEquipmentModel;
import com.modern.tsp.domain.TspEquipmentType;
import com.modern.tsp.mapper.TspEquipmentTypeMapper;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.TspEquipmentTypeAddVO;
import com.modern.tsp.repository.TspEquipmentModelRepository;
import com.modern.tsp.repository.TspEquipmentTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 摩登 - TSP - 设备分类 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspEquipmentTypeService extends TspBaseService {
    private final TspEquipmentModelService tspEquipmentModelService;
    private final TspEquipmentTypeRepository tspEquipmentTypeRepository;
    private final TspEquipmentModelRepository tspEquipmentModelRepository;
    private final TspEquipmentTypeMapper tspEquipmentTypeMapper;

    public PageInfo<TspEquipmentTypePageListDTO> getPageList(FrontQuery vo) {
        List<TspEquipmentTypePageListDTO> dtos = new ArrayList<>();

        QueryWrapper<TspEquipmentType> ew = new QueryWrapper<>();
        //按照设备类型查询设备先查询限定范围
        if(Objects.nonNull(vo.getTspEquipmentModelId())){
            TspEquipmentModel tspEquipmentModel = tspEquipmentModelRepository.getById(vo.getTspEquipmentModelId());
            ew.eq(TspEquipmentType.ID,tspEquipmentModel.getTspEquipmentTypeId());
        } else if (Objects.nonNull(vo.getTspEquipmentTypeId())) {
            ew.eq(TspEquipmentType.ID,vo.getTspEquipmentTypeId());
        }

        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like(TspEquipmentType.NAME, vo.getSearch())
                        .or().like(TspEquipmentType.EXTRA_TYPE, vo.getSearch()));
        ew.orderByDesc(TspEquipmentType.ID);
        Page<TspEquipmentType> page = tspEquipmentTypeRepository.page(new Page<>(vo.getPageNum(), vo.getPageSize()), ew);
        for (TspEquipmentType type : page.getRecords()) {
            TspEquipmentTypePageListDTO dto = new TspEquipmentTypePageListDTO();
            BeanUtils.copyProperties(type, dto);
            int count = tspEquipmentTypeMapper.countByTspEquipmentTypeId(type.getId());
            List<TspEquipmentModel> modelList;
            if (vo.getTspEquipmentModelId() != null) {
                modelList = tspEquipmentModelRepository.findByTspModelId(type.getId(), vo.getTspEquipmentModelId());
            } else {
                modelList = tspEquipmentModelRepository.findByTspEquipmentTypeId(type.getId());
            }
            List<TspEquipmentModelPageListDTO> modelDTOs = new ArrayList<>();
            for (TspEquipmentModel tspEquipmentModel : modelList) {
                int modelCount = tspEquipmentTypeMapper.countByTspEquipmentModelId(tspEquipmentModel.getId());
                TspEquipmentModelPageListDTO modelDTO = new TspEquipmentModelPageListDTO();
                BeanUtils.copyProperties(tspEquipmentModel,modelDTO);
                modelDTO.setCount(modelCount);
                modelDTOs.add(modelDTO);
            }
            if (CollectionUtils.isNotEmpty(modelList)) {
                dto.setChildren(modelDTOs);
                dto.setCount(count);
            }
            dtos.add(dto);
        }
        return PageInfo.of(dtos, vo.getPageNum(), vo.getPageSize(), page.getTotal());
    }

    public void add(TspEquipmentTypeAddVO vo) {
        TspEquipmentType type = tspEquipmentTypeRepository.getByName(vo.getName(), vo.getExtraType());
        if (type != null) {
            ErrorEnum.TSP_EQUIPMENT_TYPE_NOT_NULL_ERR.throwErr();
        }
        type = new TspEquipmentType();
        BeanUtils.copyProperties(vo, type);
        type.setCreateBy(SecurityUtils.getUsername());
        type.setUpdateBy(SecurityUtils.getUsername());
        tspEquipmentTypeRepository.save(type);
    }


    public void edit(TspEquipmentTypeAddVO vo) {
//        if (null == vo.getEquipmentTypeId()) {
//            throw new RuntimeException("设备分类ID不能为空");
//        }
//        TspEquipmentType equipmentType = tspEquipmentTypeRepository.getById(vo.getEquipmentTypeId());
//        if (equipmentType == null) {
//            ErrorEnum.TSP_EQUIPMENT_TYPE_NULL_ERR.throwErr();
//        }
//
//        QueryWrapper<TspEquipmentType> ew = new QueryWrapper<>();
//        ew.in(TspEquipmentType.NAME, vo.getName(),TspEquipmentType.EXTRA_TYPE,vo.getExtraType());
//        ew.ne(TspEquipmentType.ID, vo.getEquipmentTypeId());
//        List<TspEquipmentType> list = tspEquipmentTypeRepository.list(ew);
//        if (CollectionUtils.isNotEmpty(list)) {
//            ErrorEnum.TSP_EQUIPMENT_TYPE_EXTRA_NOT_NULL_ERR.throwErr();
//        }
//        BeanUtils.copyProperties(vo, equipmentType);
//        equipmentType.setUpdateBy(SecurityUtils.getUsername());
//        tspEquipmentTypeRepository.updateById(equipmentType)

        if (null == vo.getEquipmentTypeId()) {
            throw new RuntimeException("设备分类ID不能为空");
        }
        TspEquipmentType equipmentType = tspEquipmentTypeRepository.getById(vo.getEquipmentTypeId());
        if (equipmentType == null) {
            ErrorEnum.TSP_EQUIPMENT_TYPE_NULL_ERR.throwErr();
        }

        QueryWrapper<TspEquipmentType> ew = new QueryWrapper<>();
        ew.eq(TspEquipmentType.NAME, vo.getName());
        ew.notIn(TspEquipmentType.ID, vo.getEquipmentTypeId());
        TspEquipmentType one = tspEquipmentTypeRepository.getOne(ew);
        if (one != null) {
            ErrorEnum.TSP_EQUIPMENT_TYPE_NOT_NULL_ERR.throwErr();
        }
        BeanUtils.copyProperties(vo, equipmentType);
        equipmentType.setUpdateBy(SecurityUtils.getUsername());
        tspEquipmentTypeRepository.updateById(equipmentType);



    }

    public void delete(Long equipmentTypeId) {
        TspEquipmentType equipmentType = tspEquipmentTypeRepository.getById(equipmentTypeId);
        if (equipmentType == null) {
            ErrorEnum.TSP_EQUIPMENT_TYPE_NULL_ERR.throwErr();
        }
        List<Long> models = tspEquipmentModelRepository.findByTspEquipmentTypeId(equipmentTypeId)
                .stream().map(TspEquipmentModel::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(models)) {
            ErrorEnum.TSP_EQUIPMENT_TYPE_DELETE_ERR.throwErr();
        }
//        List<TspEquipment> equipments = tspEquipmentRepository.findInEquipmentTypeId(models);
//        if (!CollectionUtils.isEmpty(equipments)){
//            ErrorEnum.TSP_EQUIPMENT_MODEL_DELETE_ERR.throwErr();
//        }
        tspEquipmentTypeRepository.removeById(equipmentTypeId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deletes(Long[] equipmentTypeIds) {
        if (equipmentTypeIds.length == 0) {
            throw new RuntimeException("设备分类ID不能为空");
        }
        for (Long equipmentTypeId : equipmentTypeIds) {
            TspEquipmentType equipmentType = tspEquipmentTypeRepository.getById(equipmentTypeId);
            if (equipmentType == null) {
                ErrorEnum.TSP_EQUIPMENT_TYPE_NULL_ERR.throwErr();
            }
            List<Long> models = tspEquipmentModelRepository.findByTspEquipmentTypeId(equipmentTypeId)
                    .stream().map(TspEquipmentModel::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(models)) {
                ErrorEnum.TSP_EQUIPMENT_TYPE_DELETE_ERR.throwErr();
            }
//            List<TspEquipment> equipments = tspEquipmentRepository.findInEquipmentTypeId(models);
//            if (!CollectionUtils.isEmpty(equipments)){
//                ErrorEnum.TSP_EQUIPMENT_MODEL_DELETE_ERR.throwErr();
//            }
//            List<TspEquipment> equipments = tspEquipmentRepository.findByEquipmentTypeId(equipmentTypeId);
//            if (!CollectionUtils.isEmpty(equipments)){
//                ErrorEnum.TSP_EQUIPMENT_TYPE_DELETE_ERR.throwErr();
//            }
            tspEquipmentTypeRepository.removeById(equipmentTypeId);
        }
    }

    // 设备分类 - 数据传输对象 - 下拉列表"
    public List<TspEquipmentTypeSelectDTO> selectList(FrontQuery vo) {
        return tspEquipmentTypeRepository.selectList(vo);

    }

    public List<TspEquipmentTypeExcelDTO> exportList(FrontQuery vo) {
        vo.setPageNum(1);
        vo.setPageSize(Integer.MAX_VALUE);
        ArrayList<TspEquipmentTypeExcelDTO> dtos = new ArrayList<>();
        List<TspEquipmentModelPageListDTO> list = tspEquipmentModelService.getPageList(vo).getList();
        for (TspEquipmentModelPageListDTO tspEquipmentModelPageListDTO : list) {
            int count = tspEquipmentTypeMapper.countByTspEquipmentModelId(tspEquipmentModelPageListDTO.getId());
            TspEquipmentTypeExcelDTO dto = new TspEquipmentTypeExcelDTO();
            BeanUtils.copyProperties(tspEquipmentModelPageListDTO, dto);
            dto.setCount(count);
            dtos.add(dto);
        }
        return dtos;
    }

    @SneakyThrows
    public String importEquipmentModel(MultipartFile file, Boolean isUpdateSupport) {
        ExcelUtil<TspEquipmentTypeExcelDTO> util = new ExcelUtil<>(TspEquipmentTypeExcelDTO.class);
        List<TspEquipmentTypeExcelDTO> dtos = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TspEquipmentTypeExcelDTO dto : dtos) {
            try {
                // 数据校验
                Map<String, Object> checkMap = toCheckModel(dto, failureMsg, failureNum);
                failureNum = (Integer) checkMap.get("failureNum");
                failureMsg = (StringBuilder) checkMap.get("failureMsg");
                if (failureNum == 0) {
                    TspEquipmentType tspEquipmentType = tspEquipmentTypeRepository.getByNameAndExtraType(dto.getName(), dto.getExtraType());
                    TspEquipmentModel tspEquipmentModel;
                    if (tspEquipmentType == null) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、设备类型 ").append(dto.getExtraType()).append(" 不存在");
                    } else {
//                    tspEquipmentType.setIsTerminal(dto.getIsTerminal());
//                    tspEquipmentType.setUpdateBy(SecurityUtils.getUsername());
//                    tspEquipmentTypeRepository.updateById(tspEquipmentType);
                        tspEquipmentModel = tspEquipmentModelRepository.getByModelNameAndType(dto.getModelName(), tspEquipmentType.getId());
                        if (tspEquipmentModel != null) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、设备类型下的型号 ").append(dto.getModelName()).append(" 已存在");
                        } else {
                            tspEquipmentModel = new TspEquipmentModel();
                            BeanUtils.copyProperties(dto, tspEquipmentModel);
                            tspEquipmentModel.setTspEquipmentTypeId(tspEquipmentType.getId());
                            tspEquipmentModel.setCreateBy(SecurityUtils.getUsername());
                            tspEquipmentModel.setUpdateBy(SecurityUtils.getUsername());
                            tspEquipmentModelRepository.save(tspEquipmentModel);
                            successNum++;
                            successMsg.append("<br/>").append(successNum).append("、设备型号 ").append(dto.getModelName()).append(" 更新成功");
                        }
                    }
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、设备类型 " + dto.getExtraType() + " 导入失败：";
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

    private Map<String, Object> toCheckModel(TspEquipmentTypeExcelDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        // 设备类型
        if (dto.getName() == null || dto.getName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备类型 ").append(dto.getName()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 设备扩展信息
        if (dto.getExtraType() == null || dto.getExtraType().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备扩展信息 ").append(dto.getExtraType()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 设备型号
        if (dto.getModelName() == null || dto.getModelName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备型号 ").append(dto.getModelName()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 供应商
        if (dto.getSuppliers() == null || dto.getSuppliers().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、供应商 ").append(dto.getSuppliers()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 生产批次
        if (dto.getBatchNumber() == null || dto.getBatchNumber().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、生产批次 ").append(dto.getBatchNumber()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", failureNum);
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

    private Map<String, Object> toCheckType(TspEquipmentTypeImportDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        // 设备类型
        if (dto.getName() == null || dto.getName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备类型 ").append(dto.getName()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 设备扩展信息
        if (dto.getExtraType() == null || dto.getExtraType().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备扩展信息 ").append(dto.getExtraType()).append(" 不能为空");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        // 是否为终端
        if ((dto.getTerminal() != null && !dto.getTerminal().equals("")) && (!"是".equals(dto.getTerminal()) && !"否".equals(dto.getTerminal()))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、是否为终端 ").append(dto.getTerminal()).append(" 值异常");
            checkMap.put("failureNum", failureNum);
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", failureNum);
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

    @SneakyThrows
    public String importEquipmentType(MultipartFile file, Boolean isUpdateSupport) {
        ExcelUtil<TspEquipmentTypeImportDTO> util = new ExcelUtil<>(TspEquipmentTypeImportDTO.class);
        List<TspEquipmentTypeImportDTO> dtos = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TspEquipmentTypeImportDTO dto : dtos) {
            try {
                // 数据校验
                Map<String, Object> checkMap = toCheckType(dto, failureMsg, failureNum);
                failureNum = (Integer) checkMap.get("failureNum");
                failureMsg = (StringBuilder) checkMap.get("failureMsg");
                if (failureNum == 0) {
                    TspEquipmentType tspEquipmentType = tspEquipmentTypeRepository.getByNameAndExtraType(dto.getName(), dto.getExtraType());
                    if (tspEquipmentType != null) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、设备类型 ").append(dto.getName()).append(" 已存在");
                    } else {
                        tspEquipmentType = new TspEquipmentType();
                        BeanUtils.copyProperties(dto, tspEquipmentType);
                        if (dto.getTerminal() == null || "".equals(dto.getTerminal()) || dto.getTerminal().equals("是")) {
                            tspEquipmentType.setIsTerminal(true);
                        }
                        else {
                            tspEquipmentType.setIsTerminal(false);
                        }
                        tspEquipmentType.setCreateBy(SecurityUtils.getUsername());
                        tspEquipmentType.setUpdateBy(SecurityUtils.getUsername());
                        tspEquipmentTypeRepository.save(tspEquipmentType);
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、设备类型 ").append(dto.getName()).append(" 添加成功");
                    }
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、设备类型 " + dto.getExtraType() + " 导入失败：";
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
}
