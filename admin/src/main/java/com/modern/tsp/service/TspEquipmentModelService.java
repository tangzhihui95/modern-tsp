package com.modern.tsp.service;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.FrontQuery;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.bean.BeanCopyUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.tsp.domain.TspEquipmentModel;
import com.modern.tsp.domain.TspEquipmentType;
import com.modern.tsp.mapper.TspEquipmentModelMapper;
import com.modern.tsp.mapper.TspEquipmentTypeMapper;
import com.modern.tsp.model.dto.TspEquipmentModelPageListDTO;
import com.modern.tsp.model.vo.TspEquipmentModelAddVO;
import com.modern.tsp.repository.TspEquipmentModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/15 11:50
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspEquipmentModelService extends TspBaseService {
    private final TspEquipmentModelRepository tspEquipmentModelRepository;
    private final TspEquipmentModelMapper tspEquipmentModelMapper;
    private final TspEquipmentTypeMapper tspEquipmentTypeMapper;
    public void add(TspEquipmentModelAddVO vo) {
        if (vo.getTspEquipmentTypeId() == null){
            throw new RuntimeException("设备分类ID不能为空");
        }
        TspEquipmentModel model = tspEquipmentModelRepository.getByModelName(vo.getModelName());
        if (model != null){
            ErrorEnum.TSP_EQUIPMENT_MODEL_NOT_NULL_ERR.throwErr();
        }
        // bean深拷贝工具
        TspEquipmentModel copy = BeanCopyUtils.oneCopy(vo, CopyOptions.create(), TspEquipmentModel.class);
        copy.setUpdateBy(SecurityUtils.getUsername());
        copy.setCreateBy(SecurityUtils.getUsername());
        tspEquipmentModelRepository.save(copy);
    }

    public void edit(TspEquipmentModelAddVO vo) {
        if (vo.getTspEquipmentModelId() == null){
            throw new RuntimeException("设备型号ID不能为空");
        }
        TspEquipmentModel nameNotId = tspEquipmentModelRepository.getByModelNameNotId(vo.getModelName(), vo.getTspEquipmentModelId());
        if (nameNotId != null && nameNotId.getTspEquipmentTypeId().equals(vo.getTspEquipmentTypeId())){
            ErrorEnum.TSP_EQUIPMENT_MODEL_NOT_NULL_ERR.throwErr();
        }
        TspEquipmentModel copy = BeanCopyUtils.oneCopy(vo, CopyOptions.create(), TspEquipmentModel.class);
        copy.setUpdateBy(SecurityUtils.getUsername());
        copy.setId(vo.getTspEquipmentModelId());
        tspEquipmentModelRepository.updateById(copy);
    }

    public void delete(Long tspEquipmentModelId) {
        int count = tspEquipmentTypeMapper.countByTspEquipmentModelId(tspEquipmentModelId);

        if (count > 0) {
            ErrorEnum.TSP_EQUIPMENT_MODEL_DELETE_ERR.throwErr();
        }
//        TspEquipmentModel equipmentModel = tspEquipmentModelRepository.getById(tspEquipmentModelId);
//        if (equipmentModel == null) {
//            ErrorEnum.TSP_EQUIPMENT_MODEL_NULL_ERR.throwErr();
//        }
        tspEquipmentModelMapper.deleteById(tspEquipmentModelId);
    }


    public PageInfo<TspEquipmentModelPageListDTO> getPageList(FrontQuery vo) {
        QueryWrapper<TspEquipmentModel> ew = tspEquipmentModelRepository.getPageList(vo);
        IPage<TspEquipmentModelPageListDTO> pageList = tspEquipmentModelMapper.getPageList(
                Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        return PageInfo.of(pageList);
    }
}
