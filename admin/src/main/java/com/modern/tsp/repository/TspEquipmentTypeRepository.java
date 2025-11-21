package com.modern.tsp.repository;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.core.domain.FrontQuery;
import com.modern.common.utils.bean.BeanCopyUtils;
import com.modern.tsp.domain.TspEquipmentModel;
import com.modern.tsp.domain.TspEquipmentType;
import com.modern.tsp.mapper.TspEquipmentTypeMapper;
import com.modern.tsp.model.dto.TspEquipmentTypeSelectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 15:04
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspEquipmentTypeRepository extends ServicePlusImpl<TspEquipmentTypeMapper, TspEquipmentType,TspEquipmentType> {
    private final TspEquipmentModelRepository tspEquipmentModelRepository;
    public TspEquipmentType getByName(String tspEquipmentName) {
        QueryWrapper<TspEquipmentType> ew = new QueryWrapper<>();
        ew.eq(TspEquipmentType.NAME,tspEquipmentName);
        return this.getOne(ew);
    }

    public TspEquipmentType getByNameAndExtraType(String tspEquipmentName,String extraType) {
        QueryWrapper<TspEquipmentType> ew = new QueryWrapper<>();
        ew.eq(TspEquipmentType.NAME,tspEquipmentName);
        ew.eq(TspEquipmentType.EXTRA_TYPE,extraType);
        return this.getOne(ew);
    }

    public List<TspEquipmentTypeSelectDTO> selectList(FrontQuery vo) {
        ArrayList<TspEquipmentTypeSelectDTO> dtos = new ArrayList<>();
        List<TspEquipmentType> types = lambdaQuery()
                .eq(TspEquipmentType::getIsDelete,0)
                .list();
        TspEquipmentTypeSelectDTO selectDTO = new TspEquipmentTypeSelectDTO();
        if (vo.getNeedAll() != null && vo.getNeedAll() == 1) {
            selectDTO.setLabel("全部");
            selectDTO.setValue(new Long(0));
            dtos.add(selectDTO);
        }
        for (TspEquipmentType type : types) {
            TspEquipmentTypeSelectDTO dto = new TspEquipmentTypeSelectDTO();
            dto.setLabel(type.getName());
            dto.setValue(type.getId());
            dto.setChildren(this.supplierChildren(type.getId()));
            dtos.add(dto);
        }
        return dtos;
    }

    public List<TspEquipmentTypeSelectDTO> supplierChildren(Long tspEquipmentTypeId){
        List<TspEquipmentModel> models = tspEquipmentModelRepository.findByTspEquipmentTypeId(tspEquipmentTypeId);
        ArrayList<TspEquipmentTypeSelectDTO> suppliersDTOS = new ArrayList<>();
        for (TspEquipmentModel model : models) {
            TspEquipmentTypeSelectDTO dto = new TspEquipmentTypeSelectDTO();
            ArrayList<TspEquipmentTypeSelectDTO> modelDTOS = new ArrayList<>();
            TspEquipmentTypeSelectDTO modelDTO = new TspEquipmentTypeSelectDTO();
            modelDTO.setLabel(model.getModelName());
            modelDTO.setValue(model.getId());
            modelDTOS.add(modelDTO);
            dto.setLabel(model.getSuppliers());
            dto.setValue(model.getId());
            dto.setChildren(modelDTOS);
            suppliersDTOS.add(dto);
        }
        return suppliersDTOS;
    }


    public TspEquipmentType getByName(String name, @NotNull(message = "设备扩展信息不能为空") String extraType) {
        QueryWrapper<TspEquipmentType> ew = new QueryWrapper<>();
        ew.eq(TspEquipmentType.NAME,name);
        ew.eq(TspEquipmentType.EXTRA_TYPE,extraType);
        return this.getOne(ew);
    }
}
