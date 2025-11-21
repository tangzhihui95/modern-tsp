package com.modern.tsp.controller;

import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.model.dto.TspVehicleAlertDataDTO;
import com.modern.tsp.model.dto.TspVehicleFlowDataDTO;
import com.modern.tsp.model.dto.TspVehicleVolumeDataDTO;
import com.modern.tsp.model.dto.VolumeDTO;
import com.modern.tsp.model.vo.TspVehicleAlertDataVO;
import com.modern.tsp.model.vo.TspVehicleFlowDataVO;
import com.modern.tsp.model.vo.TspVehicleVolumeDataVO;
import com.modern.tsp.service.TspVehicleDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/16 16:54
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@RestController
@RequestMapping("/tsp/vehicleData")
@Api(tags = "TSP - 统计分析")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleDataController {

    private final TspVehicleDataService tspVehicleDataService;

    @ApiOperation("车辆流量统计")
    @PostMapping("/flowData")
    public Result<PageInfo<TspVehicleFlowDataDTO>> flowData(@RequestBody @Valid TspVehicleFlowDataVO vo) {
        return Result.data(tspVehicleDataService.getFlowData(vo));
    }

    @ApiOperation("车辆流量统计")
    @PostMapping("/flowDataExport")
    public AjaxResult flowDataExport(@RequestBody @Valid TspVehicleFlowDataVO vo) {
        List<TspVehicleFlowDataDTO> list = tspVehicleDataService.getFlowData(vo).getList();
        ExcelUtil<TspVehicleFlowDataDTO> util = new ExcelUtil<>(TspVehicleFlowDataDTO.class);
        return util.exportExcel(list, "车辆流量统计" + LocalDateTime.now().format(LocalDateUtils.FORMAT_YYYYMMDDHHMMSS));
    }


    //    @PreAuthorize("@ss.hasPermi('tsp:data:distributionData')")
//    @ApiOperation("车辆分布统计")
//    @PostMapping("/distributionData")
//    public Result<List<TspVehicleOnlineDataDTO>> distributionData(@RequestBody @Valid TspVehicleDataDistributionVO vo) {
//        return Result.data(tspVehicleDataService.distributionData(vo));
//    }

    //    @PreAuthorize("@ss.hasPermi('tsp:data:volumeData')")
    @ApiOperation("车辆接入量统计")
    @PostMapping("/volumeData")
    public Result<List<TspVehicleVolumeDataDTO>> volumeData(@RequestBody TspVehicleVolumeDataVO vo) {
        return Result.data(tspVehicleDataService.volumeData(vo));
    }

    //    @PreAuthorize("@ss.hasPermi('tsp:data:volumeData')")
    @ApiOperation("车辆接入量统计-新版")
    @PostMapping("/volumeChartData")
    public Result<Map<String, List<VolumeDTO>>> volumeChartData(@RequestBody TspVehicleAlertDataVO vo) {
        return Result.data(tspVehicleDataService.volumeChartData(vo));
    }

    //    @PreAuthorize("@ss.hasPermi('tsp:data:rangeData')")
//    @ApiOperation("车辆行驶里程统计")
//    @PostMapping("/rangeData")
//    public Result<Map<String, Object>> rangeData(@RequestBody TspVehicleAlertDataVO vo) {
//        return Result.data(tspVehicleDataService.rangeChartData(vo));
//    }

    //    @PreAuthorize("@ss.hasPermi('tsp:data:rangeData')")
//    @ApiOperation("车辆行驶里程统计")
//    @PostMapping("/rangeDataExport")
//    public AjaxResult rangeDataExport(@RequestBody TspVehicleAlertDataVO vo) {
//        ExcelUtil<TspVehicleRangeDataDTO> util = new ExcelUtil<>(TspVehicleRangeDataDTO.class);
//        return util.exportExcel(tspVehicleDataService.rangeChartDataExport(vo), "_里程数据_" + LocalDateTime.now().format(LocalDateUtils.FORMAT_YYYYMMDDHHMMSS));
//    }

    //    @PreAuthorize("@ss.hasPermi('tsp:data:alertData')")
    @ApiOperation("车辆告警统计")
    @PostMapping("/alertData")
    public Result<List<TspVehicleAlertDataDTO>> alertData(@RequestBody TspVehicleAlertDataVO vo) throws ServerException {
        return Result.data(tspVehicleDataService.alertChartData(vo));
    }

//    //    @PreAuthorize("@ss.hasPermi('tsp:data:activityData')")
//    @ApiOperation("车辆活跃度统计")
//    @PostMapping("/activityData")
//    public Result<List<TspVehicleActivityDataDTO>> activityData(@RequestBody TspVehicleAlertDataVO vo) {
//        return Result.data(tspVehicleDataService.activityChartData(vo));
//    }
//
//    @ApiOperation("车辆在线时长统计")
//    @PostMapping("/onlineChartData")
//    public Result<List<DayNewOnline>> onlineChartData(@RequestBody DayNewOnline vo) {
//        return Result.data(dayNewOnlineService.onlineChartData(vo));
//    }
}
