package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.tsp.domain.TspMessage;
import com.modern.tsp.model.dto.TspMessageInfoDTO;
import com.modern.tsp.model.dto.TspMessagePageListDTO;
import com.modern.tsp.model.vo.TspMessageAddVO;
import com.modern.tsp.model.vo.TspMessagePageListVO;
import com.modern.tsp.service.TspMessageService;
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
 * @date 2022/9/1 20:40
 */
@RestController
@RequestMapping("/tsp/message")
@Api(tags = "TSP - 通知推送")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspMessageController {

    private final TspMessageService tspMessageService;

//    @PreAuthorize("@ss.hasPermi('tsp:message:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspMessagePageListDTO>> list(@RequestBody TspMessagePageListVO vo){
        return Result.data(tspMessageService.list(vo));
    }

    /**
     * 添加消息
     */
    @PreAuthorize("@ss.hasPermi('tsp:message:add')")
    @ApiOperation("添加")
    @Log(title = "通知推送 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspMessageAddVO vo){
        return Result.data(()->tspMessageService.add(vo));
    }

    /**
     * 修改
     */
    @PreAuthorize("@ss.hasPermi('tsp:message:edit')")
    @ApiOperation("修改")
    @Log(title = "通知推送 - 修改", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspMessageAddVO vo){
        return Result.data(()->tspMessageService.edit(vo));
    }

    /**
     * 禁用
     */
//    @PreAuthorize("@ss.hasPermi('tsp:message:state')")
    @ApiOperation("是否启动")
    @Log(title = "通知推送 - 修改状态", businessType = BusinessType.UPDATE)
    @PatchMapping("/state/{id}")
    public Result state(@PathVariable("id")Long tspMessageId){
        return Result.data(()->tspMessageService.state(tspMessageId));
    }


    /**
     * 获取消息
     */
    @PreAuthorize("@ss.hasPermi('tsp:message:state')")
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public Result<TspMessageInfoDTO> get(@PathVariable("id")Long tspMessageId){
        return Result.data(tspMessageService.get(tspMessageId));
    }
}
