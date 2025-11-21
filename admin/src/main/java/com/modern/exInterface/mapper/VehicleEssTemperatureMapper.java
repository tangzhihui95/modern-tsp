package com.modern.exInterface.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.exInterface.entity.VehicleEssTemperature;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * vehicle_ess_temperature;可充电储能装置温度数据 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
public interface VehicleEssTemperatureMapper extends BaseMapperPlus<VehicleEssTemperature> {
    @Select({"SELECT " +
            "t.* " +
            "FROM " +
            "vehicle_ess_temperature t " +
            "LEFT JOIN tsp_vehicle a ON t.vin = a.vin " +
            "LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
            "LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment}"})
    IPage<VehicleEssTemperature> getPageList(Page<VehicleEssTemperature> page, @Param(Constants.WRAPPER) Wrapper<VehicleEssTemperature> ew);
}
