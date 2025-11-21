package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.tsp.domain.TspVehicleLicenseRecord;
import com.modern.tsp.mapper.TspVehicleLicenseRecordMapper;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 16:17
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class  TspVehicleLicenseRecordRepository extends ServicePlusImpl<TspVehicleLicenseRecordMapper, TspVehicleLicenseRecord, TspVehicleLicenseRecord> {
    public TspVehicleLicenseRecord getByTspVehicleId(Long tspVehicleId) {
        QueryWrapper<TspVehicleLicenseRecord> ew = new QueryWrapper<>();
        ew.eq(TspVehicleLicenseRecord.TSP_VEHICLE_ID,tspVehicleId);
        return this.getOne(ew);
    }
}
