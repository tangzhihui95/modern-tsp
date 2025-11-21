package com.modern.web.controller.monitor;

import java.util.List;
import java.util.stream.Collectors;

import com.modern.common.core.Result;
import com.modern.web.domain.dto.log.SysOperLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.modern.common.annotation.Log;
import com.modern.common.core.controller.BaseController;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.TableDataInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.web.domain.system.SysOperLog;
import com.modern.web.service.system.ISysOperLogService;

/**
 * 操作日志记录
 *
 * @author piaomiao
 */
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController
{
    @Autowired
    private ISysOperLogService operLogService;

    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysOperLog operLog)
    {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @GetMapping("/export")
    public AjaxResult export(SysOperLog operLog, @RequestParam(required = false, value = "ids")List<Long> ids)
    {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        if(ids != null && ids.size() > 0) {
            list = list.stream().filter(t -> ids.contains(t.getOperId())).collect(Collectors.toList());
        }
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        return util.exportExcel(list, "操作日志");
    }

    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable Long[] operIds)
    {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    public AjaxResult clean()
    {
        operLogService.cleanOperLog();
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:operlog:options')")
    @GetMapping("/options")
    public Result<List<SysOperLogDTO>> options(@RequestParam(value = "title",required = false)String title){
        return Result.data(operLogService.options(title));
    }
}
