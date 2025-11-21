package com.modern.tsp.abstracts.alertEvent.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleEssTemperature;
import com.modern.exInterface.entity.VehicleExtreme;
import com.modern.exInterface.repository.VehicleExtremeRepository;
import com.modern.tsp.abstracts.alertEvent.data.base.TspEventDataBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/26 16:59
 * 电池高温告警
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspEventDataExtremeService implements TspEventDataBaseService {
    private final VehicleExtremeRepository vehicleExtremeRepository;
    // 用来记录已经被扫描过的数据
    private static final Map<String, List<Long>> CACHE_OBJECT_MAP = new ConcurrentHashMap<>();

    @Override
    public List<String> isAlert(Time monitorStartTime, Time monitorEndTime, String operator, String value) {
        QueryWrapper<VehicleExtreme> ew = new QueryWrapper<>();
//        ew.eq(VehicleExtreme.DELETED, 0);
        ew.eq("=".equals(operator), "max_temperature", Integer.parseInt(value));
        ew.gt(">".equals(operator), "max_temperature", Integer.parseInt(value));
        ew.lt("<".equals(operator), "max_temperature", Integer.parseInt(value));
        ew.notIn(StringUtils.isNotEmpty(CACHE_OBJECT_MAP.get("ids")),VehicleExtreme.ID, CACHE_OBJECT_MAP.get("ids"));
        if (StringUtils.isNotNull(monitorStartTime) && StringUtils.isNotNull(monitorEndTime)){
            ew.ge(VehicleExtreme.COLLECT_TIME, LocalDate.now() + " " + monitorStartTime)
                    .le(VehicleExtreme.COLLECT_TIME, LocalDate.now() + " " + monitorEndTime);
        }
        List<VehicleExtreme> list = vehicleExtremeRepository.list(ew);
        // 记录ID已扫描过的不再扫描
        CACHE_OBJECT_MAP.remove("ids");
        List<Long> ids = list.stream().map(VehicleExtreme::getId).collect(Collectors.toList());
        CACHE_OBJECT_MAP.put("ids", ids);
        return list.stream().map(VehicleExtreme::getVin).collect(Collectors.toList());
    }
}
