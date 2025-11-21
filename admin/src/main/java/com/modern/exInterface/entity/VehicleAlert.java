package com.modern.exInterface.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.core.domain.BaseExModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * vehicle_alert;报警数据
 * </p>
 *
 * @author piaomiao
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_alert")
public class VehicleAlert extends BaseExModel {

    private static final long serialVersionUID = 1L;

    /**
     * 最高报警等级;（在当前发生的通用报警中，级别最高的报警所处于的等级。有效值范围：0～3，“0”表示无故障；“1”表示 1 级故障，指代不影响车辆正常行驶的故障；“2”表示 2 级故障，指代影响车辆性能，需驾驶员限制行驶的故障；“3”表示 3 级故障，为最高级别故障，指代驾驶员应立即停车处理或请求救援的故障； “0xFE”表示异常，“0xFF”表示无效。）
     */
    private Integer maxAlarmLevel;

    /**
     * 通用报警标志;（通用报警标志位定义见表 18）
     */
    private Integer generalAlarmSign;

    @TableField(exist = false)
    private Integer generalAlarmSignStatus;

    @TableField(exist = false)
    private String generalAlarmSignText;

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
     * 数据来源0-T_BOX 1-TSP
     */
    private int alertSource;

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


    public static final String VIN = "vin";

    public static final String DATA_TYPE = "data_type";

    public static final String MAX_ALARM_LEVEL = "max_alarm_level";

    public static final String GENERAL_ALARM_SIGN = "general_alarm_sign";

    public static final String ESS_TOTAL_FAULT = "ess_total_fault";

    public static final String ESS_FAULT_CODES = "ess_fault_codes";

    public static final String DRIVE_MOTOR_TOTAL_FAULT = "drive_motor_total_fault";

    public static final String DRIVE_MOTOR_FAULT_CODES = "drive_motor_fault_codes";

    public static final String ENGINE_TOTAL_FAULT = "engine_total_fault";

    public static final String ENGINE_FAULT_CODES = "engine_fault_codes";

    public static final String OTHER_TOTAL_FAULT = "other_total_fault";

    public static final String OTHER_FAULT_CODES = "other_fault_codes";

    public static final String ALERT_SOURCE = "alert_source";

}
