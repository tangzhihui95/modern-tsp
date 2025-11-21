package com.modern.xtsp.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.common.utils.SecurityUtils;
import com.modern.xtsp.domain.TspVehicleSysRole;
import com.modern.xtsp.domain.dto.TspVehicleSysRoleDTO;
import com.modern.xtsp.domain.vo.TspVehicleSysRoleVO;
import com.modern.xtsp.mapper.XTspVehicleSysRoleMapper;
import com.modern.xtsp.service.XTspVehicleSysRoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kelly
 * @description 针对表【tsp_vehicle_sys_role(角色车辆关联表)】的数据库操作Service实现
 * @createDate 2024-08-11 12:16:27
 */
@EnableCaching
@Service
public class XTspVehicleSysRoleServiceImpl extends ServiceImpl<XTspVehicleSysRoleMapper, TspVehicleSysRole>
        implements XTspVehicleSysRoleService {

    //    @Cacheable(value = "TspVehicle", key = "methodName + #roleId")
    @Override
    public List<TspVehicleSysRoleVO> getTspVehicleSysRoleVOByRole(Long roleId) {
        return getBaseMapper().queryTspVehicleSysRoleVOByRoleId(roleId);
    }

    @Override
    public List<TspVehicleSysRoleVO> getTspVehicleSysRoleVOOfAll() {
        return getBaseMapper().queryTspVehicleSysRoleVOOfAll();
    }

    @CacheEvict(value = "TspVehicle", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean saveOrUpdateByRole(List<TspVehicleSysRoleDTO> tspVehicleSysRoleDTOs) {
        //验证所有角色ID是否相同
        Assert.notEmpty(tspVehicleSysRoleDTOs);
        Long roleId = tspVehicleSysRoleDTOs.get(0).getRoleId();
        Assert.isTrue(ObjectUtil.isNotNull(roleId) && tspVehicleSysRoleDTOs.stream().allMatch(s -> ObjectUtil.equals(s.getRoleId(), roleId)));

        //delete old
        deleteByRoleId(roleId);

        //add new
        List<TspVehicleSysRole> tspVehicleSysRoles = tspVehicleSysRoleDTOs.stream().map(
                        s -> TspVehicleSysRole.builder().vehicleId(s.getVehicleId()).roleId(s.getRoleId()).build())
                .collect(Collectors.toList());
        return saveBatch(tspVehicleSysRoles);
    }

    private void deleteByRoleId(Long roleId) {
        LocalDateTime updateTime = LocalDateTime.now();
        String updateBy = SecurityUtils.getUsername();
        getBaseMapper().deleteByRoleId(roleId, updateTime, updateBy);
    }

}




