package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.exInterface.model.dto.VehicleAlertPageListDTO;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.exInterface.service.VehicleAlertService;
import com.modern.tsp.model.dto.TspVehicleAlertInfoDTO;
import com.modern.tsp.model.dto.TspVehicleAlertPageListDTO;
import com.modern.tsp.model.vo.TspVehicleAlertPageListVO;
import com.modern.tsp.model.vo.TspVehicleHandleStateVO;
import com.modern.tsp.service.TspVehicleAlertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 11:36
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@RestController
@Api(tags = "TSP - 历史报警")
@RequestMapping("/tsp/vehicle/historyAlert")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleAlertController {

    private final TspVehicleAlertService tspVehicleAlertService;
    private final VehicleAlertService vehicleAlertService;

    /**
     * 列表
     */
    @PreAuthorize("@ss.hasPermi('tsp:historyAlert:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspVehicleAlertPageListDTO>> list(@RequestBody @Valid TspVehicleAlertPageListVO vo){
        return Result.data(tspVehicleAlertService.getPageList(vo));
    }

    @ApiOperation("导出")
    @PreAuthorize("@ss.hasPermi('tsp:historyAlert:export')")
    @Log(title = "历史报警 - 导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody VehicleSearchVO vo) {
        vo.setPageNum(1);
        vo.setPageSize(Integer.MAX_VALUE);
        List<VehicleAlertPageListDTO> list = vehicleAlertService.getHistoryPageList(vo).getList();
        ExcelUtil<VehicleAlertPageListDTO> util = new ExcelUtil<>(VehicleAlertPageListDTO.class);
        return util.exportExcel(list, "历史报警信息");
    }

    @ApiOperation("详情")
    @PreAuthorize("@ss.hasPermi('tsp:historyAlert:get')")
    @GetMapping("/get/{tspVehicleAlertId}")
    public Result<TspVehicleAlertInfoDTO> get(@PathVariable("tspVehicleAlertId")Long tspVehicleAlertId){
        return Result.data(tspVehicleAlertService.get(tspVehicleAlertId));
    }


    @ApiOperation("历史告警处理")
    @PreAuthorize("@ss.hasPermi('tsp:historyAlert:handleState')")
    @Log(title = "历史报警 - 处理", businessType = BusinessType.UPDATE)
    @PutMapping("/handleState")
    public Result handleState(@RequestBody @Valid TspVehicleHandleStateVO vo){
        return Result.data(()->tspVehicleAlertService.handleState(vo));
    }
}
