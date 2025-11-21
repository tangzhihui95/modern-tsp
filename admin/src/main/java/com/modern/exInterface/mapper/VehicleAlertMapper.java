package com.modern.exInterface.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.exInterface.entity.VehicleAlert;
import com.modern.exInterface.model.dto.VehicleAlertPageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * vehicle_alert;报警数据 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
public interface VehicleAlertMapper extends BaseMapperPlus<VehicleAlert> {
    @Select({"SELECT " +
            "t.* " +
            "FROM " +
            "vehicle_alert t " +
            "RIGHT JOIN tsp_vehicle a ON t.vin = a.vin AND a.vin is not null " +
            "LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
            "LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment}"})
    IPage<VehicleAlert> getPageList(Page<VehicleAlert> page, @Param(Constants.WRAPPER) Wrapper<VehicleAlert> ew);

    @Select({"SELECT DISTINCT " +
            "t.* " +
            "FROM " +
            "vehicle_alert t " +
            "RIGHT JOIN tsp_vehicle a ON t.vin = a.vin AND a.vin is not null " +
            "LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
            "LEFT JOIN tsp_vehicle_market d on d.tsp_vehicle_id = a.id " +
            "LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment}"})
    List<VehicleAlert> findAllAlertData(@Param(Constants.WRAPPER) Wrapper<VehicleAlert> ew);

    @Select({"SELECT " +
            "t.*,a.configure_name,c.plate_code,b.sn " +
            "FROM " +
            "vehicle_alert t " +
            "LEFT JOIN tsp_vehicle a ON t.vin = a.vin AND a.vin is not null " +
            "LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
            "LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment}"})
    IPage<VehicleAlertPageListDTO> getHistoryPageList(Page<VehicleAlert> page, @Param(Constants.WRAPPER) Wrapper<VehicleAlert> ew);

}
