package com.modern.exInterface.service;

import com.alibaba.fastjson.JSONArray;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.model.LoginUser;
import com.modern.common.utils.SecurityUtils;
import com.modern.exInterface.cache.VehicleRedisCache;
import com.modern.exInterface.entity.*;
import com.modern.exInterface.mapper.VehicleIntegrateMapper;
import com.modern.exInterface.model.dto.*;
import com.modern.tsp.domain.*;
import com.modern.tsp.mapper.TspEquipmentMapper;
import com.modern.tsp.repository.*;
import com.modern.xtsp.service.impl.XTspVehicleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/14 19:42
 * @Version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleDataService {
    private final TspVehicleLicenseRepository tspVehicleLicenseRepository;
    private final TspVehicleStdModeRepository tspVehicleStdModeRepository;
    private final TspUserRepository tspUserRepository;
    private final TspVehicleModelRepository tspVehicleModelRepository;
    private final TspVehicleRepository tspVehicleRepository;
    private final TspEquipmentMapper tspEquipmentMapper;
    private final VehicleRedisCache vehicleRedisCache;
    private final VehicleIntegrateMapper vehicleIntegrateMapper;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final XTspVehicleServiceImpl xTspVehicleService;

    public Map<String, Object> getBasicAttributes(String vin) {
        Map<String, Object> map = new HashMap<>();
        TspVehicle vehicle = tspVehicleRepository.getByVin(vin);
        if (vehicle == null) {
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }
        map.put("tspVehicle", vehicle);
        // 设备信息
        if (Objects.nonNull(vehicle.getTspEquipmentId())){
        TspEquipment equipment = tspEquipmentMapper.selectById(vehicle.getTspEquipmentId());
            if (equipment != null) {
                map.put("tspEquipment", equipment);
            }
        }

        // 车牌信息
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(vehicle.getId());

        map.put("tspVehicleLicense", license);
        // 用户信息
        TspUser tspUser = tspUserRepository.getById(vehicle.getTspUserId());
        map.put("tspUser", tspUser);
        // 车型信息
        TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(vehicle.getTspVehicleStdModelId());
        if(Objects.nonNull(stdModel)){
            TspVehicleModel model = tspVehicleModelRepository.getById(stdModel.getTspVehicleModelId());
            map.put("tspVehicleModel", model);
        }
        return map;
    }

    /**
     * 地图首页实时数据-通过vin查询
     *
     * @param vin
     * @return
     */
    public Map<String, Object> getLatestData(String vin) {
        Map<String, Object> map = new HashMap<>();

        // 读取redis中的车辆数据
        // login
        VehicleLogin vehicleLogin = vehicleRedisCache.readVehicleLoginCache(vin);
        if (vehicleLogin != null) {
            VehicleLoginParsedDTO vehicleLoginParsedDTO = VehicleLoginParsedDTO.create(vehicleLogin);
            map.put("vehicleLogin", vehicleLoginParsedDTO);
        }

        // 整车数据
        VehicleIntegrate vehicleIntegrate = vehicleRedisCache.readVehicleIntegrateCache(vin);
        if (vehicleIntegrate != null) {
            VehicleIntegrateParsedDTO vehicleIntegrateParsedDTO = VehicleIntegrateParsedDTO.create(vehicleIntegrate);
            map.put("vehicleIntegrate", vehicleIntegrateParsedDTO);
        }

        // 驱动电机数据
        List<VehicleDriveMotor> vehicleDriveMotorList = vehicleRedisCache.readVehicleDriveMotorListCache(vin);
        if (vehicleDriveMotorList != null && vehicleDriveMotorList.size() > 0) {
            VehicleDriveMotorParsedDTO vehicleDriveMotorParsedDTO = VehicleDriveMotorParsedDTO.create(vehicleDriveMotorList.get(0));

            map.put("vehicleDriveMotor", vehicleDriveMotorParsedDTO);
        }

        // 车辆位置数据
        VehicleGps vehicleGps = vehicleRedisCache.readVehicleGpsCache(vin);
        if (vehicleGps != null) {
            VehicleGpsParsedDTO vehicleGpsParsedDTO = VehicleGpsParsedDTO.create(vehicleGps);
            map.put("vehicleGps", vehicleGpsParsedDTO);
        }

        // 极值数据
        VehicleExtreme vehicleExtreme = vehicleRedisCache.readVehicleExtremeCache(vin);
        if (vehicleExtreme != null) {
            VehicleExtremeParsedDTO vehicleExtremeDataPageListDTO = VehicleExtremeParsedDTO.create(vehicleExtreme);
            map.put("vehicleExtreme", vehicleExtremeDataPageListDTO);
        }

        // 报警数据
        VehicleAlert vehicleAlert = vehicleRedisCache.readVehicleAlertCache(vin);
        if (vehicleAlert != null) {
            VehicleAlertParsedDTO vehicleAlertParsedDTO = VehicleAlertParsedDTO.create(vehicleAlert);
            map.put("vehicleAlert", vehicleAlertParsedDTO);
        }

        // 可充电储能装置电压数据
        List<VehicleEss> vehicleEssList = vehicleRedisCache.readVehicleEssListCache(vin);
        if (vehicleEssList != null && vehicleEssList.size() > 0) {
            VehicleEssParsedDTO vehicleEssParsedDTO = VehicleEssParsedDTO.create(vehicleEssList.get(0));
            map.put("vehicleEss", vehicleEssParsedDTO);
        }

        // 可充电储能装置温度数据
        List<VehicleEssTemperature> vehicleEssTemperatureList = vehicleRedisCache.readVehicleEssTemperatureListCache(vin);
        if (vehicleEssTemperatureList != null && vehicleEssTemperatureList.size() > 0) {
            VehicleEssTemperatureParsedDTO vehicleEssTemperatureParsedDTO = VehicleEssTemperatureParsedDTO.create(vehicleEssTemperatureList.get(0));
            map.put("vehicleEssTemperature", vehicleEssTemperatureParsedDTO);
        }

        // logout
        VehicleLogout vehicleLogout = vehicleRedisCache.readVehicleLogoutCache(vin);
        if (vehicleLogout != null) {
            VehicleLogoutParsedDTO vehicleLogoutParsedDTO = VehicleLogoutParsedDTO.create(vehicleLogout);
            map.put("vehicleLogout", vehicleLogoutParsedDTO);
        }

        return map;
    }

    /**
     * 当前用户所有车辆最新实时数据
     * @return
     */
    public List<VehicleGpsCountListDTO> getUserVehiclesLatestData() {
        LoginUser loginUser = SecurityUtils.getLoginUser();

        List<TspVehicle> vehicles = xTspVehicleService.getAllVehiclesBySysUserId(loginUser.getUserId());

        // 获取redis中车辆缓存实时数据
        List<Object> vehiclesRealtimeData = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (int i = 0; i < vehicles.size(); i++) {
                connection.hGetAll(("VehicleRealtimeData:" + vehicles.get(i).getVin()).getBytes());
            }
            return null;
        });

        ArrayList<VehicleGpsCountListDTO> dtos = new ArrayList<>();
        LocalDateTime now = now();
        for (int i = 0; i < vehicles.size(); i++) {
            TspVehicle tspVehicle = vehicles.get(i);

            VehicleGpsCountListDTO dto = new VehicleGpsCountListDTO();

            BeanUtils.copyProperties(tspVehicle, dto);

            LocalDateTime collectTime = null;
            if (i < vehiclesRealtimeData.size()) {
                Map<String, Object> realtimeData = (Map<String, Object>) vehiclesRealtimeData.get(i);
                if (realtimeData != null && !realtimeData.isEmpty()) {
                    // 整车数据
                    Map<String, Object> integrateJson = (Map<String, Object>) realtimeData.get("VehicleIntegrate");
                    if (integrateJson != null) {
                        collectTime = convertToLocalDateTime((JSONArray) integrateJson.get("collectTime"));
                        integrateJson.remove("collectTime");
                        List<VehicleIntegrate> vehicleIntegrate = JSONArray.parseArray("[" + integrateJson + "]", VehicleIntegrate.class);
                        if (vehicleIntegrate != null && vehicleIntegrate.size() != 0) {
                            BeanUtils.copyProperties(vehicleIntegrate.get(0), dto);
                        }
                    }

                    // 报警数据
                    Map<String, Object> alertJson = (Map<String, Object>) realtimeData.get("VehicleAlert");
                    if (alertJson != null) {
                        alertJson.remove("collectTime");
                        List<VehicleAlert> vehicleAlert = JSONArray.parseArray("[" + alertJson + "]", VehicleAlert.class);
                        if (vehicleAlert != null && vehicleAlert.size() != 0) {
                            BeanUtils.copyProperties(vehicleAlert.get(0), dto);
                        }
                    }

                    // 驱动电机数据
                    List<Map<String, Object>> driveMotorJson = (List<Map<String, Object>>) realtimeData.get("VehicleDriveMotor");
                    if (driveMotorJson != null && driveMotorJson.size() != 0) {
                        Map<String, Object> driveMotorMap = driveMotorJson.get(0);
                        driveMotorMap.remove("collectTime");
                        List<VehicleDriveMotor> vehicleDriveMotor = JSONArray.parseArray("[" + driveMotorMap + "]", VehicleDriveMotor.class);
                        BeanUtils.copyProperties(vehicleDriveMotor.get(0), dto);
                    }

                    // gps
                    Map<String, Object> gpsJson = (Map<String, Object>) realtimeData.get("VehicleGps");
                    if (gpsJson != null) {
                        gpsJson.remove("collectTime");
                        List<VehicleGps> vehicleGps = JSONArray.parseArray("[" + gpsJson + "]", VehicleGps.class);
                        if (vehicleGps != null && vehicleGps.size() != 0) {
                            VehicleGps gps = vehicleGps.get(0);
                            dto.setLongitude(String.valueOf(((double) gps.getLongitude()) / 1000000));
                            dto.setLatitude(String.valueOf((double) gps.getLatitude() / 1000000));
                            BeanUtils.copyProperties(gps, dto);
                        }
                    }

                    // login
                    Map<String, Object> loginJson = (Map<String, Object>) realtimeData.get("VehicleLogin");
                    if (loginJson != null) {
                        loginJson.remove("collectTime");
                        loginJson.remove("loginTime");
                        List<VehicleLogin> vehicleLogin = JSONArray.parseArray("[" + loginJson + "]", VehicleLogin.class);
                        if (vehicleLogin != null && vehicleLogin.size() != 0) {
                            VehicleLogin login = vehicleLogin.get(0);
                            BeanUtils.copyProperties(login == null ? new VehicleLogin() : login, dto);
                        }
                    }

                    // logout
                    Map<String, Object> logoutJson = (Map<String, Object>) realtimeData.get("VehicleLogout");
                    if (logoutJson != null) {
                        logoutJson.remove("collectTime");
                        logoutJson.remove("logoutTime");
                        List<VehicleLogout> vehicleLogout = JSONArray.parseArray("[" + logoutJson + "]", VehicleLogout.class);
                        if (vehicleLogout != null && vehicleLogout.size() != 0) {
                            VehicleLogout logout = vehicleLogout.get(0);
                            BeanUtils.copyProperties(logout == null ? new VehicleLogout() : logout, dto);
                        }
                    }
                }
            }

            dto.setVehicleStateExt(calcVehicleStateExt(dto, collectTime, now));
            dto.setCollectTime(collectTime);

            dto.setVin(tspVehicle.getVin());
            dtos.add(dto);
        }
        return dtos;
    }

    private static final ZoneId ZONE_ID = ZoneId.of("GMT+8");
    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE_ID);
    }

    public static String calcVehicleStateExt(VehicleGpsCountListDTO dto, LocalDateTime collectTime, LocalDateTime now) {
        if (dto != null && collectTime != null && now != null) {
            Duration duration = Duration.between(collectTime, now);
            if (duration.toMillis() > (1000 * 60)) {
                return "离线";
            } else if (dto.getGeneralAlarmSign() != null && dto.getGeneralAlarmSign() > 0) {
                return "报警";
            } else if (dto.getChargeState() != null && (dto.getChargeState() == 0x01 || dto.getChargeState() == 0x02 || dto.getChargeState() == 0x04)) {
                return "充电";
            } else if (dto.getVehicleState() != null && dto.getSpeed() != null && dto.getVehicleState() == 0x01 && dto.getSpeed() >= 1) {
                return "行驶";
            } else if (dto.getVehicleState() != null && dto.getSpeed() != null && dto.getVehicleState() == 0x01 && dto.getSpeed() < 1) {
                return "停车";
            } else {
                return "在线";
            }
        }
        return "未知";
    }

    public static LocalDateTime convertToLocalDateTime(JSONArray collectTimeJSONArray) {
        try {
            int h = collectTimeJSONArray.size() > 3 ? (int) collectTimeJSONArray.get(3) : 0;
            int m = collectTimeJSONArray.size() > 4 ? (int) collectTimeJSONArray.get(4) : 0;
            int s = collectTimeJSONArray.size() > 5 ? (int) collectTimeJSONArray.get(5) : 0;
            LocalDateTime collectTime = LocalDateTime.of((int) collectTimeJSONArray.get(0), Month.of((int) collectTimeJSONArray.get(1)), (int) collectTimeJSONArray.get(2),
                    h, m, s, 0);

            return collectTime;
        } catch (Exception e) {
            e.printStackTrace();

            return LocalDateTime.MIN;
        }
    }
}
