package com.modern.tsp.waring.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.controller.BaseController;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.TableDataInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.waring.domain.WarningInfo;
import com.modern.tsp.waring.service.WarningInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预警规则信息Controller
 *
 * @author piaomiao
 */
@RestController
@RequestMapping("/waring/info")
public class WarningInfoController extends BaseController {

    @Resource
    private WarningInfoService warningInfoService;

    /**
     * 查询预警规则信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WarningInfo warningInfo) {
        startPage();
        List<WarningInfo> list = warningInfoService.selectWarningInfoList(warningInfo);
        return getDataTable(list);
    }

    /**
     * 导出预警规则信息列表
     */
    @Log(title = "预警规则信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult<?> export(WarningInfo warningInfo) {
        List<WarningInfo> list = warningInfoService.selectWarningInfoList(warningInfo);
        ExcelUtil<WarningInfo> util = new ExcelUtil<>(WarningInfo.class);
        return util.exportExcel(list, "预警规则信息数据");
    }

    /**
     * 获取预警规则信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult<?> getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(warningInfoService.selectWarningInfoById(id));
    }

    /**
     * 新增预警规则信息
     */
    @Log(title = "预警规则信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult<?> add(@RequestBody WarningInfo warningInfo) {
        return toAjax(warningInfoService.insertWarningInfo(warningInfo));
    }

    /**
     * 修改预警规则信息
     */
    @Log(title = "预警规则信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult<?> edit(@RequestBody WarningInfo warningInfo) {
        return toAjax(warningInfoService.updateWarningInfo(warningInfo));
    }

    /**
     * 删除预警规则信息
     * @return 受影响行数
     */
    @Log(title = "预警规则信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<?> remove(@PathVariable Long[] ids) {
        return toAjax(warningInfoService.deleteWarningInfoByIds(ids));
    }

}
