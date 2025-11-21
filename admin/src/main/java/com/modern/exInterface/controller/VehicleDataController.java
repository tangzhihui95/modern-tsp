package com.modern.exInterface.controller;

import com.modern.common.core.Result;
import com.modern.exInterface.model.dto.VehicleGpsCountListDTO;
import com.modern.exInterface.service.VehicleDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/14 19:37
 * @Version 1.0.0
 */
@RestController
@Api(tags = "TSP - 车辆数据")
@RequestMapping("/tsp/vehicle/data")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleDataController {

    private final VehicleDataService vehicleDataService;

    @ApiOperation("车辆基本属性-通过vin查询")
    @GetMapping("/basicAttributes/{vin}")
    public Result<Map<String,Object>> basicAttributes(@PathVariable("vin")String vin){
        return Result.data(vehicleDataService.getBasicAttributes(vin));
    }

    @ApiOperation("最新实时数据-通过vin查询")
    @GetMapping("/latestData/{vin}")
    public Result<Map<String,Object>> latestData(@PathVariable("vin")String vin){
        return Result.data(vehicleDataService.getLatestData(vin));
    }

    @ApiOperation("当前用户所有车辆最新实时数据")
    @GetMapping("/userVehicles/latestData")
    public Result<List<VehicleGpsCountListDTO>> userVehiclesLatestData(){
        return Result.data(vehicleDataService.getUserVehiclesLatestData());
    }
}
