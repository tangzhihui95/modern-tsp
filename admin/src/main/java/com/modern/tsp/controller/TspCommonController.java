package com.modern.tsp.controller;

import com.modern.common.core.Result;
import com.modern.tsp.service.TspCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/12/17 12:05
 * @Version 1.0.0
 */
@RestController
@Api(tags = "TSP - 公共型接口控制层")
@RequestMapping("/tsp/common")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspCommonController {

    private final TspCommonService tspCommonService;

    @ApiOperation("转vin")
    @GetMapping("/pasToVin/{search}")
    public Result<String> pasToVin(@PathVariable("search") String search) {
        return Result.data(tspCommonService.pasToVin(search));
    }

}
