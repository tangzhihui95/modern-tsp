package com.modern.tsp.synchronize;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * OTA同步TSP车辆接口
 *
 * @author WS
 * @since 2023-05-15
 */
@Data
@TableName("tsp_vehicle")
@ApiModel(value = "SynVehicle对象", description = "OTA同步TSP车辆接口-响应数据")
public class SynVehicle {

    /**
     * 车辆主键ID
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
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 是否删除  1-是  0-否
     */
    @ApiModelProperty(value = "是否删除  1-是  0-否")
    @TableField(value = "is_delete")
    private Integer isDeleted;

    /**
     * vin
     */
    @ApiModelProperty(value = "vin")
    private String vin;

    /**
     * 制造日期
     */
    @ApiModelProperty(value = "制造日期")
    @TableField(value = "ex_factory_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate leaveFactory;

    /**
     * 设备sn
     */
    @ApiModelProperty(value = "设备sn")
    private String sn;

    /**
     * imei
     */
    @ApiModelProperty(value = "imei")
    private String imei;

    /**
     * sim卡
     */
    @ApiModelProperty(value = "sim")
    private String sim;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String plateCode;

    /**
     * 二级车型ID
     */
    @ApiModelProperty(value = "二级车型ID")
    private Long tspVehicleStdModelId;

}
