package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.tsp.model.dto.TspFenceInfoDTO;
import com.modern.tsp.model.dto.TspFencePageListDTO;
import com.modern.tsp.model.vo.TspFenceAddVO;
import com.modern.tsp.model.vo.TspFencePageListVO;
import com.modern.tsp.service.TspFenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 15:05
 * @Version 1.0.0
 */
@RestController
@Api(tags = "TSP - 电子围栏管理")
@RequestMapping("/tsp/fence")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspFenceController {

    private final TspFenceService tspFenceService;

//    @PreAuthorize("@ss.hasPermi('tsp:fence:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    // @Valid(数据验证)
    public Result<PageInfo<TspFencePageListDTO>> list(@RequestBody @Valid TspFencePageListVO vo) {
        return Result.data(tspFenceService.list(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:fence:add')")
    @ApiOperation("添加")
    @Log(title = "电子围栏管理 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspFenceAddVO vo) {
        return Result.data(()->tspFenceService.add(vo));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:fence:get')")
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public Result<TspFenceInfoDTO> get(@PathVariable("id") Long tspFenceId) {
        return Result.data(tspFenceService.get(tspFenceId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:fence:edit')")
    @ApiOperation("修改")
    @Log(title = "电子围栏管理 - 修改", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspFenceAddVO vo) {
        return Result.data(()->tspFenceService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:fence:delete')")
    @ApiOperation("删除")
    @Log(title = "电子围栏管理 - 删除", businessType = BusinessType.UPDATE)
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long tspFenceId) {
        return Result.data(()->tspFenceService.delete(tspFenceId));
    }
}
