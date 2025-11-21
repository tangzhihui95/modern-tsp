package com.modern.xtsp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.modern.xtsp.domain.TspAllVehicleDailyStatistics;

import java.time.LocalDate;
import java.util.List;

public interface TspAllVehicleDailyStatisticsService extends IService<TspAllVehicleDailyStatistics> {
    List<TspAllVehicleDailyStatistics> getAllByStatisticsDateBetween(LocalDate startStatisticsDate, LocalDate endStatisticsDate);
}
