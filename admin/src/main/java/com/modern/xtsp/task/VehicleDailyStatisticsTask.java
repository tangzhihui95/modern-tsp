package com.modern.xtsp.task;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.RedisContants;
import com.modern.exInterface.entity.VehicleAlert;
import com.modern.exInterface.entity.VehicleLogin;
import com.modern.exInterface.entity.VehicleLogout;
import com.modern.exInterface.service.VehicleAlertService;
import com.modern.exInterface.service.VehicleLoginService;
import com.modern.exInterface.service.VehicleLogoutService;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TspVehicleMarket;
import com.modern.tsp.repository.TspVehicleMarketRepository;
import com.modern.xtsp.domain.TspAllVehicleDailyStatistics;
import com.modern.xtsp.domain.TspVehicleDailyStatistics;
import com.modern.xtsp.service.TspAllVehicleDailyStatisticsService;
import com.modern.xtsp.service.TspVehicleDailyStatisticsService;
import com.modern.xtsp.service.impl.XTspVehicleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component("VehicleDailyStatisticsTask")
public class VehicleDailyStatisticsTask {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private XTspVehicleServiceImpl xTspVehicleService;

    @Autowired
    private VehicleLoginService vehicleLoginService;

    @Autowired
    private VehicleLogoutService vehicleLogoutService;

    @Autowired
    private VehicleAlertService vehicleAlertService;

    @Autowired
    private TspVehicleDailyStatisticsService tspVehicleDailyStatisticsService;

    @Autowired
    private TspAllVehicleDailyStatisticsService tspAllVehicleDailyStatisticsService;

    @Autowired
    private TspVehicleMarketRepository tspVehicleMarketRepository;

    @Value("${my.next_alarm_min_interval}")
    private int nextAlarmMinInterval;  //单位秒，2次报警数据间隔超过该时间则认为是新的一次报警数据

    private LocalDate statisticsDate;

    private int MAX_HANDLE_NUM_AT_ONCE = 1000;

    @Transactional
    public void vehicleDailyStatistics() {
        statisticsDate = LocalDateTimeUtil.now().toLocalDate().minusDays(1);

        log.info("------------vehicleDailyStatistics  start(statisticsDate={})------------", statisticsDate);

        List<TspVehicle> vehicles = xTspVehicleService.getAllVehicles();
        AtomicLong count = new AtomicLong(0L);
        TspAllVehicleDailyStatistics tspAllVehicleDailyStatistics = new TspAllVehicleDailyStatistics(statisticsDate);

        //TODO 先将当前redis数据临时缓存起来

        for (int handledNum = 0; handledNum < vehicles.size(); handledNum += MAX_HANDLE_NUM_AT_ONCE) {
            List<TspVehicle> handleVehciles = vehicles.subList(handledNum, Math.min(handledNum + MAX_HANDLE_NUM_AT_ONCE, vehicles.size()));

            // 1.获取当前redis中车辆缓存实时数据
            List<Object> vehiclesCurrentRealtimeData = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                for (int i = 0; i < handleVehciles.size(); i++) {
                    connection.hGetAll((RedisContants.VEHICLE_REALTIME_DATA_PREFIX + handleVehciles.get(i).getVin()).getBytes());
                }
                return null;
            });

            // 2.计算并存储每日统计数据
            // 2.1 获取昨日redis中车辆缓存实时数据
            List<Object> vehiclesYesterdayRealtimeData = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                for (int i = 0; i < handleVehciles.size(); i++) {
                    connection.hGetAll((RedisContants.VEHICLE_DATA_SNAPSHOT_REALTIME_DATA_PREFIX + handleVehciles.get(i).getVin()).getBytes());
                }
                return null;
            });

            // 2.2 若redis中有缓存昨日数据，则统计
            Map<String, TspVehicleDailyStatistics> statisticsMap = new HashMap<>();
            for (int i = 0; i < handleVehciles.size(); i++) {
                TspVehicle vehicle = handleVehciles.get(i);
                Map<String, String> currentRealtimeData = (Map<String, String>) vehiclesCurrentRealtimeData.get(i);
                Map<String, String> yesterdayRealtimeData = (Map<String, String>) vehiclesYesterdayRealtimeData.get(i);
                if (currentRealtimeData.isEmpty() || yesterdayRealtimeData.isEmpty()) {
                    // 该车辆无比对数据，不进行统计
                    continue;
                }

                //2.2.1里程统计
                statisticsMap = statisticVehicleDailyMileage(statisticsMap, vehicle.getVin(), currentRealtimeData, yesterdayRealtimeData);
            }

            // 2.2.2在线时长统计（只统计statisticsMap中相应Key，即vin的车辆数据）
            statisticsMap = statisticVehicleRunningTime(statisticsMap);

            // 2.2.3日报警次数统计（只统计statisticsMap中相应Key，即vin的车辆数据）
            statisticsMap = statisticAlarmCount(statisticsMap);

            // 2.3保存单车日统计结果到数据库
            tspVehicleDailyStatisticsService.saveBatch(statisticsMap.values());

            // 更新所有车统计数据
            tspAllVehicleDailyStatistics.addVehicleDailyStatistics(statisticsMap);

            // 3.将上述车辆缓存实时数据缓存到新的KEY下
            stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                for (int i = 0; i < handleVehciles.size(); i++) {
                    Map<String, String> realtimeData = (Map<String, String>) vehiclesCurrentRealtimeData.get(i);
                    if (realtimeData != null && realtimeData.size() > 0) {
                        HashMap<byte[], byte[]> realtimeDataByte = new HashMap<>();
                        realtimeData.forEach((key, value) -> {
                            realtimeDataByte.put(key.getBytes(), value.getBytes());
                        });

                        connection.hMSet((RedisContants.VEHICLE_DATA_SNAPSHOT_REALTIME_DATA_PREFIX + handleVehciles.get(i).getVin()).getBytes(), realtimeDataByte);
                        connection.expire((RedisContants.VEHICLE_DATA_SNAPSHOT_REALTIME_DATA_PREFIX + handleVehciles.get(i).getVin()).getBytes(), 48 * 60 * 60);
                        count.incrementAndGet();
                    }
                }
                return null;
            });
        }

        tspAllVehicleDailyStatistics.setNewVehicleCount(statisticNewVehicleCount());

        tspAllVehicleDailyStatisticsService.save(tspAllVehicleDailyStatistics);

        log.info("------------vehicleDailyStatistics success: 车辆总数={}，统计车辆数据数量={}，缓存车辆数据数量={}，", vehicles.size(), tspAllVehicleDailyStatistics.getStatisticsCount(), count.longValue());
    }

    private Map<String, TspVehicleDailyStatistics> statisticVehicleDailyMileage(Map<String, TspVehicleDailyStatistics> statisticsMap, String vin, Map<String, String> currentRealtimeData, Map<String, String> yesterdayRealtimeData) {
        Integer mileage;
        JSONObject currentVehicleIntegrate = JSONObject.parseObject(currentRealtimeData.get("VehicleIntegrate"));
        JSONObject yesterdayVehicleIntegrate = JSONObject.parseObject(yesterdayRealtimeData.get("VehicleIntegrate"));
        if (ObjectUtil.isEmpty(currentVehicleIntegrate) || ObjectUtil.isEmpty(yesterdayVehicleIntegrate)
                || ObjectUtil.isEmpty(currentVehicleIntegrate.get("mileage")) || ObjectUtil.isEmpty(yesterdayVehicleIntegrate.get("mileage"))) {
            // 缺少比对数据
            mileage = null;
        } else {
            mileage = (Integer) currentVehicleIntegrate.get("mileage") - (Integer) (yesterdayVehicleIntegrate.get("mileage"));
        }

        if (statisticsMap.get(vin) == null) {
            statisticsMap.put(vin, new TspVehicleDailyStatistics(vin, statisticsDate));
        }
        statisticsMap.get(vin).setMileage(mileage);

        return statisticsMap;
    }

    @Transactional(readOnly = true)
    public Map<String, TspVehicleDailyStatistics> statisticVehicleRunningTime(Map<String, TspVehicleDailyStatistics> statisticsMap) {
        Map<String, List<VehicleLogin>> vehicleLoginMap = new HashMap<>();
        Map<String, List<VehicleLogout>> vehicleLogoutMap = new HashMap<>();
        statisticsMap.forEach((key, value) -> {
            vehicleLoginMap.put(key, new ArrayList<>());
            vehicleLogoutMap.put(key, new ArrayList<>());
        });


        QueryWrapper<VehicleLogin> qwVehicleLogin = new QueryWrapper<>();
        qwVehicleLogin.orderByDesc(VehicleLogin.ID);
        int currentPage = 1;
        boolean hasTodayData = true;
        while (true) {
            Page<VehicleLogin> page = vehicleLoginService.page(Page.of(currentPage, MAX_HANDLE_NUM_AT_ONCE), qwVehicleLogin);
            List<VehicleLogin> records = page.getRecords();

            for (VehicleLogin vehicleLogin : records) {
                if (isStatisticsDate(vehicleLogin.getLoginTime())) {
                    if (vehicleLoginMap.get(vehicleLogin.getVin()) != null) {
                        vehicleLoginMap.get(vehicleLogin.getVin()).add(vehicleLogin);
                    } else {
                        continue;
                    }
                } else {
                    hasTodayData = false;
                    break;
                }
            }

            if (!hasTodayData || !page.hasNext()) {
                break;
            } else {
                currentPage++;
            }
        }

        QueryWrapper<VehicleLogout> qwVehicleLogout = new QueryWrapper<>();
        qwVehicleLogout.orderByDesc(VehicleLogout.ID);
        currentPage = 1;
        hasTodayData = true;
        while (true) {
            Page<VehicleLogout> page = vehicleLogoutService.page(Page.of(currentPage, MAX_HANDLE_NUM_AT_ONCE), qwVehicleLogout);
            List<VehicleLogout> records = page.getRecords();

            for (VehicleLogout vehicleLogout : records) {
                if (isStatisticsDate(vehicleLogout.getLogoutTime())) {
                    if (vehicleLogoutMap.get(vehicleLogout.getVin()) != null) {
                        vehicleLogoutMap.get(vehicleLogout.getVin()).add(vehicleLogout);
                    } else {
                        continue;
                    }
                } else {
                    hasTodayData = false;
                    break;
                }
            }

            if (!hasTodayData || !page.hasNext()) {
                break;
            } else {
                currentPage++;
            }
        }

        //计算
        statisticsMap.forEach((vin, statistics) -> {
            List<VehicleLogin> vehicleLogins = vehicleLoginMap.get(vin);
            List<VehicleLogout> vehicleLogouts = vehicleLogoutMap.get(vin);

            if (Math.abs(vehicleLogins.size() - vehicleLogouts.size()) > 1) {
                log.error("车辆登入登出数据异常（登入数据{}条，登出数据{}条），vin={}，statisticsDate={}", vehicleLogins.size(), vehicleLogouts.size(), vin, statisticsDate);
                return;  //登入登出数据异常，不统计在线时长
            }

            int runningTime = 0;
            int i = 0, j = 0;
            for (; i < vehicleLogins.size(); i++) {
                if (j < vehicleLogouts.size()) {
                    long between = LocalDateTimeUtil.between(vehicleLogins.get(i).getLoginTime(), vehicleLogouts.get(j).getLogoutTime(), ChronoUnit.SECONDS);
                    if (between >= 0) {
                        //统计日有登入和登出数据
                        runningTime += between;
                        j++;
                    } else {
                        //统计日有登入，无登出数据，则按车辆一直运行到23:59:59统计
                        runningTime += LocalDateTimeUtil.between(vehicleLogins.get(i).getLoginTime(), LocalDateTimeUtil.endOfDay(vehicleLogins.get(i).getLoginTime()), ChronoUnit.SECONDS);
                    }
                } else {
                    //统计日有登入，无登出数据，则按车辆一直运行到23:59:59统计
                    long between = LocalDateTimeUtil.between(vehicleLogins.get(i).getLoginTime(), LocalDateTimeUtil.endOfDay(vehicleLogins.get(i).getLoginTime()), ChronoUnit.SECONDS);
                    runningTime += between;
                    break;
                }
            }
            if (j < vehicleLogouts.size()) {
                //统计日有登出，无登入数据，则按照车辆从0时一直运行到登出时间统计
                runningTime += LocalDateTimeUtil.between(LocalDateTimeUtil.beginOfDay(vehicleLogouts.get(vehicleLogouts.size() - 1).getLogoutTime()),
                        vehicleLogouts.get(vehicleLogouts.size() - 1).getLogoutTime(), ChronoUnit.SECONDS);
            }

            statistics.setRunningTime(runningTime);
        });

        return statisticsMap;
    }

    @Transactional(readOnly = true)
    public Map<String, TspVehicleDailyStatistics> statisticAlarmCount(Map<String, TspVehicleDailyStatistics> statisticsMap) {
        Map<String, List<VehicleAlert>> vehicleAlertMap = new HashMap<>();
        statisticsMap.forEach((key, value) -> {
            vehicleAlertMap.put(key, new ArrayList<>());
        });

        LambdaQueryWrapper<VehicleAlert> qwVehicleAlert = new LambdaQueryWrapper<>();
        qwVehicleAlert.ne(VehicleAlert::getMaxAlarmLevel, 0).orderByDesc(VehicleAlert::getId);
        int currentPage = 1;
        boolean hasTodayData = true;
        while (true) {
            Page<VehicleAlert> page = vehicleAlertService.page(Page.of(currentPage, MAX_HANDLE_NUM_AT_ONCE), qwVehicleAlert);
            List<VehicleAlert> records = page.getRecords();

            for (VehicleAlert vehicleAlert : records) {
                if (isStatisticsDate(vehicleAlert.getCollectTime())) {
                    if (vehicleAlertMap.get(vehicleAlert.getVin()) != null) {
                        vehicleAlertMap.get(vehicleAlert.getVin()).add(vehicleAlert);
                    } else {
                        continue;
                    }
                } else {
                    hasTodayData = false;
                    break;
                }
            }

            if (!hasTodayData || !page.hasNext()) {
                break;
            } else {
                currentPage++;
            }
        }

        //计算
        statisticsMap.forEach((vin, statistics) -> {
            List<VehicleAlert> vehicleAlerts = vehicleAlertMap.get(vin);

            vehicleAlerts = filterVehicleAlerts(vehicleAlerts);

            int[] generalAlarmCountArray = new int[32];
            for (int i = 0; i < 32; i++) {
                generalAlarmCountArray[i] = 0;
            }
            int[] alarmLevelCountArray = {0, 0, 0};

            for (VehicleAlert vehicleAlert : vehicleAlerts) {
                if (vehicleAlert.getMaxAlarmLevel() == 1) {
                    alarmLevelCountArray[0]++;
                } else if (vehicleAlert.getMaxAlarmLevel() == 2) {
                    alarmLevelCountArray[1]++;
                } else if (vehicleAlert.getMaxAlarmLevel() == 3) {
                    alarmLevelCountArray[2]++;
                }

                Integer generalAlarmSign = vehicleAlert.getGeneralAlarmSign();
                for (int i = 0; i < 32; i++) {
                    if (((generalAlarmSign >>> i) & 0x01) == 1) {
                        generalAlarmCountArray[i]++;
                    }
                }
            }

            statistics.setAlarmLevelCount(ArrayUtil.toString(alarmLevelCountArray));
            statistics.setGeneralAlarmCount(ArrayUtil.toString(generalAlarmCountArray));
        });

        return statisticsMap;
    }

    @Transactional(readOnly = true)
    public Long statisticNewVehicleCount() {
        long newVehicleCount = 0;

        LambdaQueryWrapper<TspVehicleMarket> qw = new LambdaQueryWrapper<>();
        qw.orderByDesc(TspVehicleMarket::getId);
        int currentPage = 1;
        boolean hasTodayData = true;
        while (true) {
            Page<TspVehicleMarket> page = tspVehicleMarketRepository.page(Page.of(currentPage, MAX_HANDLE_NUM_AT_ONCE), qw);
            List<TspVehicleMarket> records = page.getRecords();

            for (TspVehicleMarket tspVehicleMarket : records) {
                if (isStatisticsDate(tspVehicleMarket.getInvoicingDate())) {
                    newVehicleCount++;
                } else {
                    hasTodayData = false;
                    break;
                }
            }

            if (!hasTodayData || !page.hasNext()) {
                break;
            } else {
                currentPage++;
            }
        }

        return newVehicleCount;
    }

    private boolean isStatisticsDate(LocalDateTime dataDateTime) {
        return LocalDateTimeUtil.isSameDay(dataDateTime.toLocalDate(), statisticsDate);
    }

    private boolean isStatisticsDate(LocalDate dataDate) {
        return LocalDateTimeUtil.isSameDay(dataDate, statisticsDate);
    }

    private List<VehicleAlert> filterVehicleAlerts(List<VehicleAlert> vehicleAlerts) {
        // 前后数据相差大于30S或最高报警等级不同或通用报警标志位不同则认为是下一次报警
        if (vehicleAlerts == null) {
            return new ArrayList<>();
        } else if (vehicleAlerts.size() <= 1) {
            return vehicleAlerts;
        } else {
            List<VehicleAlert> filtered = new ArrayList<>();
            filtered.add(vehicleAlerts.get(0));
            for (int i = 1; i < vehicleAlerts.size(); i++) {
                if (LocalDateTimeUtil.between(vehicleAlerts.get(i - 1).getCollectTime(), vehicleAlerts.get(i).getCollectTime(), ChronoUnit.SECONDS) > nextAlarmMinInterval
                        || !vehicleAlerts.get(i - 1).getMaxAlarmLevel().equals(vehicleAlerts.get(i).getMaxAlarmLevel())
                        || !vehicleAlerts.get(i - 1).getGeneralAlarmSign().equals(vehicleAlerts.get(i).getGeneralAlarmSign())) {
                    filtered.add(vehicleAlerts.get(i));
                }
            }
            return filtered;
        }
    }
}
