package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.controller.BaseController;
import com.modern.common.core.domain.entity.SysUser;
import com.modern.common.core.page.PageInfo;
import com.modern.common.core.page.TableDataInfo;
import com.modern.common.enums.BusinessType;
import com.modern.tsp.domain.TspInformation;
import com.modern.tsp.model.dto.TspInformationInfoDTO;
import com.modern.tsp.model.dto.TspInformationPageListDTO;
import com.modern.tsp.model.vo.TspInformationAddVO;
import com.modern.tsp.model.vo.TspInformationPageListVO;
import com.modern.tsp.model.vo.TspMessageAddVO;
import com.modern.tsp.service.TspInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2022/10/12 15:39
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@RestController
@Api(tags = "TSP - 信息管理")
@RequestMapping("/tsp/information")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspInformationController extends BaseController {

    private final TspInformationService tspInformationService;

//    @PreAuthorize("@ss.hasPermi('tsp:information:list')")
    @ApiOperation("信息分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspInformationPageListDTO>> list(@RequestBody TspInformationPageListVO vo){
        return Result.data(tspInformationService.list(vo));
    }

    @ApiOperation("用户列表")
    @PostMapping("/listInformation")
    public TableDataInfo listInformation(@RequestBody TspInformationPageListVO vo)
    {
        startPage();
        List<TspInformationPageListDTO> list = tspInformationService.listInformation(vo);
        return getDataTable(list);
    }

    /**
     * 添加信息
     */
    @PreAuthorize("@ss.hasPermi('tsp:information:add')")
    @ApiOperation("添加")
    @Log(title = "信息发布 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspInformationAddVO vo){
        return Result.data(()->tspInformationService.add(vo));
    }

    /**
     * 获取消息
     */
    @PreAuthorize("@ss.hasPermi('tsp:information:get')")
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public Result<TspInformationInfoDTO> get(@PathVariable("id")Long tspInformationId){
        return Result.data(tspInformationService.get(tspInformationId));
    }

    /**
     * 修改
     */
    @PreAuthorize("@ss.hasPermi('tsp:information:edit')")
    @ApiOperation("修改")
    @Log(title = "信息发布 - 修改", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspInformationAddVO vo){
        return Result.data(()->tspInformationService.edit(vo));
    }

    /**
     * 下线
     */
    @PreAuthorize("@ss.hasPermi('tsp:information:unload')")
    @ApiOperation("信息发布下线")
    @Log(title = "信息发布 - 下线", businessType = BusinessType.UPDATE)
    @PatchMapping("/unload/{id}")
    public Result unload(@PathVariable("id")Long tspInformationId){
        return Result.data(()->tspInformationService.unload(tspInformationId));
    }
}
