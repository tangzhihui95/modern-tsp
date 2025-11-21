package com.modern.exInterface.model.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/2 10:29
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("历史数据(报警数据) - 数据传输对象 - 分页列表")
public class VehicleAlertPageListDTO extends BaseDTO {

    @ApiModelProperty(value = "报警主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long vehicleAlertId;

    /**
     * 数据类型;（0x02实时数据，0x03补发数据）
     */
    private String dataTypeStr;

    /**
     * VIN;（应符合GB16735的规定）
     */
    @Excel(name = "VIN",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String vin;

    /**
     * 数据类型;（0x02实时数据，0x03补发数据）
     */
    private Integer dataType;

    /**
     * 最高报警等级;（在当前发生的通用报警中，级别最高的报警所处于的等级。有效值范围：0～3，“0”表示无故障；“1”表示 1 级故障，指代不影响车辆正常行驶的故障；“2”表示 2 级故障，指代影响车辆性能，需驾驶员限制行驶的故障；“3”表示 3 级故障，为最高级别故障，指代驾驶员应立即停车处理或请求救援的故障； “0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "报警等级",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private Integer maxAlarmLevel;

    /**
     * 通用报警标志;（通用报警标志位定义见表 18）
     */
    private Integer generalAlarmSign;

    /**
     * 可充电储能装置故障总数 N1;（N1 个可充电存储装置故障，有效值范围：0～252，“0xFE” 表示异常，“0xFF”表示无效。）
     */
    private Integer essTotalFault;

    /**
     * 可充电储能装置故障代码列表4×N1;（可充电储能装置故障个数等于可充电储能装置故障总数 N1。见可充电储能装置故障代码列表。）
     */
    private byte[] essFaultCodes;

    /**
     * 驱动电机故障总数N2;（N2个驱动电机故障，有效值范围：0～252，“0xFE”表示异常，“0xFF”表示无效。）
     */
    private Integer driveMotorTotalFault;

    /**
     * 驱动电机故障代码列表4×N2;（电机故障个数等于电机故障总数N2。 见驱动电机故障代码列表。）
     */
    private byte[] driveMotorFaultCodes;

    /**
     * 发动机故障总数N3;（A2001 固定传 0。 因为汽车是电动汽车，无燃油发动机，所以无此项数据）
     */
    private Integer engineTotalFault;

    /**
     * 发动机故障列表;（无数据，因为汽车是电动汽车，无燃油发动机，所以无此项数据）
     */
    private byte[] engineFaultCodes;

    /**
     * 其他故障总数N4;（N4个其他故障，有效值范围：0～252，“0xFE”表示异常，“0xFF”表示无效。）
     */
    private Integer otherTotalFault;

    /**
     * 其他故障代码列表;（故障个数等于故障总数 N4 。见其他系统故障代码列表。）
     */
    private byte[] otherFaultCodes;

    /**
     * 其他故障代码列表;（故障个数等于故障总数 N4 。见其他系统故障代码列表。）
     */
    @Excel(name = "车牌号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String plateCode;

    /**
     * 报警处理时间
     */
    @Excel(name = "处理时间",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dealTime;

    @Excel(name = "处理状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @TableField(exist = false)
    private String dealText = Objects.isNull(dealTime)?"否":"是";

    @Excel(name = "采集时间",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectTime;

    @Excel(name = "上报时间",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("上报时间")
    private LocalDateTime escalationTime;

}
