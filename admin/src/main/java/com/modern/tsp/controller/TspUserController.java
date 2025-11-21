package com.modern.tsp.controller;


import com.modern.common.annotation.Log;
import com.modern.common.core.Result;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.FrontPageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.domain.TspUser;
import com.modern.tsp.domain.TspUserDTO;
import com.modern.tsp.model.dto.TspUserPageListDTO;
import com.modern.tsp.model.vo.TspUserAddVO;
import com.modern.tsp.model.vo.TspUserExcelVO;
import com.modern.tsp.model.vo.TspUserPageListVO;
import com.modern.tsp.service.TspUserService;
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
 * 摩登 - TSP - TSP用户 前端控制器
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/tsp/user")
@Api(tags = "TSP - 用户")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspUserController {

    private final TspUserService tspUserService;


    /**
     * 列表
     */
//    @PreAuthorize("@ss.hasPermi('tsp:user:list')")
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public Result<FrontPageInfo<TspUserPageListDTO>> list(@RequestBody @Valid TspUserPageListVO vo){
        return Result.data(tspUserService.getPageList(vo));
    }

    /**
     * 添加
     */
    @PreAuthorize("@ss.hasAnyPermi('tsp:user:add')")
    @Log(title = "一般用户 - 添加", businessType = BusinessType.INSERT)
    @ApiOperation("添加")
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TspUserAddVO vo){
        return Result.data(() -> tspUserService.add(vo));
    }


    /**
     * 编辑
     */
    @PreAuthorize("@ss.hasAnyPermi('tsp:user:edit')")
    @Log(title = "一般用户 - 编辑", businessType = BusinessType.UPDATE)
    @ApiOperation("编辑")
    @PutMapping("/edit")
    public Result edit(@RequestBody @Valid TspUserAddVO vo){
        return Result.data(() -> tspUserService.edit(vo));
    }

    /**
     * 查看详情
     */
//    @PreAuthorize("@ss.hasAnyPermi('tsp:user:get')")
    @ApiOperation("用户详情")
    @GetMapping("/get/{tspUserId}")
    public Result<TspUserDTO> get(@PathVariable("tspUserId")Long tspUserId){
        return Result.data(tspUserService.get(tspUserId));
    }

    /**
     * 查看详情
     */
//    @PreAuthorize("@ss.hasAnyPermi('tsp:user:find')")
    @ApiOperation("用户车辆详情")
    @GetMapping("/find/{tspUserId}")
    public Result<List<Map<String,Object>>> findCarInfo(@PathVariable("tspUserId")Long tspUserId){
        return Result.data(tspUserService.findCarInfo(tspUserId));
    }

    /**
     * 查看历史绑定
     */
//    @PreAuthorize("@ss.hasAnyPermi('tsp:user:findHistory')")
    @ApiOperation("历史绑定记录")
    @GetMapping("/findHistory/{tspUserId}")
    public Result<List<Map<String,Object>>> findHistory(@PathVariable("tspUserId")Long tspUserId){
        return Result.data(tspUserService.findHistory(tspUserId));
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('tsp:user:deletes')")
    @Log(title = "一般用户 - 删除", businessType = BusinessType.DELETE)
    @ApiOperation("删除")
    @DeleteMapping("deletes/{tspUserIds}")
    public Result deletes(@PathVariable("tspUserIds")Long[] tspUserIds){
        return Result.data(() -> tspUserService.deletes(tspUserIds));
    }


    /**
     * 导入
     */
    @ApiOperation("导入")
    @PreAuthorize("@ss.hasPermi('tsp:user:importUser')")
    @Log(title = "一般用户 - 导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importUser")
    public Result importUser(MultipartFile file, Boolean isUpdateSupport) {
        return Result.success(tspUserService.importUser(file,isUpdateSupport));
    }


    @ApiOperation("下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<TspUserExcelVO> util = new ExcelUtil<>(TspUserExcelVO.class);
        return util.importTemplateExcel("一般用户信息数据");
    }

    @ApiOperation("导出")
    @PreAuthorize("@ss.hasPermi('tsp:user:export')")
    @Log(title = "一般用户 - 导出", businessType = BusinessType.EXPORT)
    @GetMapping("/exportUser")
    public AjaxResult export(TspUserPageListVO vo) {
        List<TspUserPageListDTO> list = tspUserService.exportList(vo);
        ExcelUtil<TspUserPageListDTO> util = new ExcelUtil<TspUserPageListDTO>(TspUserPageListDTO.class);
        return util.exportExcel(list, "一般用户信息");
    }


}

