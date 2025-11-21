package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.*;
import com.modern.tsp.mapper.TspVehicleModelMapper;
import com.modern.tsp.model.dto.TspEquipmentTypeSelectDTO;
import com.modern.tsp.model.dto.TspVehicleModelSelectDTO;
import com.modern.tsp.model.vo.TspVehicleModelPageListVO;
import com.modern.tsp.model.vo.TspVehiclePageListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 17:50
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EnableCaching
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleModelRepository extends ServicePlusImpl<TspVehicleModelMapper, TspVehicleModel,TspVehicleModel> {
    private final TspVehicleStdModeRepository tspVehicleStdModeRepository;
    public List<TspVehicleModel> findByTspVehicleTypeId(Long tspVehicleTypeId) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper<>();
        ew.eq(TspVehicleModel.TSP_VEHICLE_TYPE_ID,tspVehicleTypeId);
        return this.list(ew);
    }

    public TspVehicleModel getByVehicleModelName(String vehicleModelName) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper<>();
        ew.eq(TspVehicleModel.VEHICLE_MODEL_NAME,vehicleModelName);
        return this.getOne(ew);
    }

    public List<TspVehicleModel> selectList(Long tspVehicleModelId) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper<>();
        ew.eq(StringUtils.isNotNull(tspVehicleModelId),TspVehicleModel.ID,tspVehicleModelId);
        return this.list(ew);
    }

    public List<TspVehicleModel> getByParentModelId(Long id) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper<>();
        ew.eq(TspVehicleModel.PARENT_MODEL_ID,id);
        return this.list(ew);
    }

    public List<TspVehicleModelSelectDTO> selectChildrenList(TspVehiclePageListVO vo) {
        ArrayList<TspVehicleModelSelectDTO> dtos = new ArrayList<>();
        TspVehicleModelSelectDTO selectDTO = new TspVehicleModelSelectDTO();
        if (vo.getNeedAll() != null && vo.getNeedAll() == 1) {
            selectDTO.setLabel("全部");
            selectDTO.setValue(new Long(0));
            dtos.add(selectDTO);
        }
        List<TspVehicleModel> models = this.list();
        for (TspVehicleModel model : models) {
            TspVehicleModelSelectDTO dto = new TspVehicleModelSelectDTO();
            dto.setLabel(model.getVehicleModelName());
            dto.setValue(model.getId());
            dto.setChildren(this.supplierChildren(model.getId()));
            dtos.add(dto);
        }
        return dtos;
    }

    private List<TspVehicleModelSelectDTO> supplierChildren(Long tspVehicleModelId) {
        List<TspVehicleStdModel> models = tspVehicleStdModeRepository.getByTspVehicleModelId(tspVehicleModelId);
        ArrayList<TspVehicleModelSelectDTO> suppliersDTOS = new ArrayList<>();
        for (TspVehicleStdModel model : models) {
            TspVehicleModelSelectDTO dto = new TspVehicleModelSelectDTO();
            dto.setLabel(model.getStdModeName());
            dto.setValue(model.getId());
            suppliersDTOS.add(dto);
        }
        return suppliersDTOS;
    }

    public QueryWrapper<TspVehicleModel> getStdModelList(TspVehicleModelPageListVO vo) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper<>();
        ew.in(CollectionUtils.isNotEmpty(vo.getIds()),"a.id",vo.getIds());
        ew.eq(vo.getTspVehicleStdModelId() != null, "t.id",vo.getTspVehicleStdModelId());
        ew.like(StringUtils.isNotEmpty(vo.getVehicleModelName()),"a.vehicle_model_name",vo.getVehicleModelName());
        ew.orderByDesc("t.create_time");
        return ew;
    }

    @Override
//    @Cacheable(value = "TspVehicleModel", key = "methodName + #id")
    public TspVehicleModel getById(Serializable id) {
        return super.getById(id);
    }
}
