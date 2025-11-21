package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspFenceVehicle;
import com.modern.tsp.mapper.TspFenceVehicleMapper;
import org.springframework.stereotype.Service;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 17:53
 * @Version 1.0.0
 */
@Service
public class TspFenceVehicleRepository extends ServicePlusImpl<TspFenceVehicleMapper, TspFenceVehicle, TspFenceVehicle> {

    public QueryWrapper<TspFenceVehicle> vehicleIn(Long tspVehicleId, Long tspFenceId) {
        QueryWrapper<TspFenceVehicle> ew = new QueryWrapper<>();
        ew.eq("tsp_vehicle_id",tspVehicleId);
        ew.eq("tsp_fence_id",tspFenceId);
        return ew;
    }

    public void deleteByFenceIdAndVehicleId(Long tspFenceId, Long tspVehicleId) {
        UpdateWrapper<TspFenceVehicle> ew = new UpdateWrapper<>();
        ew.eq("tsp_fence_id",tspFenceId);
        ew.eq("tsp_vehicle_id",tspVehicleId);
        ew.set("is_delete",1);
        ew.set("update_by", SecurityUtils.getUsername());
        this.update(ew);
    }

    public void deleteByFenceId(Long tspFenceId) {
        UpdateWrapper<TspFenceVehicle> ew = new UpdateWrapper<>();
        ew.eq("tsp_fence_id",tspFenceId);
        ew.set("is_delete",1);
        ew.set("update_by", SecurityUtils.getUsername());
        this.update(ew);
    }
}
