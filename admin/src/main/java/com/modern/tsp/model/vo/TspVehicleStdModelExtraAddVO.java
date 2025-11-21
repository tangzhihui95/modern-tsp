package com.modern.tsp.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.tsp.enums.TspVehicleTransmissionCaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 17:21
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 请求对象 - 添加车型型号扩展信息")
public class TspVehicleStdModelExtraAddVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车辆型号ID")
    private Long tspVehicleStdModelId;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备ID")
    private Long tspEquipmentId = 0L;


    @ApiModelProperty("续航里程")
    private Double extensionMileage;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("慢充时间")
    private LocalDateTime slowChargeTime;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("快充时间")
    private LocalDateTime fastChargeTime;


    @ApiModelProperty("快充百分比(%)")
    private Integer fastPercentage;


    @ApiModelProperty("最高车速")
    private Integer maxSpeed;


    @ApiModelProperty("驱动电机布置型式/位置")
    private String drivingMotorType;


    @ApiModelProperty("电池容量")
    private Integer batteryCapacity;


    @ApiModelProperty("电池类型")
    private Integer batteryType;


    @ApiModelProperty("电池包个数")
    private Integer batteryCount;


    @ApiModelProperty("电池组质保")
    private String batteryPackWarranty;


    @ApiModelProperty("里程检测方式")
    private String mileageDetectio;


    @ApiModelProperty("电池包串并联方式")
    private String batterySeriesParallel;


    @ApiModelProperty("驱动电机数")
    private Integer drivingMotorNumber;


    @ApiModelProperty("电机类型")
    private String motorType;


    @ApiModelProperty("电机总功率")
    private Integer totalPower;


    @ApiModelProperty("电机总扭矩")
    private Integer totalTorqueMotor;


    @ApiModelProperty("综合油耗(L/100km)")
    private Integer oilWear;


    @NotEmpty(message = "环保标准不能为空")
    @ApiModelProperty("环保标准")
    private String environmentalProtection;


    @ApiModelProperty("发动机型号")
    private String engineType;


    @ApiModelProperty("发动机排量(mL)")
    private Integer displacement;


    @ApiModelProperty("气缸数")
    private Integer cylinderNumber;


    @ApiModelProperty("最大功率(kw)")
    private Integer maximumPower;


    @ApiModelProperty("最大扭矩")
    private Integer maximumTorque;

//    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @ApiModelProperty("变速箱")
    private String transmissionCase;


    @ApiModelProperty("额定电压")
    private String ratedVoltage;


    @ApiModelProperty("驱动方式")
    private String drivingMode;


    @ApiModelProperty("整车质保")
    private String vehicleWarranty;


    @ApiModelProperty("车身尺寸(长*宽*高)")
    private String dimensions;


    @ApiModelProperty("数据来源")
    private Integer dataSource;


    @TableField(value = "extra_images", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("车型图片")
    private List<String> extraImages;
}
