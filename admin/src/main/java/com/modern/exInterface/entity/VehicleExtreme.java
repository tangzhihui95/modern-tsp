package com.modern.exInterface.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.modern.common.core.domain.BaseExModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * vehicle_extreme;极值数据
 * </p>
 *
 * @author piaomiao
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_extreme")
public class VehicleExtreme extends BaseExModel {

    private static final long serialVersionUID = 1L;

    /**
     * 最高电压电池子系统号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    private Integer maxVoltageBatterySubsystem;

    /**
     * 最高电压电池单体代号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    private Integer maxVoltageBatteryCell;

    /**
     * 电池单体电压最高值;（有效值范围：0～15000（表示 0V～15V），最小计量单元： 0.001V ，“ 0xFF,0xFE ” 表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer maxVoltageBattery;

    /**
     * 最低电压电池子系统号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    private Integer minVoltageBatterySubsystem;

    /**
     * 最低电压电池单体代号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    private Integer minVoltageBatteryCell;

    /**
     * 电池单体电压最低值;（有效值范围：0～15000（表示 0V～15V），最小计量单元： 0.001V ，“ 0xFF,0xFE ” 表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer minVoltageBattery;

    /**
     * 最高温度子系统号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    private Integer maxTemperatureSubsystem;

    /**
     * 最高温度探针序号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    private Integer maxTemperatureProbe;

    /**
     * 最高温度值;（有效值范围：0～250（数值偏移量 40℃，表示40℃～+210 ℃），最小计量单元：1℃，“0xFE”表示异常，“0xFF”表示无效）
     */
    private Integer maxTemperature;

    /**
     * 最低温度子系统号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    private Integer minTemperatureSubsystem;

    /**
     * 最低温度探针序号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    private Integer minTemperatureProbe;

    /**
     * 最低温度值;（有效值范围：0～250（数值偏移量 40℃，表示40℃～+210 ℃），最小计量单元：1℃，“0xFE”表示异常，“0xFF”表示无效。）
     */
    private Integer minTemperature;


    public static final String MAX_VOLTAGE_BATTERY_SUBSYSTEM = "max_voltage_battery_subsystem";

    public static final String MAX_VOLTAGE_BATTERY_CELL = "max_voltage_battery_cell";

    public static final String MAX_VOLTAGE_BATTERY = "max_voltage_battery";

    public static final String MIN_VOLTAGE_BATTERY_SUBSYSTEM = "min_voltage_battery_subsystem";

    public static final String MIN_VOLTAGE_BATTERY_CELL = "min_voltage_battery_cell";

    public static final String MIN_VOLTAGE_BATTERY = "min_voltage_battery";

    public static final String MAX_TEMPERATURE_SUBSYSTEM = "max_temperature_subsystem";

    public static final String MAX_TEMPERATURE_PROBE = "max_temperature_probe";

    public static final String MAX_TEMPERATURE = "max_temperature";

    public static final String MIN_TEMPERATURE_SUBSYSTEM = "min_temperature_subsystem";

    public static final String MIN_TEMPERATURE_PROBE = "min_temperature_probe";

    public static final String MIN_TEMPERATURE = "min_temperature";


}
