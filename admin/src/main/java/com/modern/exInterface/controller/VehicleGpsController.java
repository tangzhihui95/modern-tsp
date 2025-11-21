package com.modern.exInterface.controller;


import com.modern.common.core.Result;
import com.modern.common.core.domain.BaseVO;
import com.modern.exInterface.model.dto.VehicleGpsCountListDTO;
import com.modern.exInterface.model.dto.VehicleGpsInfoDTO;
import com.modern.exInterface.model.dto.VehicleGpsParsedDTO;
import com.modern.exInterface.model.dto.VehicleGpsSearchSelectListDTO;
import com.modern.exInterface.model.vo.VehicleGpsHistoryVO;
import com.modern.exInterface.service.VehicleGpsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * vehicle_gps;车辆位置数据 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@RestController
@Api(tags = "TSP - 车辆位置数据")
@RequestMapping("/tsp/vehicle/gps")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleGpsController {

    private final VehicleGpsService vehicleGpsService;

    @ApiOperation("获取定位整车信息")
    @GetMapping("/getGpsInfo/{vin}")
    public Result<VehicleGpsInfoDTO> getGpsInfo(@PathVariable("vin") String vin) {

        return Result.data(vehicleGpsService.getGpsInfo(vin));
    }

    @ApiOperation("单车历史轨迹")
    @PostMapping("/findHistory")
    public Result<List<VehicleGpsParsedDTO>> findHistory(@RequestBody VehicleGpsHistoryVO vo) {
        return Result.data(vehicleGpsService.findHistory(vo));
    }

    @ApiOperation("获取定位整车信息下拉列表")
    @PostMapping("/selectList")
    public Result<List<VehicleGpsSearchSelectListDTO>> selectList(@RequestBody BaseVO vo) {
        return Result.data(vehicleGpsService.selectList(vo.getSearch()));
    }

}

