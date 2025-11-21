package com.modern.tsp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TspVehicleEquipment;
import com.modern.tsp.model.dto.TspEquipmentPageListDTO;
import com.modern.tsp.model.dto.TspVehiclePageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/14 14:16
 * @Version 1.0.0
 */
public interface TspVehicleEquipmentMapper extends BaseMapperPlus<TspVehicleEquipment> {

    @Select({
        "SELECT a.create_time,a.un_bind_time,b.sn,b.sim,b.icc_id,b.imei,CONCAT(d.name,' / ',c.model_name) AS 'modelName',a.upload_time FROM tsp_vehicle_equipment a " +
                "LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
                "LEFT JOIN tsp_equipment_model c ON b.tsp_equipment_model_id = c.id " +
                "LEFT JOIN tsp_equipment_type d ON c.tsp_equipment_type_id = d.id ${ew.customSqlSegment}"
    })
    IPage<TspEquipmentPageListDTO> getPageList(Page<TspEquipmentPageListDTO> page, @Param(Constants.WRAPPER) QueryWrapper<TspVehicleEquipment> ew);
}
