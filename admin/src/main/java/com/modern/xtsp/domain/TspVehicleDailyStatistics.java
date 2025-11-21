package com.modern.xtsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@TableName(value = "tsp_statistics_vehicle_daily")
public class TspVehicleDailyStatistics extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * VIN;（应符合GB16735的规定）
     */
    private String vin;

    /**
     * 日在线时长;（秒）
     */
    private Integer runningTime;

    /**
     * 日行驶里程;（0.1km）
     */
    private Integer mileage;

    /**
     * 日最高报警等级次数;（1级次数，2级次数，3级次数，空格分隔报警次数）
     */
    private String alarmLevelCount;

    /**
     * 日报警次数;（按照通用报警标志位顺序，空格分隔报警次数）
     */
    private String generalAlarmCount;

    /**
     * 日使用流量;（kb）
     */
    private Integer trafficUsage;

    /**
     * 统计的数据日期
     */
    private LocalDate statisticsDate;

    public TspVehicleDailyStatistics(String vin, LocalDate statisticsDate) {
        this.vin = vin;
        this.statisticsDate = statisticsDate;
    }
}
