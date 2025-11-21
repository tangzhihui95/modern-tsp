package com.modern.common.core.controller;


import com.modern.common.core.Result;
import com.modern.common.core.domain.Provinces;
import com.modern.common.core.service.ProvincesService;
import com.modern.tsp.model.dto.ProvincesTreeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 区县行政编码字典表 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@RestController
@Api("区县行政编码字典")
@RequestMapping("/provinces")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProvincesController {

    private final ProvincesService provincesService;

    /**
     * 全国省份
     */
    @ApiOperation("全国省份")
    @GetMapping("/provincesAll")
    public Result<List<Provinces>> provincesAll() {
        return Result.data(provincesService.getByDepth(1));
    }

//    @ApiOperation("全国省份树形结构")
//    @GetMapping("/provincesTree")
//    public Result<List<ProvincesTreeDTO>> provincesTree(){
//        return Result.data(provincesService.provincesTree());
//    }

    @ApiOperation("全国省份")
    @GetMapping("/provincesTrees")
    public Result<List<ProvincesTreeDTO>> provincesTrees(){
        return Result.data(provincesService.provincesTrees());
    }

    @ApiOperation("全国城市")
    @GetMapping("/cityTrees/{value}")
    public Result<List<ProvincesTreeDTO>> cityTrees(@PathVariable(value = "value")String value){
        return Result.data(provincesService.cityTrees(value));
    }

    @ApiOperation("全国区域")
    @GetMapping("/areaTrees/{value}")
    public Result<List<ProvincesTreeDTO>> areaTrees(@PathVariable("value") String value){
        return Result.data(provincesService.areaTrees(value));
    }

    /**
     * 根据省份查询地市
     */
    @ApiOperation("根据省份查询地市")
    @GetMapping("/findCitys/{provinceNames}")
    public Result<List<Provinces>> findCitys(@PathVariable("provinceNames") String[] provinceNames) {
        return Result.data(provincesService.findCitys(provinceNames));
    }
}

