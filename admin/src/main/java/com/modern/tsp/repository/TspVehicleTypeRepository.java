package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.tsp.domain.TspVehicleType;
import com.modern.tsp.mapper.TspVehicleTypeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 17:18
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspVehicleTypeRepository extends ServicePlusImpl<TspVehicleTypeMapper, TspVehicleType,TspVehicleType> {
    public TspVehicleType getByTypeName(String typeName) {
        QueryWrapper<TspVehicleType> ew = new QueryWrapper<>();
        ew.eq(TspVehicleType.TYPE_NAME,typeName);
        return this.getOne(ew);
    }

    public List<TspVehicleType> selectList(Long tspVehicleTypeId) {
        QueryWrapper<TspVehicleType> ew = new QueryWrapper<>();
        if (tspVehicleTypeId != null){
            ew.notIn(TspVehicleType.ID,tspVehicleTypeId);
        }
        return this.list(ew);
    }
}
