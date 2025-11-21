package com.modern.tsp.move.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.Result;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.move.entity.MoveAlertInfo;
import com.modern.tsp.move.entity.MoveAlertInfoForm;
import com.modern.tsp.move.service.MoveAlertInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/move/alert")
public class MoveAlertInfoController  {

    @Resource
    private MoveAlertInfoService service;

    @GetMapping("/list")
    public Result<Page<MoveAlertInfo>> list(MoveAlertInfoForm moveAlertInfoForm){

        return Result.data(service.queryPage(moveAlertInfoForm));
    }

    /**
     * 单保存接口
     * @param moveAlertInfoForm 新增报警日志参数
     * @return 操作结果
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody MoveAlertInfoForm moveAlertInfoForm){
        service.baseSave(moveAlertInfoForm);
        return Result.data(true);
    }


    /**
     * 处理时调用的接口
     * @param moveAlertInfoForm 修改时需要携带的参数
     * @return 操作结果
     */
    @PostMapping("/updateForHandle")
    public Result<Boolean> updateForHandle(@RequestBody MoveAlertInfoForm moveAlertInfoForm){
        moveAlertInfoForm.setHandleTime(LocalDateTime.now());
        moveAlertInfoForm.setHandleUser(SecurityUtils.getUserId());
        service.updateById(moveAlertInfoForm);
        return Result.data(true);
    }

    /**
     * 监控时调用该接口
     * @param moveAlertInfoForm 修改监控状态时需要的参数
     * @return 操作结果
     */
    @PostMapping("/updateForMonitor")
    public Result<Boolean> updateForMonitor(@RequestBody MoveAlertInfoForm moveAlertInfoForm){
        return Result.data(service.updateById(moveAlertInfoForm));
    }

    @GetMapping("/getById/{id}")
    public Result<MoveAlertInfo> getById(@PathVariable("id")Long id){
        return Result.data(service.getById(id));
    }

    @GetMapping("/count")
    public Result<Integer> count(){
        return Result.data(service.count());
    }

}
