package com.modern.tsp.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.tsp.model.dto.TspDealerInfoDTO;
import com.modern.tsp.model.dto.TspDealerPageListDTO;
import com.modern.tsp.model.vo.TspDealerAddVO;
import com.modern.tsp.model.vo.TspDealerPageListVO;
import com.modern.tsp.service.TspDealerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 15:05
 * @Version 1.0.0
 */
@RestController
@Api(tags = "TSP - 经销商管理")
@RequestMapping("/tsp/dealer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspDealerController {

    private final TspDealerService tspDealerService;

//    @PreAuthorize("@ss.hasPermi('tsp:dealer:list')")
    @ApiOperation("列表")
    @PostMapping("/list")
    public Result<PageInfo<TspDealerPageListDTO>> list(@RequestBody @Valid TspDealerPageListVO vo) {
        return Result.data(tspDealerService.list(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:add')")
    @ApiOperation("添加")
    @Log(title = "经销商管理 - 添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspDealerAddVO vo) {
        return Result.data(()->tspDealerService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:get')")
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public Result<TspDealerInfoDTO> get(@PathVariable("id") Long tspDealerId) {
        return Result.data(tspDealerService.get(tspDealerId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:edit')")
    @ApiOperation("修改")
    @Log(title = "经销商管理 - 修改", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspDealerAddVO vo) {
        return Result.data(()->tspDealerService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:delete')")
    @ApiOperation("删除")
    @Log(title = "经销商管理 - 删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long tspDealerId) {
        return Result.data(()->tspDealerService.delete(tspDealerId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:deletes')")
    @ApiOperation("批量删除")
    @Log(title = "经销商管理 - 批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/deletes/{tspDealerIds}")
    public Result deletes(@PathVariable Long[] tspDealerIds) {
        return Result.data(()->tspDealerService.batchDelete(tspDealerIds));
    }
}
