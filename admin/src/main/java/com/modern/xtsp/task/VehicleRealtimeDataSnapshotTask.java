package com.modern.xtsp.task;

import com.modern.common.constant.RedisContants;
import com.modern.common.utils.LocalDateUtils;
import com.modern.tsp.domain.TspVehicle;
import com.modern.xtsp.service.impl.XTspVehicleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component("VehicleRealtimeDataSnapshotTask")
public class VehicleRealtimeDataSnapshotTask {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private XTspVehicleServiceImpl xTspVehicleService;

    private int MAX_HANDLE_NUM_AT_ONCE = 1000;

    public void vehicleRealtimeDataSnapshot() {
        log.info("------------vehicleRealtimeDataSnapshot  start------------");

        List<TspVehicle> vehicles = xTspVehicleService.getAllVehicles();
        AtomicLong count = new AtomicLong(0L);

        for (int handledNum = 0; handledNum < vehicles.size(); handledNum += MAX_HANDLE_NUM_AT_ONCE) {
            List<TspVehicle> handleVehciles = vehicles.subList(handledNum, Math.min(handledNum + MAX_HANDLE_NUM_AT_ONCE, vehicles.size()));

            // 获取当前redis中车辆缓存实时数据
            List<Object> vehiclesRealtimeData = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                for (int i = 0; i < handleVehciles.size(); i++) {
                    connection.hGetAll((RedisContants.VEHICLE_REALTIME_DATA_PREFIX + handleVehciles.get(i).getVin()).getBytes());
                }
                return null;
            });

            // 将上面获取的数据缓存到新的KEY下
            stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                for (int i = 0; i < handleVehciles.size(); i++) {
                    Map<String, String> realtimeData = (Map<String, String>) vehiclesRealtimeData.get(i);
                    if (realtimeData != null && realtimeData.size() > 0) {
                        HashMap<byte[], byte[]> realtimeDataByte = new HashMap<>();
                        realtimeData.forEach((key, value) -> {
                            realtimeDataByte.put(key.getBytes(), value.getBytes());
                        });

                        connection.hMSet((RedisContants.VEHICLE_DATA_SNAPSHOT_REALTIME_DATA_PREFIX + handleVehciles.get(i).getVin()).getBytes(), realtimeDataByte);
                        connection.expire((RedisContants.VEHICLE_DATA_SNAPSHOT_REALTIME_DATA_PREFIX + handleVehciles.get(i).getVin()).getBytes(), 24 * 60 * 60);
                        count.incrementAndGet();
                    }
                }
                return null;
            });
        }

        stringRedisTemplate.opsForHash().put(RedisContants.VEHICLE_DATA_SNAPSHOT_TIME,
                RedisContants.VEHICLE_REALTIME_DATA_PREFIX.replace(":", ""), LocalDateUtils.getCurrentTimeFormat());

        log.info("------------vehicleRealtimeDataSnapshot success: 车辆总数={}, 缓存车辆数据={}", vehicles.size(), count.longValue());
    }

}
