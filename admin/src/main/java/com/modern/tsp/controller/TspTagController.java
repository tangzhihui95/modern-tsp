package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.tsp.model.dto.TspTagInfoDTO;
import com.modern.tsp.model.dto.TspTagLabelDTO;
import com.modern.tsp.model.dto.TspTagPageListDTO;
import com.modern.tsp.model.vo.TspTagAddVO;
import com.modern.tsp.model.vo.TspTagPageListVO;
import com.modern.tsp.service.TspTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "TSP - 标签管理")
@RequestMapping("/tsp/tag")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspTagController {

    private final TspTagService tspTagService;

    @PreAuthorize("@ss.hasPermi('tsp:tag:list')")
    @ApiOperation("列表")
    @PostMapping("/list")
    public Result<PageInfo<TspTagPageListDTO>> list(@RequestBody @Valid TspTagPageListVO vo) {
        return Result.data(tspTagService.list(vo));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:tag:add')")
    @ApiOperation("添加")
    @Log(title = "标签管理 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspTagAddVO vo) {
        return Result.data(()->tspTagService.add(vo));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:tag:get')")
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public Result<TspTagInfoDTO> get(@PathVariable("id") Long tspTagId) {
        return Result.data(tspTagService.get(tspTagId));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:tag:edit')")
    @ApiOperation("修改")
    @Log(title = "标签管理 - 修改", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspTagAddVO vo) {
        return Result.data(()->tspTagService.edit(vo));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:tag:delete')")
    @ApiOperation("删除")
    @Log(title = "标签管理 - 删除", businessType = BusinessType.UPDATE)
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long tspTagId) {
        return Result.data(()->tspTagService.delete(tspTagId));
    }

    @ApiOperation("标签下拉列表")
    @GetMapping("/getLabel/{tagType}")
    public Result<List<TspTagLabelDTO>> getLabel(@PathVariable("tagType") Integer tagType) {
        return Result.data(tspTagService.getLabel(tagType));
    }
}
