package com.modern.tsp.controller;


import com.modern.common.core.Result;
import com.modern.common.core.domain.FrontQuery;
import com.modern.common.core.page.PageInfo;
import com.modern.tsp.domain.TspUseVehicleRecord;
import com.modern.tsp.model.dto.TspUseVehicleRecordPageListDTO;
import com.modern.tsp.model.vo.TspUseVehicleRecordPageListVO;
import com.modern.tsp.service.TspUseVehicleRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 摩登 - TSP - 车辆绑定记录 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@RestController
@Api(tags = "TSP - 绑定记录")
@RequestMapping("/tsp/useVehicleRecord")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspUseVehicleRecordController {

    private final TspUseVehicleRecordService tspUseVehicleRecordService;

    /**
     * 列表
     */
    @PreAuthorize("@ss.hasPermi('tsp:useVehicleRecord:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspUseVehicleRecordPageListDTO>>
    list(@RequestBody @Valid TspUseVehicleRecordPageListVO frontQuery){
        return Result.data(tspUseVehicleRecordService.getPageList(frontQuery));
    }

}

