package com.modern.exInterface.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.modern.common.core.domain.BaseExModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * vehicle_integrate;整车数据
 * </p>
 *
 * @author piaomiao
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_integrate")
public class VehicleIntegrate extends BaseExModel {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆状态;（0x01：车辆启动状态；0x02：熄火；0x03：其他状态；0xFE：表示异常，0xFF：表示无效。）
     */
    private Integer vehicleState;

    /**
     * 充电状态;（0x01：停车充电；0x02：行使充电；0x03：未充电状态；0x04：充电完成；0xFE：表示异常，0xFF： 表示无效）
     */
    private Integer chargeState;

    /**
     * 运行模式;（0x01: 纯电；0x02：混动；0x03：燃油；0xFE：表示异常；0xFF：表示无效）
     */
    private Integer runMode;

    /**
     * 车速;（有效值范围：0～2200（表示 0 km/h～220 km/h），最小计量单元：0.1km/h，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer speed;

    /**
     * 里程;（有效值范围：0～9999999（表示 0km～999999.9km），最小计量单元：0.1km。“0xFF, 0xFF, 0xFF,0xFE” 表示异常，“0xFF,0xFF,0 xFF,0xFF”表示无效。）
     */
    private Integer mileage;

    /**
     * 总电压;（有效值范围：0～10000（表示 0 V～1000V），最小计量单元：0.1V，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer totalVoltage;

    /**
     * 总电流;（有效值范围：0～20000（偏移量 1000A，表示1000A～+1000A），最小计量单元：0.1A，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer totalCurrent;

    /**
     * 剩余电量;（有效值范围：0～100（表示 0%～100%），最小计量单元：1%，“0xFE”表示异常，“0xFF”表示无效。）
     */
    private Integer soc;

    /**
     * DC-DC 状态;（0x01：工作；0x02：断开，0xFE：表示异常，0xFF： 表示无效。）
     */
    private Integer dcDcState;

    /**
     * 档位
     */
    private Integer gear;

    /**
     * 绝缘电阻;（有效范围 0~60000（表示 0KΩ~60000KΩ），最小计量单元：1KΩ ）
     */
    private Integer insulationResistance;

    /**
     * 加速踏板状态;（有效值范围:0~100( 表示 0 %~100%)，最小计量单元:1%，"0xFE"表示异常，"0xFF"表示无效）
     */
    private Integer acceleratorPedalPosition;

    /**
     * 制动踏板状态;（有效值范围:0-100 表示 0%-100%) ，最小计量单元:1% ,"0"表示制动关的状态;在无具体行程值情况下，用"0x55" 即"101"表示制动有效状态，"0xFE"表示异常， "0xFF"表示无效）
     */
    private Integer brakePedalPosition;


    public static final String VEHICLE_STATE = "vehicle_state";

    public static final String CHARGE_STATE = "charge_state";

    public static final String RUN_MODE = "run_mode";

    public static final String SPEED = "speed";

    public static final String MILEAGE = "mileage";

    public static final String TOTAL_VOLTAGE = "total_voltage";

    public static final String TOTAL_CURRENT = "total_current";

    public static final String SOC = "soc";

    public static final String DC_DC_STATE = "dc_dc_state";

    public static final String GEAR = "gear";

    public static final String INSULATION_RESISTANCE = "insulation_resistance";

    public static final String ACCELERATOR_PEDAL_POSITION = "accelerator_pedal_position";

    public static final String BRAKE_PEDAL_POSITION = "brake_pedal_position";


}
