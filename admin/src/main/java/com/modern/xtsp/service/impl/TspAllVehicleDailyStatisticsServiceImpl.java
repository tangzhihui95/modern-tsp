package com.modern.xtsp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.xtsp.domain.TspAllVehicleDailyStatistics;
import com.modern.xtsp.mapper.TspAllVehicleDailyStatisticsMapper;
import com.modern.xtsp.service.TspAllVehicleDailyStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@EnableCaching
@Slf4j
@Service
public class TspAllVehicleDailyStatisticsServiceImpl extends ServiceImpl<TspAllVehicleDailyStatisticsMapper, TspAllVehicleDailyStatistics> implements TspAllVehicleDailyStatisticsService {

    @Override
    public List<TspAllVehicleDailyStatistics> getAllByStatisticsDateBetween(LocalDate startStatisticsDate, LocalDate endStatisticsDate) {
        LambdaQueryWrapper<TspAllVehicleDailyStatistics> qw = new LambdaQueryWrapper<>();
        qw.between(startStatisticsDate != null && endStatisticsDate != null, TspAllVehicleDailyStatistics::getStatisticsDate, startStatisticsDate, endStatisticsDate)
                .orderByDesc(TspAllVehicleDailyStatistics::getId);
        return getBaseMapper().selectList(qw);
    }
}
