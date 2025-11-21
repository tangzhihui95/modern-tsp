package com.modern.tsp.controller;


import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.domain.FrontQuery;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.domain.TspEquipment;
import com.modern.tsp.domain.TspEquipmentType;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.TspEquipmentPageListVO;
import com.modern.tsp.model.vo.TspEquipmentTypeAddVO;
import com.modern.tsp.service.TspEquipmentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 摩登 - TSP - 设备分类 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/tsp/equipmentType")
@Api(tags = "TSP - 设备分类")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspEquipmentTypeController {

    private final TspEquipmentTypeService equipmentTypeService;


    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspEquipmentTypePageListDTO>> list(@RequestBody FrontQuery vo) {
        return Result.data(equipmentTypeService.getPageList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:add')")
    @ApiOperation("添加")
    @Log(title = "设备分类 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspEquipmentTypeAddVO vo) {
        return Result.data(() -> equipmentTypeService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:edit')")
    @ApiOperation("编辑")
    @Log(title = "设备分类 - 编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspEquipmentTypeAddVO vo) {
        return Result.data(() -> equipmentTypeService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:delete')")
    @ApiOperation("删除")
    @Log(title = "设备分类 - 删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{equipmentTypeId}")
    public Result delete(@PathVariable("equipmentTypeId") Long equipmentTypeId) {
        return Result.data(() -> equipmentTypeService.delete(equipmentTypeId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:deletes')")
    @ApiOperation("批量删除")
    @Log(title = "设备分类 - 批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/deletes/{equipmentTypeIds}")
    public Result deletes(@PathVariable Long[] equipmentTypeIds) {
        return Result.data(() -> equipmentTypeService.deletes(equipmentTypeIds));
    }

    //    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:selectList')")
    @ApiOperation("下拉列表")
    @Log(title = "设备分类 - 下拉列表", businessType = BusinessType.DELETE)
    @PostMapping("/selectList")
    public Result<List<TspEquipmentTypeSelectDTO>> selectList(@RequestBody FrontQuery vo) {
        return Result.data(equipmentTypeService.selectList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:export')")
    @ApiOperation("导出")
    @Log(title = "设备信息 - 导出", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FrontQuery vo) {
        List<TspEquipmentTypeExcelDTO> list = equipmentTypeService.exportList(vo);
        ExcelUtil<TspEquipmentTypeExcelDTO> util = new ExcelUtil<TspEquipmentTypeExcelDTO>(TspEquipmentTypeExcelDTO.class);
        return util.exportExcel(list, "设备分类信息");
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:importEquipmentModel')")
    @ApiOperation("设备型号导入")
    @Log(title = "设备型号信息 - 导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importEquipmentModel")
    public Result importEquipmentModel(MultipartFile file, Boolean isUpdateSupport) {
        return Result.success(equipmentTypeService.importEquipmentModel(file, isUpdateSupport));
    }

    @ApiOperation("设备分类导入")
    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:importEquipmentType')")
    @Log(title = "设备分类信息 - 导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importEquipmentType")
    public Result importEquipmentType(MultipartFile file, Boolean isUpdateSupport) {
        return Result.success(equipmentTypeService.importEquipmentType(file, isUpdateSupport));
    }

    @ApiOperation("设备分类下载模板")
//    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:importTemplate')")
    @GetMapping("/importTypeTemplate")
    public AjaxResult importTypeTemplate() {
        ExcelUtil<TspEquipmentTypeImportDTO> util = new ExcelUtil<>(TspEquipmentTypeImportDTO.class);
        return util.importTemplateExcel("设备类型数据");
    }

    @ApiOperation("设备型号下载模板")
//    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:importTemplate')")
    @GetMapping("/importModelTemplate")
    public AjaxResult importModelTemplate() {
        ExcelUtil<TspEquipmentTypeExcelDTO> util = new ExcelUtil<>(TspEquipmentTypeExcelDTO.class);
        return util.importTemplateExcel("设备型号数据");
    }
}

