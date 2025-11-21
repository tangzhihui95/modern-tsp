package com.modern.tsp.controller;


import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.domain.TspVehicleModel;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.TspVehicleModelAddVO;
import com.modern.tsp.model.vo.TspVehicleModelPageListVO;
import com.modern.tsp.model.vo.TspVehiclePageListVO;
import com.modern.tsp.service.TspVehicleModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 摩登 - TSP - 车辆车型 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@RestController
@Api(tags = "TSP - 车辆车型")
@RequestMapping("/tsp/vehicle/model")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleModelController {
    private final TspVehicleModelService tspVehicleModelService;
    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspVehicleModelPageListDTO>> list(@RequestBody @Valid TspVehicleModelPageListVO vo) {
        return Result.data(tspVehicleModelService.getPageList(vo));
    }


    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:add')")
    @ApiOperation("添加")
    @Log(title = "车辆车型 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspVehicleModelAddVO vo) {
        return Result.data(() -> tspVehicleModelService.add(vo));
    }


    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:edit')")
    @ApiOperation("编辑")
    @Log(title = "车辆车型 - 编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspVehicleModelAddVO vo){
        return Result.data(()->tspVehicleModelService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:delete')")
    @ApiOperation("删除")
    @Log(title = "车辆车型 - 删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{tspVehicleModelId}")
    public Result delete(@PathVariable Long tspVehicleModelId){
        return Result.data(()->tspVehicleModelService.delete(tspVehicleModelId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:deletes')")
    @ApiOperation("批量删除")
    @Log(title = "车辆车型 - 批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/deletes/{tspVehicleModelIds}")
    public Result deletes(@PathVariable("tspVehicleModelIds") Long[] tspVehicleModelIds){
        return Result.data(()-> tspVehicleModelService.deletes(tspVehicleModelIds));
    }


    @ApiOperation("一级车型导入")
    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:importVehicleModel')")
    @Log(title = "一级车型 - 导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importVehicleModel")
    public Result importVehicleModel(@RequestParam("file") MultipartFile multipartFile, Boolean isUpdateSupport) {
        return Result.success(tspVehicleModelService.importVehicleModel(multipartFile,isUpdateSupport));
    }

    @ApiOperation("二级车型导入")
    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:importVehicleStdModel')")
    @Log(title = "二级车型 - 导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importVehicleStdModel")
    public Result importVehicleStdModel(@RequestParam("file") MultipartFile multipartFile, Boolean isUpdateSupport) {
        return Result.success(tspVehicleModelService.importVehicleModelStd(multipartFile,isUpdateSupport));
    }

    @ApiOperation("一级车型列表")
//    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:selectList')")
    @GetMapping("/selectList")
    public Result selectList(@RequestParam(value = "tspVehicleModelId",required = false)Long tspVehicleModelId){
        return Result.data(tspVehicleModelService.selectList(tspVehicleModelId));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:selectChildrenList')")
    @ApiOperation("二级车型列表")
    @Log(title = "车型 - 下拉列表")
    @PostMapping("/selectChildrenList")
    public Result<List<TspVehicleModelSelectDTO>> selectChildrenList(@RequestBody @Valid TspVehiclePageListVO vo) {
        return Result.data(tspVehicleModelService.selectChildrenList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:export')")
    @Log(title = "车型 - 导出", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TspVehicleModelPageListVO vo) {
        List<TspVehicleStdModelExListDTO> list = tspVehicleModelService.exportList(vo);
        ExcelUtil<TspVehicleStdModelExListDTO> util = new ExcelUtil<>(TspVehicleStdModelExListDTO.class);
        return util.exportExcel(list, "车型信息");
    }

    @ApiOperation("下载模板")
    @GetMapping("/importTemplateModel")
    public AjaxResult importTemplateModel() {
        ExcelUtil<TspVehicleModel> util = new ExcelUtil<>(TspVehicleModel.class);
        return util.importTemplateExcel("一级车型信息数据");
    }

    @ApiOperation("下载模板")
    @GetMapping("/importTemplateStdModel")
    public AjaxResult importTemplateStdModel() {
        ExcelUtil<TspVehicleStdModelExportListDTO> util = new ExcelUtil<>(TspVehicleStdModelExportListDTO.class);
        return util.importTemplateExcel("二级车型信息数据");
    }
}

