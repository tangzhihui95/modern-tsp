package com.modern.xtsp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.modern.tsp.domain.TspVehicle;

import java.util.List;

public interface XTspVehicleService extends IService<TspVehicle> {
    List<TspVehicle> getAllVehiclesByCurrentUser();

    List<TspVehicle> getAllVehiclesBySysUserId(Long sysUserId);

    List<TspVehicle> getAllVehiclesBySysRoleId(Long sysRoleId);

    List<TspVehicle> getAllVehicles();
}
