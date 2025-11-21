package com.modern.tsp.abstracts.alertEvent.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleEssTemperature;
import com.modern.exInterface.entity.VehicleExtreme;
import com.modern.exInterface.repository.VehicleEssTemperatureRepository;
import com.modern.tsp.abstracts.alertEvent.data.base.TspEventDataBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/26 15:41
 * 温度差异告警
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspEventDataEssTemperatureService implements TspEventDataBaseService {

    private final VehicleEssTemperatureRepository vehicleEssTemperatureRepository;
    // 用来记录已经被扫描过的数据
    private static final Map<String, List<Long>> CACHE_OBJECT_MAP = new ConcurrentHashMap<>();

    @Override
    public List<String> isAlert(Time monitorStartTime, Time monitorEndTime, String operator, String value) {
        QueryWrapper<VehicleEssTemperature> ew = new QueryWrapper<>();
//        ew.eq(VehicleEssTemperature.DELETED, 0);
        ew.eq("=".equals(operator), "temperatures", Integer.parseInt(value));
        ew.gt(">".equals(operator), "temperatures", Integer.parseInt(value));
        ew.lt("<".equals(operator), "temperatures", Integer.parseInt(value));
        ew.notIn(StringUtils.isNotEmpty(CACHE_OBJECT_MAP.get("ids")),VehicleEssTemperature.ID, CACHE_OBJECT_MAP.get("ids"));
        if (StringUtils.isNotNull(monitorStartTime) && StringUtils.isNotNull(monitorEndTime)){
            ew.ge(VehicleEssTemperature.COLLECT_TIME, LocalDate.now() + " " + monitorStartTime)
                    .le(VehicleEssTemperature.COLLECT_TIME, LocalDate.now() + " " + monitorEndTime);
        }
        List<VehicleEssTemperature> list = vehicleEssTemperatureRepository.list(ew);
        // 记录ID已扫描过的不再扫描
        CACHE_OBJECT_MAP.remove("ids");
        List<Long> ids = list.stream().map(VehicleEssTemperature::getId).collect(Collectors.toList());
        CACHE_OBJECT_MAP.put("ids", ids);
        return list.stream().map(VehicleEssTemperature::getVin).collect(Collectors.toList());
    }
}
