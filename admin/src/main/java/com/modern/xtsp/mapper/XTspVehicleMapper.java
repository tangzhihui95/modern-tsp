package com.modern.xtsp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.tsp.domain.TspVehicle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface XTspVehicleMapper extends BaseMapper<TspVehicle> {
    List<TspVehicle> selectAllVehiclesBySysUserId(@Param("sysUserId") Long sysUserId);

    List<TspVehicle> selectAllVehiclesBySysRoleId(@Param("sysRoleId") Long sysRoleId);

    List<TspVehicle> selectAllVehicles();
}
