package com.modern.xtsp.controller;

import com.alibaba.fastjson.JSONObject;
import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.controller.BaseController;
import com.modern.common.enums.BusinessType;
import com.modern.xtsp.domain.vo.ZhiYuanVehicleTboxSettingsVO;
import com.modern.xtsp.service.impl.XZhiYuanTspServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "TSP - 智源车控")
@RequestMapping("/zhiyuan/")
public class XZhiYuanTspController extends BaseController {

    @Autowired
    private XZhiYuanTspServiceImpl xZhiYuanTspService;


    @ApiOperation("发送查询命令")
    @PreAuthorize("@ss.hasPermi('zhiyuan:tsp:tbox-query')")
    @GetMapping("/tbox-settings/query/{vin}")
    public Result sendQueryTboxSettingsCommand(@PathVariable("vin") String vin) {
        return Result.data(xZhiYuanTspService.sendQueryTboxSettingsCommand(vin));
    }

    @ApiOperation("获取查询命令结果")
    @PreAuthorize("@ss.hasPermi('zhiyuan:tsp:tbox-query')")
    @GetMapping("/tbox-settings/query-result/{vin}")
    public Result<JSONObject> getQueryTboxSettingsResult(@PathVariable("vin") String vin) {
        return Result.data(xZhiYuanTspService.getQueryTboxSettingsResult(vin));
    }

    @ApiOperation("发送设置命令")
    @PreAuthorize("@ss.hasPermi('zhiyuan:tsp:tbox-update')")
    @Log(title = "设置命令", businessType = BusinessType.UPDATE)
    @PostMapping("/tbox-settings/update")
    public Result sendUpdateTboxSettingsCommand(@RequestBody @Valid ZhiYuanVehicleTboxSettingsVO zhiYuanVehicleTboxSettingsVO) {
        return Result.data(xZhiYuanTspService.sendUpdateTboxSettingsCommand(zhiYuanVehicleTboxSettingsVO));
    }

    @ApiOperation("获取设置命令结果")
    @PreAuthorize("@ss.hasPermi('zhiyuan:tsp:tbox-update')")
    @GetMapping("/tbox-settings/update-result/{vin}")
    public Result<JSONObject> getUpdateTboxSettingsResult(@PathVariable("vin") String vin) {
        return Result.data(xZhiYuanTspService.getUpdateTboxSettingsResult(vin));
    }

    @ApiOperation("下发命令")
    @PostMapping("/vehicle-control/send")
    public Result sendVehicleControlCommand(@RequestBody @Valid XZhiYuanTspServiceImpl.TspVehicleControlCommandVO tspVehicleControlCommandVO){
        return Result.data(xZhiYuanTspService.sendVehicleControlCommand(tspVehicleControlCommandVO));
    }

    @ApiOperation("根据vin获取命令执行结果")
    @GetMapping("/vehicle-control/result/{vin}")
    public Result<Object> commandExecuteResult(@PathVariable String vin){
        return Result.data(xZhiYuanTspService.getVehicleControlCommandExecuteResult(vin));
    }
}
