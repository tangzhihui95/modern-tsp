package com.modern.exInterface.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/22 19:03
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆位置数据 - 数据传输对象 - 数据统计列表")
public class VehicleGpsCountListDTO extends BaseDTO {

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
     * gps坐标类型
     */
    @ApiModelProperty("gps坐标类型;（0-3bit: 0bit: 0,有效定位；1,无效定位（当数据通信正常，而不能获取定位信息时，发送最后一次有效定位信息，并将定位状态置为无效。） 1bit: 0,北纬；1,南纬。 2bit: 0,东经；1,西经。)  (4-7bit: 0x0：wgs84（GPS标准坐标）； 0x1：gcj02（火星坐标，即高德地图、腾讯地图和MapABC等地图使用的坐标；）； 0x2：bd09ll（百度地图采用的经纬度坐标）； 0x3：bd09mc（百度地图采用的墨卡托平面坐标））")
    private Integer gpsType;


    /**
     * 地址信息
     */
    @ApiModelProperty("地址信息")
    private String address;

    /**
     * 登入时间
     */
    @ApiModelProperty("登入时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;


    /**
     * SIM卡ICCID
     */
    @ApiModelProperty("SIM卡ICCID")
    private String iccid;


    /**
     * 登出时间
     */
    @ApiModelProperty("登出时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logoutTime;


    /**
     * 通用报警标志;（通用报警标志位定义见表 18）
     */
    @ApiModelProperty("通用报警标志大于0为异常报警")
    private Integer generalAlarmSign;

    @ApiModelProperty("最高报警等级")
    private Integer maxAlarmLevel;


    //整车数据
    @ApiModelProperty("车辆状态")
    private Integer vehicleState;

    @ApiModelProperty("充电状态")
    private Integer chargeState;

    @ApiModelProperty("车速")
    private Integer speed;

    @ApiModelProperty("档位")
    private Integer gear;

    @ApiModelProperty("剩余电量")
    private Integer soc;

    @ApiModelProperty("数据采集时间")
    private LocalDateTime collectTime;

    /**
     * 里程;（有效值范围：0～9999999（表示 0km～999999.9km），最小计量单元：0.1km。“0xFF, 0xFF, 0xFF,0xFE” 表示异常，“0xFF,0xFF,0 xFF,0xFF”表示无效。）
     */
    @ApiModelProperty("里程")
    private Integer mileage;

    //自定义数据
    @ApiModelProperty("计算的车辆状态")
    private String vehicleStateExt;

}
