package com.modern.exInterface.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.exInterface.entity.VehicleIntegrate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * vehicle_integrate;整车数据 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
public interface VehicleIntegrateMapper extends BaseMapperPlus<VehicleIntegrate> {

//    @Select({"SELECT DISTINCT " +
//            "t.*,t.collect_time % 10 " +
//            "FROM " +
//            "vehicle_integrate t " +
//            "RIGHT JOIN tsp_vehicle a ON t.vin = a.vin AND a.vin is not NULL " +
//            "LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
//            "LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment}"})
//    IPage<VehicleIntegrate> getPageList(Page<VehicleIntegrate> page,
//                                        @Param(Constants.WRAPPER) Wrapper<VehicleIntegrate> ew);

    @Select({
            "SELECT t.id,t.vin,t.vehicle_state,t.charge_state,t.run_mode,t.speed,t.mileage,t.total_voltage,t.total_current,t.soc,t.dc_dc_state,t.gear,t.insulation_resistance,t.accelerator_pedal_position,t.brake_pedal_position,t.data_type,t.collect_time,t.create_time" +
//              "SELECT t.*" +
                    " FROM vehicle_integrate t" +
                    " LEFT  JOIN tsp_vehicle a ON t.vin = a.vin AND a.vin IS NOT NULL" +
                    " LEFT  JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
                    " LEFT  JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id" +
                    " ${ew.customSqlSegment}"
//                    "WHERE t.vin =  '${vin}' " +
//                    "AND (t.collect_time  BETWEEN collect_time = ${collect_time}  AND collect_time = ${collect_time})"
    })

    IPage<VehicleIntegrate> getPageList(Page<VehicleIntegrate> page,
                                        @Param(Constants.WRAPPER) Wrapper<VehicleIntegrate> ew);

    List<VehicleIntegrate> findByDateAndVinList(@Param("date") String date, @Param("vinList") List<String> vinList);

    VehicleIntegrate findByBeforeDateAndEqVin(@Param("date") String date, @Param("vin") String vin);

}
