package com.modern.exInterface.controller;


import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.exInterface.model.dto.VehicleAlertDTO;
import com.modern.exInterface.model.dto.VehicleAlertPageListDTO;
import com.modern.exInterface.model.dto.VehicleAlertParsedDTO;
import com.modern.exInterface.model.vo.VehicleAlertAddVO;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.exInterface.service.VehicleAlertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * vehicle_alert;报警数据 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@RestController
@Api(tags = "TSP - 报警数据")
@RequestMapping("/tsp/vehicle/alert")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleAlertController {

    private final VehicleAlertService vehicleAlertService;

    /**
     * 列表
     */
    @ApiOperation("分页列表")
//    @PreAuthorize("@ss.hasPermi('tsp:alert:list')")
    @PostMapping("/list")
    public Result<PageInfo<VehicleAlertParsedDTO>> list(@RequestBody @Valid VehicleSearchVO vo) {
        return Result.data(vehicleAlertService.getPageList(vo));
    }

    /**
     * 历史列表
     */
    @ApiOperation("历史列表")
//    @PreAuthorize("@ss.hasPermi('tsp:alert:historyList')")
    @PostMapping("/historyList")
    public Result<PageInfo<VehicleAlertPageListDTO>> historyList(@RequestBody @Valid VehicleSearchVO vo) {
        return Result.data(vehicleAlertService.getHistoryPageList(vo));
    }

    @ApiOperation("详情")
    @PreAuthorize("@ss.hasPermi('tsp:alert:get')")
    @GetMapping("/get/{vehicleAlertId}")
    public Result<VehicleAlertDTO> get(@PathVariable("vehicleAlertId") Long vehicleAlertId) {
        return Result.data(vehicleAlertService.get(vehicleAlertId));
    }

    @ApiOperation("处理报警")
    @PreAuthorize("@ss.hasPermi('tsp:alert:deal')")
    @PostMapping("/deal")
    public Result deal(@RequestBody @Valid VehicleAlertAddVO vo) {
        return Result.data(() -> vehicleAlertService.deal(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:alert:export')")
    @Log(title = "报警数据 - 导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@Validated @RequestBody VehicleSearchVO vo) {
        return vehicleAlertService.export(vo);
    }

}

