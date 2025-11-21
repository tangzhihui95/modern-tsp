package com.modern.xtsp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.xtsp.domain.TspVehicleSysRole;
import com.modern.xtsp.domain.vo.TspVehicleSysRoleVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kelly
 * @description 针对表【tsp_vehicle_sys_role(角色车辆关联表)】的数据库操作Mapper
 * @createDate 2024-08-11 12:16:27
 * @Entity com.modern.xtsp.domain.TspVehicleSysRole
 */
public interface XTspVehicleSysRoleMapper extends BaseMapper<TspVehicleSysRole> {

    List<TspVehicleSysRoleVO> queryTspVehicleSysRoleVOByRoleId(Long roleId);

    List<TspVehicleSysRoleVO> queryTspVehicleSysRoleVOOfAll();

    int deleteByRoleId(@Param("roleId") Long roleId, @Param("updateTime") LocalDateTime updateTime, @Param("updateBy") String updateBy);
}




