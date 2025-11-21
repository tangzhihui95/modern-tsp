package com.modern.tsp.controller;


import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.model.dto.TspEquipmentExcelDTO;
import com.modern.tsp.model.dto.TspEquipmentLikeSelectDTO;
import com.modern.tsp.model.dto.TspEquipmentPageListDTO;
import com.modern.tsp.model.vo.TspEquipmentAddVO;
import com.modern.tsp.model.vo.TspEquipmentPageListVO;
import com.modern.tsp.model.vo.TspEquipmentScrapVO;
import com.modern.tsp.model.vo.TspVehicleScrapVO;
import com.modern.tsp.service.TspEquipmentService;
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
 * 摩登 - TSP - 设备 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@RestController
@Api(tags = "TSP - 设备信息")
@RequestMapping("/tsp/equipment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspEquipmentController {

    private final TspEquipmentService tspEquipmentService;


//    @PreAuthorize("@ss.hasPermi('tsp:equipment:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspEquipmentPageListDTO>> list(@RequestBody @Valid TspEquipmentPageListVO vo) {
        return Result.data(tspEquipmentService.getPageList(vo));
    }


    @PreAuthorize("@ss.hasPermi('tsp:equipment:add')")
    @ApiOperation("添加")
    @Log(title = "设备信息 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspEquipmentAddVO vo) {
        return Result.data(() -> tspEquipmentService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipment:edit')")
    @ApiOperation("编辑")
    @Log(title = "设备信息 - 编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspEquipmentAddVO vo) {
        return Result.data(() -> tspEquipmentService.edit(vo));
    }


//    @PreAuthorize("@ss.hasPermi('tsp:equipment:likeSelect')")
    @ApiOperation("下拉列表")
    @GetMapping("/likeSelect/{tspEquipmentId}")
    public Result<List<TspEquipmentLikeSelectDTO>> likeSelect(@PathVariable Long tspEquipmentId,
                                                              @RequestParam(required = false) String name) {
        return Result.data(tspEquipmentService.likeSelect(tspEquipmentId, name));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipment:deletes')")
    @ApiOperation("批量删除")
    @Log(title = "设备信息 - 批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/deletes/{tspEquipmentIds}")
    public Result deletes(@PathVariable Long[] tspEquipmentIds) {
        return Result.data(() -> tspEquipmentService.deletes(tspEquipmentIds));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipment:export')")
    @ApiOperation("导出")
    @Log(title = "设备信息 - 导出", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TspEquipmentPageListVO vo) {
        List<TspEquipmentExcelDTO> list = tspEquipmentService.exportList(vo);
        ExcelUtil<TspEquipmentExcelDTO> util = new ExcelUtil<TspEquipmentExcelDTO>(TspEquipmentExcelDTO.class);
        return util.exportExcel(list, "设备信息");
    }


    @ApiOperation("导入")
    @PreAuthorize("@ss.hasPermi('tsp:equipment:importEquipment')")
    @Log(title = "设备信息 - 导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importEquipment")
    public Result importEquipment(MultipartFile file,Boolean isUpdateSupport) {
        return Result.success(tspEquipmentService.importEquipment(file,isUpdateSupport));
    }


    @ApiOperation("下载模板")
//    @PreAuthorize("@ss.hasPermi('tsp:equipment:importTemplate')")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<TspEquipmentExcelDTO> util = new ExcelUtil<TspEquipmentExcelDTO>(TspEquipmentExcelDTO.class);
        return util.importTemplateExcel("设备信息数据");
    }

    /**
     * 报废
     */
    @PreAuthorize("@ss.hasPermi('tsp:equipment:scrap')")
    @ApiOperation("报废")
    @Log(title = "设备信息 - 报废", businessType = BusinessType.UPDATE)
    @PutMapping("/scrap")
    public Result scrap(@RequestBody @Valid TspEquipmentScrapVO vo){
        return Result.data(() -> tspEquipmentService.scrap(vo));
    }


}

