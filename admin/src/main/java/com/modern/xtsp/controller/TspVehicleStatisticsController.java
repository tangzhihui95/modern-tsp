package com.modern.xtsp.controller;

import com.modern.common.core.Result;
import com.modern.common.core.controller.BaseController;
import com.modern.xtsp.domain.TspAllVehicleDailyStatistics;
import com.modern.xtsp.domain.TspVehicleDailyStatistics;
import com.modern.xtsp.service.TspAllVehicleDailyStatisticsService;
import com.modern.xtsp.service.TspVehicleDailyStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Api(tags = "TSP - 统计分析")
@RequestMapping("/tsp/statistics")
public class TspVehicleStatisticsController extends BaseController {

    @Autowired
    private TspAllVehicleDailyStatisticsService tspAllVehicleDailyStatisticsService;

    @Autowired
    private TspVehicleDailyStatisticsService tspVehicleDailyStatisticsService;

    @ApiOperation("查询所有车辆每日统计")
    @PreAuthorize("@ss.hasPermi('tsp:statistics:all')")
    @GetMapping("/daily/all-vehicle/list")
    public Result<List<TspAllVehicleDailyStatistics>> listAllVehicleDailyStatistics(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return Result.data(tspAllVehicleDailyStatisticsService.getAllByStatisticsDateBetween(startDate, endDate));
    }

    @ApiOperation("查询单个车辆每日统计")
    @PreAuthorize("@ss.hasPermi('tsp:statistics:list')")
    @GetMapping("/daily/vehicle/list")
    public Result<List<TspVehicleDailyStatistics>> listVehicleDailyStatistics(@RequestParam(required = true) String search, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return Result.data(tspVehicleDailyStatisticsService.listByVinAndStatisticsDateBetween(search, startDate, endDate));
    }

//    @ApiOperation("查询角色关联的所有车辆")
//    @PreAuthorize("@ss.hasPermi('tsp:statistics:query')")
//    @GetMapping("/list-by-role/{roleId}")
//    public Result<List<TspVehicleSysRoleVO>> listByRole(@PathVariable("roleId") Long roleId) {
//        return Result.data(tspVehicleSysRoleService.getTspVehicleSysRoleVOByRole(roleId));
//    }
//
//    @ApiOperation("新增角色车辆关联")
//    @PreAuthorize("@ss.hasPermi('tsp:statistics:add')")
//    @Log(title = "角色车辆关联 - 添加", businessType = BusinessType.INSERT)
//    @PostMapping("/add-by-role")
//    public Result<Boolean> saveOrUpdateByRole(@RequestBody List<TspVehicleSysRoleDTO> tspVehicleSysRoleDTOs) {
//        return Result.data(tspVehicleSysRoleService.saveOrUpdateByRole(tspVehicleSysRoleDTOs));
//    }

//    @ApiOperation("获取角色车辆关联详细信息")
//    @PreAuthorize("@ss.hasPermi('tsp:vehicle-role:query')")
//    @GetMapping(value = "/{id}")
//    public Result getInfo(@PathVariable("id") Long id) {
//        return Result.data(() -> tspVehicleSysRoleService.getById(id));
//    }

//    @ApiOperation("新增角色车辆关联")
//    @PreAuthorize("@ss.hasPermi('tsp:vehicle-role:add')")
//    @Log(title = "角色车辆关联 - 添加", businessType = BusinessType.INSERT)
//    @PostMapping
//    public Result add(@RequestBody TspVehicleSysRole tspVehicleSysRole)
//    {
//        //TODO
//        return Result.data(() -> tspVehicleSysRoleService.save(tspVehicleSysRole));
//    }

//    @ApiOperation("修改角色车辆关联")
//    @PreAuthorize("@ss.hasPermi('tsp:vehicle-role:edit')")
//    @Log(title = "角色车辆关联 - 编辑", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public Result edit(@RequestBody TspVehicleSysRole tspVehicleSysRole)
//    {
//        //TODO
//        return Result.data(() -> tspVehicleSysRoleService.updateById(tspVehicleSysRole));
//    }

//    @ApiOperation("删除角色车辆关联")
//    @PreAuthorize("@ss.hasPermi('tsp:vehicle-role:remove')")
//    @Log(title = "角色车辆关联 - 删除", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public Result remove(@PathVariable Long[] ids)
//    {
//        //TODO
//        return Result.data(() -> tspVehicleSysRoleService.removeByIds(Arrays.asList(ids)));
//    }

//    @ApiOperation("导出角色车辆关联列表")
//    @PreAuthorize("@ss.hasPermi('tsp:vehicle-role:export')")
//    @Log(title = "角色车辆关联 - 导出", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(TspVehicleSysRole tspVehicleSysRole)
//    {
//        //TODO
//        List<TspVehicleSysRole> list = tspVehicleSysRoleService.selectTspVehicleSysRoleList(tspVehicleSysRole);
//        ExcelUtil<TspVehicleSysRole> util = new ExcelUtil<TspVehicleSysRole>(TspVehicleSysRole.class);
//        return util.exportExcel(list, "角色车辆关联数据");
//    }
}
