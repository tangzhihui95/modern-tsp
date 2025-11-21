package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.enums.BusinessType;
import com.modern.tsp.model.vo.TspEquipmentModelAddVO;
import com.modern.tsp.service.TspEquipmentModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/15 11:49
 */

@RestController
@RequestMapping("/tsp/equipmentModel")
@Api(tags = "TSP - 设备型号")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspEquipmentModelController {

    private final TspEquipmentModelService tspEquipmentModelService;


    /**
     * 添加型号
     */
    @PreAuthorize("@ss.hasPermi('tsp:equipmentModel:add')")
    @ApiOperation("添加")
    @Log(title = "设备型号 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspEquipmentModelAddVO vo){
        return Result.data(()->tspEquipmentModelService.add(vo));
    }

    /**
     * 修改型号
     */
    @PreAuthorize("@ss.hasPermi('tsp:equipmentModel:edit')")
    @ApiOperation("编辑")
    @Log(title = "设备型号 - 编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspEquipmentModelAddVO vo){
        return Result.data(()->tspEquipmentModelService.edit(vo));
    }

    /**
     * 删除型号
     */
    @PreAuthorize("@ss.hasPermi('tsp:equipmentModel:delete')")
    @ApiOperation("删除")
    @Log(title = "设备型号 - 删除", businessType = BusinessType.UPDATE)
    @DeleteMapping("/delete/{tspEquipmentModelId}")
    public Result delete(@PathVariable Long tspEquipmentModelId){
        return Result.data(()->tspEquipmentModelService.delete(tspEquipmentModelId));
    }
}
