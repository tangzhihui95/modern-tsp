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
 * vehicle_ess;可充电储能装置电压数
 * </p>
 *
 * @author piaomiao
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_ess")
public class VehicleEss extends BaseExModel {

    private static final long serialVersionUID = 1L;

    /**
     * 可充电储能子系统个数;（N 个可充电储能子系统，有效值范围：1～250，“0xFE”表示异常，“0xFF”表示无效。）
     */
    private Integer totalNumber;

    /**
     * 可充电储能子系统号;（有效值范围：1～250）
     */
    private Integer sequenceNumber;

    /**
     * 可充电储能子系统装置电压;（有效值范围：0～10000（表示 0V～1000V），最小计量单元：0.1V，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。 ）
     */
    private Integer voltage;

    /**
     * 可充电储能子系统装置电流;（有 效 值 范 围 ： 0 ～ 20000 （ 数 值 偏 移 量1000A，表示-1000A～+1000A），最小计量单元：0.1 A，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer current;

    /**
     * 单体电池总数;（N 个电池单体，有效值范围：1～65531，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer batteryNumber;

    /**
     * 本帧起始电池序号;（当本帧单体个数超过 200 时，应拆分成多帧数据进行传输，有效值范围：1～65531）
     */
    private Integer thisBatteryStartIndex;

    /**
     * 本帧单体电池总数M;（本帧单体总数 m;有效值范围：1～200）
     */
    private Integer thisBatteryNumber;

    /**
     * 单体电池电压值2*M;（有效值范围：0～60000（表示 0V～60.000V），最小计量单元：0.001V，单体电池电压个数等于本帧单体电池总数 m，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 ）
     */
    private byte[] thisBatteryVoltages;


    public static final String TOTAL_NUMBER = "total_number";

    public static final String SEQUENCE_NUMBER = "sequence_number";

    public static final String VOLTAGE = "voltage";

    public static final String CURRENT = "current";

    public static final String BATTERY_NUMBER = "battery_number";

    public static final String THIS_BATTERY_START_INDEX = "this_battery_start_index";

    public static final String THIS_BATTERY_NUMBER = "this_battery_number";

    public static final String THIS_BATTERY_VOLTAGES = "this_battery_voltages";


}
