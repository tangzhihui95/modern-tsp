package com.modern.exInterface.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/7 16:41
 */
@Data
@ApiModel("TSP - 数据传输对象 - EMMC存储容量信息")
public class VehicleEmmcDTO {

    @ApiModelProperty("容量")
    private String capacity;

    @ApiModelProperty("使用容量")
    private String usedCapacity;

    @ApiModelProperty("剩余容量")
    private String surplusCapacity;
}
