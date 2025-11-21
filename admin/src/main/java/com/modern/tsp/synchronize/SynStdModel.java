package com.modern.tsp.synchronize;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.tsp.enums.TpsVehicleDataKeyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * TSP二级车型开放同步类
 * @author WS
 * @since 2023-05-15
 */
@Data
@TableName("tsp_vehicle_std_model")
@ApiModel(value = "SynStdModel对象", description = "OTA同步TSP二级车型接口-响应数据")
public class SynStdModel {

    /**
     * 二级车型主键ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
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
    @TableField(value = "is_delete")
    @ApiModelProperty(value = "是否删除  1-是  0-否")
    private Integer isDeleted;

    /**
     * 一级车型ID
     */
    @ApiModelProperty(value = "一级车型ID")
    private Long tspVehicleModelId;

    /**
     * 二级车型名称
     */
    @ApiModelProperty(value = "二级车型名称")
    private String stdModeName;

    /**
     * 能源类型
     */
    @ApiModelProperty(value = "能源类型")
    private TpsVehicleDataKeyEnum dataKey;

    /**
     * 公告批次
     */
    @ApiModelProperty(value = "公告批次")
    private String noticeBatch;

    /**
     * 公告型号
     */
    @ApiModelProperty(value = "公告型号")
    private String noticeModel;
}
