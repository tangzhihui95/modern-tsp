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
 * vehicle_ess_temperature;可充电储能装置温度数据
 * </p>
 *
 * @author piaomiao
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_ess_temperature")
public class VehicleEssTemperature extends BaseExModel {

    private static final long serialVersionUID = 1L;

    /**
     * 可充电储能子系统个数;（N 个可充电储能装置，有效值范围：1～250，“0xFE”表示异常，“0xFF”表示无效。）
     */
    private Integer totalNumber;

    /**
     * 可充电储能子系统号;（有效值范围：1～250）
     */
    private Integer sequenceNumber;

    /**
     * 可充电储能温度探针个数N;（N 个温度探针，有效值范围：1～32766，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    private Integer probeNumber;

    /**
     * 可充电储能子系统各温度探针监测到的温度值1*N;（有效值范围：0～250 （数值偏移量 40℃，表示-40℃～+210℃），最小计量单元：1℃， “0xFE”表示异常，“0xFF”表示无效。）
     */
    private byte[] temperatures;

    public static final String TOTAL_NUMBER = "total_number";

    public static final String SEQUENCE_NUMBER = "sequence_number";

    public static final String PROBE_NUMBER = "probe_number";

    public static final String TEMPERATURES = "temperatures";


}
