package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.tsp.domain.TspModel;
import com.modern.tsp.model.dto.TspMessagePageListDTO;
import com.modern.tsp.model.dto.TspModelDTO;
import com.modern.tsp.model.dto.TspTagInfoDTO;
import com.modern.tsp.model.dto.TspTagPageListDTO;
import com.modern.tsp.model.vo.TspModelVO;
import com.modern.tsp.model.vo.TspTagAddVO;
import com.modern.tsp.model.vo.TspTagPageListVO;
import com.modern.tsp.service.TspModelService;
import com.modern.tsp.service.TspTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 15:05
 * @Version 1.0.0
 */
@RestController
@Api(tags = "TSP - 短信模板")
@RequestMapping("/tsp/model")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspModelController {

    private final TspModelService tspModelService;

//    @PreAuthorize("@ss.hasPermi('tsp:model:list')")
    @ApiOperation("列表")
    @PostMapping("/list")
    public Result<List<TspModel>> list() {
        return Result.data(tspModelService.list());
    }

//    @PreAuthorize("@ss.hasPermi('tsp:model:listModel')")
    @ApiOperation("分页列表")
    @PostMapping("/listModel")
    public Result<PageInfo<TspModelDTO>> listModel(@RequestBody @Valid TspModelVO vo) {
        return Result.data(tspModelService.listModel(vo));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:model:add')")
    @ApiOperation("添加")
    @Log(title = "标签管理 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspModel vo) {
        return Result.data(()->tspModelService.add(vo));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:model:get')")
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public Result<TspModel> get(@PathVariable("id") Long tspModelId) {
        return Result.data(tspModelService.get(tspModelId));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:model:edit')")
    @ApiOperation("修改")
    @Log(title = "标签管理 - 修改", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspModel vo) {
        return Result.data(()->tspModelService.edit(vo));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:model:delete')")
    @ApiOperation("删除")
    @Log(title = "标签管理 - 删除", businessType = BusinessType.UPDATE)
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long tspModelId) {
        return Result.data(()->tspModelService.delete(tspModelId));
    }

    @ApiOperation("标签下拉列表")
    @GetMapping("/getLabel/{tagType}")
    public Result<List<Map<String,Object>>> getLabel(@PathVariable("tagType") Integer tagType) {
        return Result.data(tspModelService.getLabel(tagType));
    }
}
