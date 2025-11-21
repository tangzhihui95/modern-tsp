package com.modern.exInterface.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 15:16
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
public class VehicleAlertDTO {

    @ApiModelProperty(value = "报警主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long vehicleAlertId;

    /**
     * vin码
     */
    private String vin;

    /**
     * 车牌号
     */
    private String plateCode;

    /**
     * 车主
     */
    private String realName;

    /**
     * 处理状态
     */
    private String status;

    /**
     * 最高报警等级;（在当前发生的通用报警中，级别最高的报警所处于的等级。有效值范围：0～3，“0”表示无故障；“1”表示 1 级故障，指代不影响车辆正常行驶的故障；“2”表示 2 级故障，指代影响车辆性能，需驾驶员限制行驶的故障；“3”表示 3 级故障，为最高级别故障，指代驾驶员应立即停车处理或请求救援的故障； “0xFE”表示异常，“0xFF”表示无效。）
     */
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
     * 通用报警标志;（通用报警标志位定义见表 18）
     */
    @ApiModelProperty("报警信息")
    private List<Map<String,Integer>> generalAlarmSigns;

    /**
     * 报警处理时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dealTime;

    /**
     * 处理详情
     */
    private String dealContent;

}
