package com.modern.exInterface.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.domain.TspEquipment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author piaomiao
 * @apiNode VehicleIntegrateInfoDTO
 * @date 2022/6/2412:14
 * <p>
 * Copyright: Copyright (C) 2019 tlcd, Inc. All rights reserved.
 * <p>
 * Company: 湖南成为科技有限公司
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆监控信息 - 整车数据详情")
public class VehicleGpsInfoDTO extends BaseDTO {

    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String plateCode;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String realName;


    /**
     * 手机号(账号)
     */
    @ApiModelProperty("手机号(账号)")
    private String mobile;


    /**
     * 车牌颜色
     */
    @ApiModelProperty("车牌颜色")
    private String plateColour;

    /**
     * 车身颜色
     */
    @ApiModelProperty("颜色")
    private String color;


    /**
     * VIN
     */
    @ApiModelProperty("VIN")
    private String vin;


    /**
     * 车型名称
     */
    @ApiModelProperty("车型名称")
    private String vehicleModelName;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatar;


    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private String latitude;


    /**
     * 地址信息
     */
    @ApiModelProperty("地址信息")
    private String address;

    /**
     * 电池电量
     */
    private Integer soc;

    /**
     * 告警状态
     */
    private boolean alertStatus;

    /**
     * 在线状态
     */
    private boolean onlineStatus;

    /**
     * 充电状态
     */
    private boolean chargeStatus;

    /**
     * 登入时间
     */
    @ApiModelProperty("登入时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtLogin;

    private LocalDateTime collectTime;


    /**
     * SIM卡ICCID
     */
    @ApiModelProperty("SIM卡ICCID")
    private String iccid;

    /**
     * SIM卡imei
     */
    @ApiModelProperty("SIM卡imei")
    private String imei;


    /**
     * 登出时间
     */
    @ApiModelProperty("登出时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtLogout;

    private TspEquipment tspEquipment;

}
