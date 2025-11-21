package com.modern.tsp.move.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "move_alert_info")
public class MoveAlertInfo extends BaseModel {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 车辆VIN 号
     */
    private String vin;

    /**
     * ICCID
     */
    @TableField(value = "ICCID")
    private String ICCID ;

    /**
     * 报警位置 （文字）
     */
    private String address;

    /**
     * 报警时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "当前绑定时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime errorTime;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 车主姓名
     */
    private String carMaster;

    /**
     * 车主手机
     */
    private String carTel;

    /**
     * 车辆颜色
     */
    private String carColor;

    /**
     *  处理状态 1 已处理，0未处理
     */
    private Integer dealType;

    /**
     * 处理状态翻译字段
     */
    @TableField(exist = false)
    private String dealTypeText ;

    /**
     * 处理详情
     */
    private String dealRemark;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 处理人
     */
    private Long handleUser;

    /**
     * 处理人翻译字段
     */
    @TableField(exist = false)
    private String handlerUserName;

    /**
     * 处理时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "当前绑定时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime handleTime;

    /**
     * 是否监控 1是0否
     */
    private Integer isMonitor;

    @TableField(exist = false)
    private String isMonitorText ;


}
