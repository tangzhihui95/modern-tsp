package com.modern.xtsp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.xtsp.domain.TspVehicleDailyStatistics;
import com.modern.xtsp.mapper.TspVehicleDailyStatisticsMapper;
import com.modern.xtsp.service.TspVehicleDailyStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@EnableCaching
@Slf4j
@Service
public class TspVehicleDailyStatisticsServiceImpl extends ServiceImpl<TspVehicleDailyStatisticsMapper, TspVehicleDailyStatistics> implements TspVehicleDailyStatisticsService {
    @Override
    public List<TspVehicleDailyStatistics> listByVinAndStatisticsDateBetween(String vin, LocalDate startStatisticsDate, LocalDate endStatisticsDate) {
        LambdaQueryWrapper<TspVehicleDailyStatistics> lqw = new LambdaQueryWrapper<>();
        lqw.eq(TspVehicleDailyStatistics::getVin, vin)
                .between(startStatisticsDate != null && endStatisticsDate != null, TspVehicleDailyStatistics::getStatisticsDate, startStatisticsDate, endStatisticsDate)
                .orderByDesc(TspVehicleDailyStatistics::getId);

        return getBaseMapper().selectList(lqw);
    }
}
