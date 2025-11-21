package com.modern.xtsp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.modern.xtsp.domain.TspVehicleSysRole;
import com.modern.xtsp.domain.dto.TspVehicleSysRoleDTO;
import com.modern.xtsp.domain.vo.TspVehicleSysRoleVO;

import java.util.List;

/**
 * @author kelly
 * @description 针对表【tsp_vehicle_sys_role(角色车辆关联表)】的数据库操作Service
 * @createDate 2024-08-11 12:16:27
 */
public interface XTspVehicleSysRoleService extends IService<TspVehicleSysRole> {

    List<TspVehicleSysRoleVO> getTspVehicleSysRoleVOByRole(Long roleId);

    List<TspVehicleSysRoleVO> getTspVehicleSysRoleVOOfAll();

    Boolean saveOrUpdateByRole(List<TspVehicleSysRoleDTO> tspVehicleSysRoleDTOs);
}
