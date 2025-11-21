package com.modern.tsp.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.tsp.synchronize.SynVehicle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * OTA同步TSP车辆 Mapper
 * @author WS
 * @since 2023-05-15
 */
@Mapper
public interface SynVehicleMapper extends BaseMapper<SynVehicle> {
    List<SynVehicle> query();
}
