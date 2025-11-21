package com.modern.exInterface.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.exInterface.entity.VehicleGps;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * vehicle_gps;车辆位置数据 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
public interface VehicleGpsMapper extends BaseMapperPlus<VehicleGps> {

    @Select(value = {"SELECT t.id,t.latitude,t.longitude,t.collect_time FROM vehicle_gps t WHERE  t.deleted = 0"})
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    List<VehicleGps> findAll();

    @Select(value = {
        "SELECT t.*  FROM " +
                "vehicle_gps t " +
                "LEFT JOIN tsp_vehicle a ON t.vin = a.vin " +
                "LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
                "LEFT JOIN tsp_vehicle_license c ON t.vin = c.vin ${ew.customSqlSegment}"
    })
    List<VehicleGps> findHistory(@Param(Constants.WRAPPER) Wrapper<VehicleGps> ew);

    @Select(value = {
            "select vin from vehicle_gps where deleted = 0"
    })
    List<String> findVin();
    @Select(value = {
            "select vin from vehicle_gps where deleted = 0 and id = ${id}"
    })
    String getVinById(@Param("id") Long id);
    @Select(value = {
            "SELECT a.id,a.latitude, a.longitude, MAX(a.collect_time) collect_time " +
                    "FROM tsp_vehicle t LEFT JOIN vehicle_gps a ON t.vin = a.vin ${ew.customSqlSegment}"
    })
    List<VehicleGps> findAllfindAllByMaxCollectTime(@Param(Constants.WRAPPER) Wrapper<VehicleGps> ew);

    @Select(value = {
            "SELECT id,vin,gps_type,longitude,latitude,data_type,collect_time,create_time,deleted FROM vehicle_gps WHERE vin = " + "'" + "${vin}" + "'" + " order by collect_time desc LIMIT 0,1"
    })
    VehicleGps getGpsByCollerctTime(String vin);
}
