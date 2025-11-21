package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.tsp.model.dto.TspFeedbackInfoDTO;
import com.modern.tsp.model.dto.TspFeedbackPageListDTO;
import com.modern.tsp.model.vo.TspFeedbackAddVO;
import com.modern.tsp.model.vo.TspFeedbackPageListVO;
import com.modern.tsp.service.TspFeedbackService;
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
 * @Date 2022/10/17 09:05
 * @Version 1.0.0
 */
@RestController
@Api(tags = "TSP - 问题反馈管理")
@RequestMapping("/tsp/feedback")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspFeedbackController {

    private final TspFeedbackService tspFeedbackService;

//    @PreAuthorize("@ss.hasPermi('tsp:feedback:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspFeedbackPageListDTO>> list(@RequestBody @Valid TspFeedbackPageListVO vo) {
        return Result.data(tspFeedbackService.list(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:feedback:add')")
    @ApiOperation("添加")
    @Log(title = "问题反馈管理 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspFeedbackAddVO vo) {
        return Result.data(()->tspFeedbackService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:feedback:get')")
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public Result<TspFeedbackInfoDTO> get(@PathVariable("id") Long tspFeedbackId) {
        return Result.data(tspFeedbackService.get(tspFeedbackId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:feedback:deal')")
    @ApiOperation("处理")
    @Log(title = "问题反馈管理 - 处理", businessType = BusinessType.UPDATE)
    @PutMapping("/deal")
    public Result deal(@RequestBody @Valid TspFeedbackAddVO vo) {
        return Result.data(()->tspFeedbackService.deal(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:feedback:delete')")
    @ApiOperation("删除")
    @Log(title = "问题反馈管理 - 删除", businessType = BusinessType.UPDATE)
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long tspFeedbackId) {
        return Result.data(()->tspFeedbackService.delete(tspFeedbackId));
    }
}
