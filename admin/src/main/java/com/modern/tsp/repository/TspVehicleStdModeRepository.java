package com.modern.tsp.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.tsp.domain.TspVehicleStdModel;
import com.modern.tsp.mapper.TspVehicleStdModeMapper;
import com.modern.tsp.model.dto.TspVehicleExFactoryExcelDTO;
import com.modern.tsp.model.dto.TspVehicleStdModelLabelMapDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 15:03
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EnableCaching
@Service
public class TspVehicleStdModeRepository extends ServicePlusImpl<TspVehicleStdModeMapper, TspVehicleStdModel, TspVehicleStdModel> {
    @Autowired
    private TspVehicleStdModeMapper tspVehicleStdModeMapper;
    public List<TspVehicleStdModel> getByTspVehicleModelId(Long id) {
        LambdaQueryWrapper<TspVehicleStdModel> ew = new LambdaQueryWrapper<>();
        ew.eq(TspVehicleStdModel::getTspVehicleModelId,id);
        return list(ew);
    }

    public List<TspVehicleStdModel> findByTspVehicleModelId(Long tspVehicleModelId) {
        QueryWrapper<TspVehicleStdModel> ew = new QueryWrapper<>();
        ew.eq("tsp_vehicle_model_id",tspVehicleModelId);
        return this.list(ew);
    }

    public QueryWrapper<TspVehicleStdModel> getByStdModeName(String stdModeName,String vehicleModelName) {
        QueryWrapper<TspVehicleStdModel> ew = new QueryWrapper<>();
        ew.eq("a.std_mode_name",stdModeName);
        ew.eq("b.vehicle_model_name",vehicleModelName);
        return ew;
    }

    public TspVehicleStdModel getByStdModeNameAndModelId(String stdModeName,Long modelId) {
        QueryWrapper<TspVehicleStdModel> ew = new QueryWrapper<>();
        ew.eq("std_mode_name",stdModeName);
        ew.eq("tsp_vehicle_model_id",modelId);
        return this.getOne(ew);
    }

    public TspVehicleStdModelLabelMapDTO getLabelMap() {
        List<String> labelMap = tspVehicleStdModeMapper.getLabelMap();
        Map<String, String> map = new HashMap<>();
        String jsonStr = "";
        for (String s : labelMap) {
            map.put(s,s);
            jsonStr = JSON.toJSONString(map);
        }
        TspVehicleStdModelLabelMapDTO dto = new TspVehicleStdModelLabelMapDTO();
        dto.setCount("全部");
        dto.setDate("时间");
        dto.setModeNames(jsonStr);
        return dto;
    }

    public Wrapper<TspVehicleStdModel> getAllStdCount(String date) {
        QueryWrapper<TspVehicleStdModel> ew = new QueryWrapper<>();
        ew.groupBy("DATE_FORMAT( t.create_time, '%Y-%m-%d' )");
        ew.le("t.create_time",date + " 00:00:00");
        ew.ge("t.create_time",date + " 23:59:59");
        return ew;
    }

    @Override
//    @Cacheable(value = "TspVehicleStdModel", key = "methodName + #id")
    public TspVehicleStdModel getById(Serializable id) {
        return super.getById(id);
    }
}
