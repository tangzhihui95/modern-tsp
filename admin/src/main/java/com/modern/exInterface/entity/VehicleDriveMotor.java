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
 * vehicle_drive_motor;驱动电机数据
 * </p>
 *
 * @author piaomiao
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_drive_motor")
public class VehicleDriveMotor extends BaseExModel {

    private static final long serialVersionUID = 1L;

    /**
     * 驱动电机个数;（有效值 1~253）
     */
    private Integer totalNumber;

    /**
     * 驱动电机序号;（有效值范围 1~253）
     */
    private Integer sequenceNumber;

    /**
     * 驱动电机状态;（0x01：耗电；0x02：发电；0x03：关闭状态，“0xFE” 表示异常，“0xFF”表示无效。）
     */
    private Integer state;

    /**
     * 驱动电机控制器温度;（有效值范围：0～250 （数值偏移量 40℃，表示40℃～+210 ℃），最小计量单元：1℃，“0xFE”表示异常，“0xFF”表示无效。）
     */
    private Integer controllerTemperature;

    /**
     * 驱动电机转速;（有效值范围：0～65531（数值偏移量 20000 表示20000 r/min ～ 45531r/min ）， 最小计量单元 ： 1r/min，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer rotateSpeed;

    /**
     * 驱动电机转矩;（有效值范围：0～65531（数值偏移量 20000 表示2000 N*m～4553.1N*m），最小计量单元：0.1N*m，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer torque;

    /**
     * 驱动电机温度;（有效值范围：0～250 （数值偏移量 40℃，表示40℃～+210 ℃），最小计量单元：1℃，“0xFE”表示异常，“0xFF”表示无效。）
     */
    private Integer temperature;

    /**
     * 电机控制器输入电压;（有效值范围：0～60000（表示 0V～6000V），最小计量单元：0.1V，“0xFF,0xFE”表示异常，“0xFF,0xFF” 表示无效。）
     */
    private Integer controllerInputVoltage;

    /**
     * 电机控制器直流母线电流;（有效值范围： 0～20000（数值偏移量 1000A，表示1000A ～ +1000A ）， 最小计量单 元 ： 0.1A ，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer controllerDcBusCurrent;


    public static final String TOTAL_NUMBER = "total_number";

    public static final String SEQUENCE_NUMBER = "sequence_number";

    public static final String STATE = "state";

    public static final String CONTROLLER_TEMPERATURE = "controller_temperature";

    public static final String ROTATE_SPEED = "rotate_speed";

    public static final String TORQUE = "torque";

    public static final String TEMPERATURE = "temperature";

    public static final String CONTROLLER_INPUT_VOLTAGE = "controller_input_voltage";

    public static final String CONTROLLER_DC_BUS_CURRENT = "controller_dc_bus_current";

}
