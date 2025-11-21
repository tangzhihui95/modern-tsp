package com.modern.tsp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspFenceVehicle;
import com.modern.tsp.model.dto.TspVehiclePageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 17:54
 * @Version 1.0.0
 */
public interface TspFenceVehicleMapper extends BaseMapperPlus<TspFenceVehicle> {

    @Select({
            "SELECT t.*,a.*,b.*,c.*,d.* FROM tsp_vehicle t " +
                    "LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id " +
                    "LEFT JOIN tsp_user b ON t.tsp_user_id = b.id " +
                    "LEFT JOIN tsp_equipment c ON t.tsp_equipment_id = c.id " +
                    "LEFT JOIN tsp_vehicle_license d ON t.id = d.tsp_vehicle_id and d.is_delete = 0 " +
                    "LEFT JOIN tsp_fence_vehicle e ON t.id = e.tsp_vehicle_id and e.is_delete = 0 " +
                    "WHERE t.is_delete = 0 and e.tsp_fence_id = #{tspFenceId}"
    })
    List<TspVehiclePageListDTO> getVehicleList(Long tspFenceId);

    @Select({
            "SELECT * FROM tsp_fence_vehicle ${ew.customSqlSegment}"
    })
    TspFenceVehicle vehicleIn(@Param(Constants.WRAPPER) QueryWrapper<TspFenceVehicle> ew);
}
