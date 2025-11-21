package com.modern.tsp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspEquipment;
import com.modern.tsp.domain.TspVehicleEquipment;
import com.modern.tsp.model.dto.TspEquipmentPageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 * 摩登 - TSP - 设备 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
public interface TspEquipmentMapper extends BaseMapperPlus<TspEquipment> {
    @Select({
            "SELECT t.id,tv.id as tsp_vehicle_id,tv.vin as tsp_vehicle_vin,tv.state,t.tsp_equipment_model_id,t.version,t.sn,t.icc_id,t.sim,t.imsi,t.imei,t.is_terminal,t.supplier_code,t.scrap_time,b.extra_type," +
                    "t.serial_number,t.operator,t.is_online,t.is_register,t.is_scrap,t.create_time,a.model_name,a.suppliers,b.id as tsp_equipment_type_id,b.name,CONCAT(b.name,' / ',a.model_name) as 'typeModel' FROM " +
                    "tsp_equipment t " +
                    "LEFT JOIN tsp_vehicle tv ON  t.id = tv.tsp_equipment_id and tv.is_delete = 0 " +
                    "LEFT JOIN tsp_equipment_model a ON t.tsp_equipment_model_id = a.id " +
                    "LEFT JOIN tsp_equipment_type b ON a.tsp_equipment_type_id = b.id ${ew.customSqlSegment}"
    })
    IPage<TspEquipmentPageListDTO> getPageList(Page<TspEquipmentPageListDTO> page, @Param(Constants.WRAPPER) Wrapper<TspEquipment> ew);

    @Select({
            "SELECT a.id FROM tsp_equipment a LEFT JOIN tsp_vehicle b ON a.id = b.tsp_equipment_id " +
                    "WHERE a.is_scrap = 0 AND a.is_delete = 0 AND b.is_delete = 0"
    })
    List<Long> getBindEquipments();

    @Select({
            "SELECT b.id,b.sn,b.sim,b.icc_id,b.imei,CONCAT(d.name,' / ',c.model_name) AS 'typeModel' " +
                    "FROM tsp_equipment b " +
                    "LEFT JOIN tsp_equipment_model c ON b.tsp_equipment_model_id = c.id " +
                    "LEFT JOIN tsp_equipment_type d ON c.tsp_equipment_type_id = d.id ${ew.customSqlSegment}"
    })
    IPage<TspEquipmentPageListDTO> getNowPageList(Page<TspEquipmentPageListDTO> page, @Param(Constants.WRAPPER) QueryWrapper<TspEquipment> ew);


    @Select({"SELECT COUNT(t.id) FROM tsp_equipment t " +
            "LEFT JOIN tsp_vehicle_equipment tve ON t.id = tve.tsp_equipment_id " +
            "LEFT JOIN tsp_vehicle tv ON  tve.tsp_vehicle_id = tv.id " +
            "LEFT JOIN tsp_equipment_model a ON t.tsp_equipment_model_id = a.id " +
            "LEFT JOIN tsp_equipment_type b ON a.tsp_equipment_type_id = b.id ${ew.customSqlSegment}"})
    Integer getCount(@Param(Constants.WRAPPER) QueryWrapper<TspEquipment> ew);
}
