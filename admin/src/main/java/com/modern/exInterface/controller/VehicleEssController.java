package com.modern.exInterface.controller;


import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.exInterface.model.dto.VehicleEssParsedDTO;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.exInterface.service.VehicleEssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * vehicle_ess;可充电储能装置电压数 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@RestController
@Api(tags = "TSP - 可充电储能装置电压数据")
@RequestMapping("/tsp/vehicle/ess")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleEssController {

    private final VehicleEssService vehicleEssService;

    /**
     * 列表
     */
    @ApiOperation("分页列表")
//    @PreAuthorize("@ss.hasPermi('tsp:ess:list')")
    @PostMapping("/list")
    public Result<PageInfo<VehicleEssParsedDTO>> list(@RequestBody @Valid VehicleSearchVO vo) {
        return Result.data(vehicleEssService.getPageList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:ess:export')")
    @Log(title = "可充电储能装置电压数据 - 导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@Validated @RequestBody VehicleSearchVO vo) {
        return vehicleEssService.export(vo);
    }
}

