package com.modern.tsp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/8/9 18:44
 */
@ApiModel("报警数据 - 通用报警标志")
public enum TspVehicleAlertAlarmSignEnum implements IEnum<Integer> {
    /**
     * 温度差异报警
     */
    @ApiModelProperty("温度差异报警")
    TEMPERATURE_DIFFERENCE_ALERT("温度差异报警",0x01),
    /**
     * 电池高温报警
     */
    @ApiModelProperty("电池高温报警")
    BATTERY_HIGH_TEMPERATURE_ALERT("电池高温报警",0x02),
    /**
     * 车载储能装置类型过压报警
     */
    @ApiModelProperty("车载储能装置类型过压报警")
    STORAGE_DEVICE_TYPE_OVER_ALERT("车载储能装置类型过压报警",0x04),
    /**
     * 车载储能装置类型欠压报警
     */
    @ApiModelProperty("车载储能装置类型欠压报警")
    STORAGE_DEVICE_TYPE_UNDER_ALERT("车载储能装置类型欠压报警",0x08),
    /**
     * SOC低报警
     */
    @ApiModelProperty("SOC低报警")
    SOC_ALERT("SOC低报警",0x10),
    /**
     * 单体电池过压报警
     */
    @ApiModelProperty("单体电池过压报警")
    SINGLE_BATTERY_OVER_ALERT("单体电池过压报警",0x20),
    /**
     * 单体电池欠压报警
     */
    @ApiModelProperty("单体电池欠压报警")
    SINGLE_BATTERY_UNDER_ALERT("单体电池欠压报警",0x40),
    /**
     * SOC过高报警
     */
    @ApiModelProperty("SOC过高报警")
    SOC_OVERTOP_ALERT("SOC过高报警",0x80),
    /**
     * SOC跳变报警
     */
    @ApiModelProperty("SOC跳变报警")
    SOC_JUMP_ALERT("SOC跳变报警",0x100),
    /**
     * 可充电储能系统不匹配报警
     */
    @ApiModelProperty("可充电储能系统不匹配报警")
    CHARGE_STORED_SYSTEM_MISMATCH_ALERT("可充电储能系统不匹配报警",0x200),
    /**
     * 电池单体一致性差报警
     */
    @ApiModelProperty("电池单体一致性差报警")
    SINGLE_BATTERY_UNIFORMITY_ALERT("电池单体一致性差报警",0x400),
    /**
     * 绝缘报警
     */
    @ApiModelProperty("绝缘报警")
    INSULATION_ALERT("绝缘报警",0x800),
    /**
     * DC-DC温度报警
     */
    @ApiModelProperty("DC-DC温度报警")
    DC_TEMPERATURE_ALERT("DC-DC温度报警",0x1000),
    /**
     * 制动系统报警
     */
    @ApiModelProperty("制动系统报警")
    BRAKING_SYSTEM_ALERT("制动系统报警",0x2000),
    /**
     * DC-DC状态报警
     */
    @ApiModelProperty("DC-DC状态报警")
    DC_STATE_ALERT("DC-DC状态报警",0x4000),
    /**
     * 驱动电机控制器温度报警
     */
    @ApiModelProperty("驱动电机控制器温度报警")
    DRIVE_CONTROLLER_TEMPERATURE_ALERT("驱动电机控制器温度报警",0x8000),
    /**
     * 高压互锁状态报警
     */
    @ApiModelProperty("高压互锁状态报警")
    HIGH_VOLTAGE_INTERLOCK_ALERT("高压互锁状态报警",0x10000),
    /**
     * 驱动电机温度报警
     */
    @ApiModelProperty("驱动电机温度报警")
    DRIVE_MOTOR_TEMPERATURE_ALERT("驱动电机温度报警",0x20000),
    /**
     * 车载储能装置类型过充报警
     */
    @ApiModelProperty("车载储能装置类型过充报警")
    STORAGE_DEVICE_TYPE_OVERCHARGE_ALERT("车载储能装置类型过充报警",0x40000);


    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspVehicleAlertAlarmSignEnum(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TspVehicleAlertAlarmSignEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
