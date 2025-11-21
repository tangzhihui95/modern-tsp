package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.tsp.domain.TspVehicleIdentificationReceive;
import com.modern.tsp.mapper.TspVehicleIdentificationReceiveMapper;
import org.springframework.stereotype.Service;


/**
 * <p>
 * </p>
 *
 * @author nut
 * @since 2022-11-02
 */
@Service
public class TspVehicleIdentificationReceiveRepository extends ServicePlusImpl<TspVehicleIdentificationReceiveMapper, TspVehicleIdentificationReceive, TspVehicleIdentificationReceive> {

    public TspVehicleIdentificationReceive getByVin(String vin) {
        LambdaQueryWrapper<TspVehicleIdentificationReceive> ew = new LambdaQueryWrapper<>();
        ew.eq(TspVehicleIdentificationReceive::getVin, vin).orderByDesc(TspVehicleIdentificationReceive::getId).last("LIMIT 1");
        return this.getOne(ew);
    }
}
