package com.modern.tsp.synchronize;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * TSP一级车型开放同步类
 * @author WS
 * @since 2023-05-15
 */
@Data
@TableName("tsp_vehicle_model")
@ApiModel(value = "SynModel对象", description = "OTA同步TSP一级车型接口-响应数据")
public class SynModel {
    /**
     * 一级车型主键ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 是否删除  1-是  0-否
     */
    @ApiModelProperty(value = "是否删除  1-是  0-否")
    @TableField(value = "is_delete")
    private Integer isDeleted;

    /**
     * 一级车型名称
     */
    @ApiModelProperty(value = "一级车型名称")
    private String vehicleModelName;
}
