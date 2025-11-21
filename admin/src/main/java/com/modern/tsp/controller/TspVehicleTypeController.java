package com.modern.tsp.controller;


import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.FrontQuery;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.tsp.domain.TspVehicleType;
import com.modern.tsp.model.dto.TspVehicleTypePageListDTO;
import com.modern.tsp.model.vo.TspVehicleTypeAddVO;
import com.modern.tsp.service.TspVehicleTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 摩登 - TSP - 车辆分类 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/tsp/vehicle/type")
@Api(tags = "TSP - 车辆分类")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleTypeController {


    private final TspVehicleTypeService tspVehicleTypeService;


    @PreAuthorize("@ss.hasPermi('tsp:vehicleType:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspVehicleTypePageListDTO>> list(@RequestBody @Valid FrontQuery vo){
        return Result.data(tspVehicleTypeService.getPageList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleType:add')")
    @ApiOperation("添加")
    @Log(title = "车辆分类 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspVehicleTypeAddVO vo){
        return Result.data(() ->tspVehicleTypeService.add(vo));
    }


    @PreAuthorize("@ss.hasPermi('tsp:vehicleType:edit')")
    @ApiOperation("编辑")
    @Log(title = "车辆分类 - 编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspVehicleTypeAddVO vo){
        return Result.data(() -> tspVehicleTypeService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleType:deletes')")
    @ApiOperation("批量删除")
    @Log(title = "车辆分类 - 批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/deletes/{tspVehicleTypeIds}")
    public Result deletes(@PathVariable Long[] tspVehicleTypeIds){
        return Result.data(() -> tspVehicleTypeService.deletes(tspVehicleTypeIds));
    }


//    @PreAuthorize("@ss.hasPermi('tsp:vehicleType:selectList')")
    @ApiOperation("下拉列表")
    @GetMapping("/selectList")
    public Result<List<TspVehicleType>> selectList(@RequestParam(value = "tspVehicleTypeId",required = false) Long tspVehicleTypeId){
        return Result.data(tspVehicleTypeService.selectList(tspVehicleTypeId));
    }
}

