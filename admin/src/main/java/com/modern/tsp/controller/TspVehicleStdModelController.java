package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.TspVehiclePageListVO;
import com.modern.tsp.model.vo.TspVehicleStdModelAddVO;
import com.modern.tsp.service.TspVehicleStdModeService;
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
 * @date 2022/6/30 16:08
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@RestController
@Api(tags = "TSP - 车型型号")
@RequestMapping("/tsp/vehicle/stdModel")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleStdModelController {

    private final TspVehicleStdModeService tspVehicleStdModeService;

    /**
     * 添加
     */
    @PreAuthorize("@ss.hasPermi('tsp:stdModel:addStdModel')")
    @ApiOperation("添加")
    @Log(title = "车型型号 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/addStdModel")
    public Result add(@RequestBody @Valid TspVehicleStdModelAddVO vo) {
        return Result.data(() -> tspVehicleStdModeService.add(vo));
    }

    /**
     * 编辑
     *
     */
    @PreAuthorize("@ss.hasPermi('tsp:stdModel:edit')")
    @ApiOperation("编辑")
    @Log(title = "车型型号 - 编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspVehicleStdModelAddVO vo) {
        return Result.data(() -> tspVehicleStdModeService.edit(vo));
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('tsp:stdModel:remove')")
    @ApiOperation("删除")
    @Log(title = "车型型号 - 删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{tspVehicleStdModelId}")
    public Result delete(@PathVariable("tspVehicleStdModelId")Long tspVehicleStdModelId){
        return Result.data(()->tspVehicleStdModeService.delete(tspVehicleStdModelId));
    }


    /**
     * 详情
     */
    @PreAuthorize("@ss.hasPermi('tsp:stdModel:get')")
    @ApiOperation("详情")
    @GetMapping("/get/{tspVehicleStdModelId}")
    public Result get(@PathVariable("tspVehicleStdModelId")Long tspVehicleStdModelId){
        return Result.data(tspVehicleStdModeService.get(tspVehicleStdModelId));
    }


    /**
     * 车辆型号下拉列表
     */
//    @PreAuthorize("@ss.hasPermi('tsp:stdModel:select')")
    @ApiOperation("车辆型号下拉列表")
    @GetMapping("/select")
    public Result<List<TspVehicleStdModelSelectListDTO>> select(@RequestParam(value = "tspVehicleStdModelId",required = false)Long tspVehicleStdModelId){
        return Result.data(tspVehicleStdModeService.select());
    }

    @ApiOperation("统计车型标签")
//    @PreAuthorize("@ss.hasPermi('tsp:stdModel:getLabelMap')")
    @GetMapping("/getLabelMap")
    public Result<TspVehicleStdModelLabelMapDTO> getLabelMap(){
        return Result.data(tspVehicleStdModeService.getLabelMap());
    }
}
