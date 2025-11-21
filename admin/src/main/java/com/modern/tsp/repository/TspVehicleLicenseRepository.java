package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.tsp.domain.TspVehicleLicense;
import com.modern.tsp.mapper.TspVehicleLicenseMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 9:37
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EnableCaching
@Service
public class TspVehicleLicenseRepository extends ServicePlusImpl<TspVehicleLicenseMapper, TspVehicleLicense,TspVehicleLicense> {

//    @Cacheable(value = "TspVehicleLicense", key = "methodName + #tspVehicleId")
    public TspVehicleLicense getByTspVehicleId(Long tspVehicleId) {
        QueryWrapper<TspVehicleLicense> ew = new QueryWrapper<>();
        ew.eq("tsp_vehicle_id",tspVehicleId);
        return this.getOne(ew);
    }

    public TspVehicleLicense getByPlateCode(String plateCode) {
        QueryWrapper<TspVehicleLicense> ew = new QueryWrapper<>();
        ew.eq("plate_code",plateCode);
        return this.getOne(ew);
    }
}
