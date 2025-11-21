package com.modern.tsp.controller;

import com.modern.tsp.mapper.SynModelMapper;
import com.modern.tsp.mapper.SynStdModelMapper;
import com.modern.tsp.mapper.SynVehicleMapper;
import com.modern.tsp.synchronize.SynModel;
import com.modern.tsp.synchronize.SynStdModel;
import com.modern.tsp.synchronize.SynVehicle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OTA同步TSP信息 -接口类
 * @author WS
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/synchronize")
@Api(tags = "TSP - OTA同步TSP车辆与车型接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SynchronizeController {
    private final SynVehicleMapper synVehicleMapper;
    private final SynStdModelMapper synStdModelMapper;
    private final SynModelMapper synModelMapper;

    @ApiOperation("车辆同步")
    @PostMapping("/vehicle")
    public List<SynVehicle> vehicleSynchronize(){
        return synVehicleMapper.query();
    }

    @ApiOperation("二级车型同步")
    @PostMapping("/stdModel")
    public List<SynStdModel> stdModelSynchronize(){
        return synStdModelMapper.selectList(null);
    }

    @ApiOperation("一级车型同步")
    @PostMapping("/model")
    public List<SynModel> modelSynchronize(){
        return synModelMapper.selectList(null);
    }
}
