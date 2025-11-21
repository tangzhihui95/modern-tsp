package com.modern.exInterface.controller;


import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.exInterface.model.dto.VehicleDriveMotorParsedDTO;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.exInterface.service.VehicleDriveMotorService;
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
 * vehicle_drive_motor;驱动电机数据 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@RestController
@Api(tags = "TSP - 驱动电机数据")
@RequestMapping("/tsp/vehicle/driveMotor")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleDriveMotorController {

    private final VehicleDriveMotorService vehicleDriveMotorService;


    /**
     * 列表
     */
    @ApiOperation("分页列表")
//    @PreAuthorize("@ss.hasPermi('tsp:driveMotor:list')")
    @PostMapping("/list")
    public Result<PageInfo<VehicleDriveMotorParsedDTO>> list(@RequestBody @Valid VehicleSearchVO vo) {
        return Result.data(vehicleDriveMotorService.getPageList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:driveMotor:export')")
    @Log(title = "驱动电机数据 - 导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@Validated @RequestBody VehicleSearchVO vo) {
        return vehicleDriveMotorService.export(vo);
    }
}

