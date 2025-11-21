package com.modern.tsp.controller;


import com.modern.common.core.Result;
import com.modern.tsp.domain.TspTboxVersion;
import com.modern.tsp.repository.TspTboxVersionRepository;
import com.modern.tsp.service.TspTboxVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * <p>
 * 摩登 - TSP - 设备版本(测试) 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-09-08
 */
@RestController
@RequestMapping("/tsp/tbox/version")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspTboxVersionController {


    private final TspTboxVersionService tspTboxVersionService;

    /**
     * 版本升级
     */
    @PutMapping("updateVersion")
    public Result updateVersion(@RequestBody @Valid TspTboxVersion tspTboxVersion){
        return Result.data(()->tspTboxVersionService.updateVersion(tspTboxVersion));
    }

    /**
     * 查询
     */
    @GetMapping("get/{vin}")
    public Result<TspTboxVersion> get(@PathVariable("vin")String vin){
        return Result.data(tspTboxVersionService.get(vin));
    }

}

