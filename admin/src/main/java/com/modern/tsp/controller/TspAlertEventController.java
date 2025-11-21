package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.exInterface.model.dto.TspAlertEventInfoDTO;
import com.modern.tsp.model.dto.TspAlertEventPageListDTO;
import com.modern.tsp.model.vo.TspAlertEventAddVO;
import com.modern.tsp.model.vo.TspAlertEventPageListVO;
import com.modern.tsp.service.TspAlertEventService;
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
 * @date 2022/7/14 15:39
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@RestController
@Api(tags = "TSP - 告警事件规则")
@RequestMapping("/tsp/vehicle/alertEvent")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspAlertEventController {

    private final TspAlertEventService tspAlertEventService;

    /**
     * 列表
     */
    @PreAuthorize("@ss.hasPermi('tsp:alertEvent:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspAlertEventPageListDTO>> list(@RequestBody @Valid TspAlertEventPageListVO vo) {
        return Result.data(tspAlertEventService.getPageList(vo));
    }


    /**
     * 删除规则
     */
    @PreAuthorize("@ss.hasPermi('tsp:alertEvent:delete')")
    @ApiOperation("删除")
    @Log(title = "告警事件规则 - 删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{tspAlertEventId}")
    public Result delete(@PathVariable("tspAlertEventId") Long tspAlertEventId) {
        return Result.data(() -> tspAlertEventService.delete(tspAlertEventId));
    }


    /**
     * 添加规则
     */
    @PreAuthorize("@ss.hasPermi('tsp:alertEvent:add')")
    @ApiOperation("添加")
    @Log(title = "告警事件规则 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspAlertEventAddVO vo) {
        return Result.data(() -> tspAlertEventService.add(vo));
    }

    /**
     * 添加规则
     */
    @PreAuthorize("@ss.hasPermi('tsp:alertEvent:edit')")
    @ApiOperation("编辑")
    @Log(title = "告警事件规则 - 编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspAlertEventAddVO vo) {
        return Result.data(() -> tspAlertEventService.edit(vo));
    }


    /**
     * 详情
     */
    @PreAuthorize("@ss.hasPermi('tsp:alertEvent:get')")
    @ApiOperation("详情")
    @GetMapping("/get/{tspAlertEventId}")
    public Result<TspAlertEventInfoDTO> get(@PathVariable("tspAlertEventId") Long tspAlertEventId) {
        return Result.data(tspAlertEventService.get(tspAlertEventId));
    }

    /**
     * 开启关闭
     */
//    @PreAuthorize("@ss.hasPermi('tsp:alertEvent:setState')")

    @ApiOperation("开启/关闭")
    @PatchMapping("/setState/{tspAlertEventId}")
    public Result setState(@PathVariable("tspAlertEventId") Long tspAlertEventId) {
        return Result.data(() -> tspAlertEventService.setState(tspAlertEventId));
    }
}
