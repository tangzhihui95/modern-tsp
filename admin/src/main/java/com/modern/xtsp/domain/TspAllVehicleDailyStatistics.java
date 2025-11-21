package com.modern.xtsp.domain;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.modern.common.core.domain.BaseModel;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@TableName(value = "tsp_statistics_vehicle_all_daily")
public class TspAllVehicleDailyStatistics extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 所有车在线总数;（辆）
     */
    private Long allRunningCount;

    /**
     * 所有车日在线时长;（秒）
     */
    private Long allRunningTime;

    /**
     * 所有车日行驶里程;（0.1km）
     */
    private Long allMileage;

    /**
     * 所有车日最高报警等级次数;（1级次数，2级次数，3级次数，空格分隔报警次数）
     */
    private String allAlarmLevelCount;

    /**
     * 所有车日报警次数;（按照通用报警标志位顺序，空格分隔报警次数）
     */
    private String allGeneralAlarmCount;

    /**
     * 所有车日使用流量;（kb）
     */
    private Long allTrafficUsage;

    /**
     * 所有新接入车辆数量;（辆）
     */
    private Long newVehicleCount;

    /**
     * 所有车总数
     */
    private Long statisticsCount;

    /**
     * 统计的数据日期
     */
    private LocalDate statisticsDate;

    public TspAllVehicleDailyStatistics(LocalDate statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public void addVehicleDailyStatistics(Map<String, TspVehicleDailyStatistics> statisticsMap) {
        if (allRunningCount == null) {
            allRunningCount = 0L;
        }
        if (allRunningTime == null) {
            allRunningTime = 0L;
        }
        if (allMileage == null) {
            allMileage = 0L;
        }
        if (statisticsCount == null) {
            statisticsCount = 0L;
        }

        statisticsMap.forEach((vin, tspVehicleDailyStatistics) -> {
            allRunningTime += tspVehicleDailyStatistics.getRunningTime() == null ? 0 : tspVehicleDailyStatistics.getRunningTime();
            allMileage += tspVehicleDailyStatistics.getMileage() == null ? 0 : tspVehicleDailyStatistics.getMileage();
            if (tspVehicleDailyStatistics.getRunningTime() != null && tspVehicleDailyStatistics.getRunningTime() > 0) {
                allRunningCount++;
            }
            statisticsCount++;

            Long[] vehicleAlarmLevelCountArray = convertStringToArray(tspVehicleDailyStatistics.getAlarmLevelCount());
            Long[] allAlarmLevelCountArray = convertStringToArray(allAlarmLevelCount);
            for (int i = 0; i < vehicleAlarmLevelCountArray.length; i++) {
                if (i < allAlarmLevelCountArray.length) {
                    allAlarmLevelCountArray[i] += vehicleAlarmLevelCountArray[i];
                } else {
                    allAlarmLevelCountArray = ArrayUtil.append(allAlarmLevelCountArray, vehicleAlarmLevelCountArray[i]);
                }
            }
            allAlarmLevelCount = ArrayUtil.toString(allAlarmLevelCountArray);

            Long[] vehicleGeneralAlarmCountArray = convertStringToArray(tspVehicleDailyStatistics.getGeneralAlarmCount());
            Long[] allGeneralAlarmCountArray = convertStringToArray(allGeneralAlarmCount);
            for (int i = 0; i < vehicleGeneralAlarmCountArray.length; i++) {
                if (i < allGeneralAlarmCountArray.length) {
                    allGeneralAlarmCountArray[i] += vehicleGeneralAlarmCountArray[i];
                } else {
                    allGeneralAlarmCountArray = ArrayUtil.append(allGeneralAlarmCountArray, vehicleGeneralAlarmCountArray[i]);
                }
            }
            allGeneralAlarmCount = ArrayUtil.toString(allGeneralAlarmCountArray);
        });
    }

    private static Long[] convertStringToArray(String arrayString) {
        if (StrUtil.isBlank(arrayString)) {
            return new Long[0];
        }

        // 移除字符串中的方括号和空格
        String trimmedString = arrayString.replaceAll("\\[|\\]| ", "");

        // 将字符串分割成子字符串
        String[] substrings = trimmedString.split(",");

        // 将子字符串转换回整数并存储在数组中
        Long[] result = new Long[substrings.length];
        for (int i = 0; i < substrings.length; i++) {
            result[i] = Long.parseLong(substrings[i]);
        }

        return result;
    }
}
