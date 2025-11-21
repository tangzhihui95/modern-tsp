package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.exInterface.entity.command.VehicleCommand;
import com.modern.tsp.domain.TspDealer;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.TspVehicleAddVO;
import com.modern.tsp.model.vo.TspVehiclePageListVO;
import com.modern.tsp.model.vo.TspVehicleScrapVO;
import com.modern.tsp.service.TspVehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 摩登 - TSP - 车辆信息 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/tsp/vehicle")
@Api(tags = "TSP - 车辆信息")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleController {


    private final TspVehicleService tspVehicleService;

    /**
     * 列表
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<PageInfo<TspVehiclePageListDTO>> list(@RequestBody @Valid TspVehiclePageListVO vo) {
        return Result.data(tspVehicleService.getPageList(vo));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:add')")
    @ApiOperation("新增")
    @Log(title = "车辆信息 - 新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result<TspVehicleInfoDTO> add(@RequestBody @Valid TspVehicleAddVO vo) {
        return Result.data(tspVehicleService.add(vo));
    }


    /**
     * 编辑
     */
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:edit')")
    @ApiOperation("编辑")
    @Log(title = "车辆信息 - 编辑", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspVehicleAddVO vo) {
        return Result.data(() -> tspVehicleService.edit(vo));
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:delete')")
    @ApiOperation("删除")
    @Log(title = "车辆信息 - 删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{tspVehicleId}")
    public Result delete(@PathVariable Long tspVehicleId) {
        return Result.data(() -> tspVehicleService.delete(tspVehicleId));
    }

    /**
     * 批量删除
     */
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:deletes')")
    @ApiOperation("批量删除")
    @Log(title = "车辆信息 - 批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/deletes/{tspVehicleIds}")
    public Result deletes(@PathVariable Long[] tspVehicleIds) {
        return Result.data(() -> tspVehicleService.deletes(tspVehicleIds));
    }

    /**
     * 绑定
     */
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:bind')")
    @ApiOperation("根据车辆id和用户id进行车辆绑定")
    @Log(title = "车辆信息 - 绑定", businessType = BusinessType.UPDATE)
    @PatchMapping("/bind/{tspVehicleId}/{tspUserId}")
    public Result bind(@PathVariable Long tspVehicleId,@PathVariable Long tspUserId) {
        return Result.data(() -> tspVehicleService.bind(tspVehicleId,tspUserId));
    }

    /**
     * 报废
     */
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:scrap')")
    @ApiOperation("报废")
    @Log(title = "车辆信息 - 报废", businessType = BusinessType.UPDATE)
    @PutMapping("/scrap")
    public Result scrap(@RequestBody @Valid TspVehicleScrapVO vo){
        return Result.data(() -> tspVehicleService.scrap(vo));
    }

    /**
     * 获取详情
     */
    @ApiOperation("详情")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:get')")
    @GetMapping("/get/{tspVehicleId}")
    public Result<TspVehicleInfoDTO> get(@PathVariable Long tspVehicleId){
        return Result.data(tspVehicleService.get(tspVehicleId));
    }

    /**
     * 设备解绑
     */
    @ApiOperation("根据设备id进行设备解绑")
    @GetMapping("/dealEquipment/{tspEquipmentId}")
    public Result dealEquipment(@PathVariable Long tspEquipmentId){
        return Result.data(()->tspVehicleService.dealEquipment(tspEquipmentId));
    }

    @ApiOperation("导入出厂信息")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:importVehicle')")
    @Log(title = "车辆 - 导入出厂信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importVehicle")
    public Result importVehicle(@RequestParam("file") MultipartFile multipartFile,Boolean isUpdateSupport) {
        return Result.success(tspVehicleService.importVehicle(multipartFile,isUpdateSupport));
    }


    @ApiOperation("车辆销售信息导入")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:importSales')")
    @Log(title = "车辆 - 导入销售信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importSales")
    public Result importSales(@RequestParam("file") MultipartFile multipartFile,Boolean isUpdateSupport) {
        return Result.success(tspVehicleService.importSales(multipartFile,isUpdateSupport));
    }

    @ApiOperation("车辆导出")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:export')")
    @Log(title = "车辆 - 导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody @Valid TspVehiclePageListVO vo) {
        List<TspVehicleExportListDTO> list = tspVehicleService.exportList(vo);
        ExcelUtil<TspVehicleExportListDTO> util = new ExcelUtil<TspVehicleExportListDTO>(TspVehicleExportListDTO.class);
        return util.exportExcel(list, "车辆信息");
    }

    /**
     * 认证信息
     */
    @ApiOperation("根据车辆id查询认证信息")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:getAuditInfo')")
    @GetMapping("/getAuditInfo/{tspVehicleId}")
    public Result<TspVehicleAuditInfoDTO> getAuditInfo(@PathVariable Long tspVehicleId){
        return Result.data(tspVehicleService.getAuditInfo(tspVehicleId));
    }

    /**
     * 下载出厂信息模板
     */
    @ApiOperation("下载车辆出厂信息模板")
    @GetMapping("/importExFactoryTemplate")
    public AjaxResult importExFactoryTemplate() {
        ExcelUtil<TspVehicleExFactoryTemplateDTO> util = new ExcelUtil<>(TspVehicleExFactoryTemplateDTO.class);
        return util.importTemplateExcel("出厂信息数据");
    }

    /**
     * 下载销售信息模板
     */
    @ApiOperation("下载车辆销售信息模板")
    @GetMapping("/importSaleTemplate")
    public AjaxResult importSaleTemplate() {
        ExcelUtil<TspVehicleSaleTemplateDTO> util = new ExcelUtil<>(TspVehicleSaleTemplateDTO.class);
        return util.importTemplateExcel("销售信息数据");
    }

    /**
     * 文件上传
     */
    @ApiOperation("根据type进行文件上传")
    @PostMapping("/upload/{type}")
    public Result<String> upload(@RequestParam("file") MultipartFile file,@PathVariable Integer type){
        return Result.data(tspVehicleService.upload(file,type));
    }

    /**
     * 关联账号
     * @return
     */
    @ApiOperation("车辆关联账号")
    @GetMapping("/relationMobileOption")
    public Result<List<TspVehicleRelationMobileOptionDTO>> relationMobileOption(){
        return Result.data(tspVehicleService.relationMobileOption());
    }

    @ApiOperation("车辆统计")
    @GetMapping("/data")
    public Result<TspVehicleDataDTO> data(){
        return Result.data(tspVehicleService.data());
    }


    /**
     * 下发指令
     */
    @ApiOperation("旧下发指令（车辆符合性测试用）")
    @PostMapping("/downloadPackage")
    public Result downloadPackage(@RequestBody @Valid TspDownloadPackageDTO dto){
        return Result.data(()->tspVehicleService.downloadPackage(dto));
    }

    /**
     * 下发指令
     */

    @ApiOperation("下发指令")
    @PostMapping("/sendCommand")
    public Result sendCommand(@RequestBody @Valid TspVehicleCommandDTO tspVehicleCommandDTO){
        return Result.data(()->tspVehicleService.sendCommand(tspVehicleCommandDTO));
    }

    /**
     * 获取指令执行结果
     */
    @ApiOperation("根据vin获取指令执行结果")
    @GetMapping("/commandExecuteResult/{vin}")
    public Result<VehicleCommand> commandExecuteResult(@PathVariable String vin){
        return Result.data(tspVehicleService.getCommandExecuteResult(vin));
    }

//    @PreAuthorize("@ss.hasPermi('tsp:vehicle:equipmentHistory')")
    @ApiOperation("根据车辆id查询设备分页列表")
    @GetMapping("/equipmentHistory/{tspVehicleId}")
    public Result<PageInfo<TspEquipmentPageListDTO>> equipmentHistory(@PathVariable Long tspVehicleId) {
        return Result.data(tspVehicleService.equipmentHistory(tspVehicleId));
    }

    @ApiOperation("根据设备id查询设备绑定信息")
    @GetMapping("/equipmentNow/{tspEquipmentId}")
    public Result<PageInfo<TspEquipmentPageListDTO>> equipmentNow(@PathVariable Long tspEquipmentId) {
        return Result.data(tspVehicleService.equipmentNow(tspEquipmentId));
    }

    @ApiOperation("车辆导出出厂信息")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:exportExFactory')")
    @Log(title = "车辆 - 导出出厂信息", businessType = BusinessType.EXPORT)
    @PostMapping("/exportExFactory")
    public AjaxResult exportExFactory(@RequestBody @Valid TspVehiclePageListVO vo) {
        List<TspVehicleExFactoryTemplateDTO> list = tspVehicleService.exportExFactory(vo);
        ExcelUtil<TspVehicleExFactoryTemplateDTO> util = new ExcelUtil<>(TspVehicleExFactoryTemplateDTO.class);
        return util.exportExcel(list, "车辆出厂信息");
    }

    @ApiOperation("车辆导出销售信息")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:exportSales')")
    @Log(title = "车辆 - 导出销售信息", businessType = BusinessType.EXPORT)
    @PostMapping("/exportSales")
    public AjaxResult exportSales(@RequestBody @Valid TspVehiclePageListVO vo) {
        List<TspVehicleSaleTemplateDTO> list = tspVehicleService.exportSales(vo);
        ExcelUtil<TspVehicleSaleTemplateDTO> util = new ExcelUtil<>(TspVehicleSaleTemplateDTO.class);
        return util.exportExcel(list, "车辆销售信息");
    }

    /**
     * 获取实名认证详情
     */
    @ApiOperation("根据车辆id查询实名认证详情")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:getRealNameResult')")
    @GetMapping("/getRealNameResult/{tspVehicleId}")
    public Result<Map<String,Object>> getRealNameResult(@PathVariable Long tspVehicleId){
        return Result.data(tspVehicleService.getRealNameResult(tspVehicleId));
    }

    /**
     * 单车监控列表
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('tsp:vehicle:listVehicle')")
    @ApiOperation("单车监控列表")
    @PostMapping("/listVehicle")
    public Result<PageInfo<TspVehiclePageListDTO>> listVehicle(@RequestBody @Valid TspVehiclePageListVO vo) {
        return Result.data(tspVehicleService.listVehicle(vo));
    }

    /**
     * 获取绑定信息详情
     */
    @ApiOperation("根据车辆id查询绑定信息详情")
//    @PreAuthorize("@ss.hasPermi('tsp:vehicle:get')")
    @GetMapping("/getBind/{tspVehicleId}")
    public Result<List<Map<String,Object>>> getBind(@PathVariable Long tspVehicleId){
        return Result.data(tspVehicleService.getBind(tspVehicleId));
    }

    /**
     * 经销商名称下拉
     * @return
     */
    @ApiOperation("经销商名称下拉")
    @GetMapping("/saleNameList")
    public Result<List<Map<String,String>>> saleNameList(){
        return Result.data(tspVehicleService.saleNameList());
    }
    /**
     * 经销商名称下拉
     * @return
     */
    @ApiOperation("通过经销商地址模糊查询经销商列表信息")
    @GetMapping("/saleNameListByLikeAddress")
    public Result<List<TspDealer>> saleNameListByLikeAddress(@RequestParam("address") String address){
        return Result.data(tspVehicleService.saleNameListByLikeAddress(address));
    }

    /**
     * 由经销商名称获取经销商地址
     * @return
     */
    @ApiOperation("根据经销商名称查询经销商地址")
    @GetMapping("/saleNameGetAddress/{dealerName}")
    public Result<Map<String,String>> saleNameGetAddress(@PathVariable("dealerName") String dealerName){
        return Result.data(tspVehicleService.saleNameGetAddress(dealerName));
    }
}

