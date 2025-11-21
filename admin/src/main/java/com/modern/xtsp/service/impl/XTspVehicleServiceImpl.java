package com.modern.xtsp.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspVehicle;
import com.modern.xtsp.mapper.XTspVehicleMapper;
import com.modern.xtsp.service.XTspVehicleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableCaching
@Service
public class XTspVehicleServiceImpl extends ServiceImpl<XTspVehicleMapper, TspVehicle> implements XTspVehicleService {

    @Override
    public List<TspVehicle> getAllVehiclesByCurrentUser() {
        return getAllVehiclesBySysUserId(SecurityUtils.getUserId());
    }

    @Cacheable(value = "TspVehicle", key = "methodName + #sysUserId")
    @Override
    public List<TspVehicle> getAllVehiclesBySysUserId(Long sysUserId) {
        List<TspVehicle> tspVehicles;

        if (SecurityUtils.isAdmin(sysUserId)) {
            //是超级管理员，获取所有车辆
            tspVehicles = getAllVehicles();
        } else {
            //获取该用户对应所有角色下的所有车辆
            tspVehicles = getBaseMapper().selectAllVehiclesBySysUserId(sysUserId);
        }

        return tspVehicles;
    }

    @Cacheable(value = "TspVehicle", key = "methodName")
    @Override
    public List<TspVehicle> getAllVehicles() {
        return getBaseMapper().selectAllVehicles();
    }

    @Cacheable(value = "TspVehicle", key = "methodName + #sysRoleId")
    @Override
    public List<TspVehicle> getAllVehiclesBySysRoleId(Long sysRoleId) {
        Assert.notNull(sysRoleId);

        return getBaseMapper().selectAllVehiclesBySysRoleId(sysRoleId);
    }
}
