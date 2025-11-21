package com.modern.xtsp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.modern.xtsp.domain.TspVehicleDailyStatistics;

import java.time.LocalDate;
import java.util.List;

public interface TspVehicleDailyStatisticsService extends IService<TspVehicleDailyStatistics> {
    List<TspVehicleDailyStatistics> listByVinAndStatisticsDateBetween(String vin, LocalDate startStatisticsDate, LocalDate endStatisticsDate);
}
