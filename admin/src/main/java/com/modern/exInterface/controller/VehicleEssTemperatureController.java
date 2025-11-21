package com.modern.exInterface.controller;


import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.exInterface.model.dto.VehicleEssTemperatureParsedDTO;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.exInterface.service.VehicleEssTemperatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * vehicle_ess_temperature;可充电储能装置温度数据 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@RestController
@Api(tags = "TSP - 可充电储能装置温度数据")
@RequestMapping("/tsp/vehicle/essTemperature")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleEssTemperatureController {

    private final VehicleEssTemperatureService vehicleEssTemperatureService;

    /**
     * 列表
     */
    @ApiOperation("分页列表")
//    @PreAuthorize("@ss.hasPermi('tsp:essTemperature:list')")
    @PostMapping("/list")
    public Result<PageInfo<VehicleEssTemperatureParsedDTO>> list(@RequestBody @Valid VehicleSearchVO vo) {
        return Result.data(vehicleEssTemperatureService.getPageList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:essTemperature:export')")
    @Log(title = "可充电储能装置温度数据 - 导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@Validated @RequestBody VehicleSearchVO vo) {
        return vehicleEssTemperatureService.export(vo);
    }
}

