package com.modern.exInterface.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.modern.common.config.RedisConfig;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.utils.LocalDateUtils;
import com.modern.exInterface.entity.*;
import com.modern.exInterface.mapper.VehicleGpsMapper;
import com.modern.exInterface.model.dto.VehicleGpsInfoDTO;
import com.modern.exInterface.model.dto.VehicleGpsParsedDTO;
import com.modern.exInterface.model.dto.VehicleGpsSearchSelectListDTO;
import com.modern.exInterface.model.dto.VehicleGpsSelectListDTO;
import com.modern.exInterface.model.vo.VehicleGpsHistoryVO;
import com.modern.exInterface.repository.VehicleGpsRepository;
import com.modern.tsp.domain.*;
import com.modern.tsp.mapper.TspEquipmentMapper;
import com.modern.tsp.mapper.TspVehicleMapper;
import com.modern.tsp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.modern.exInterface.service.VehicleDataService.convertToLocalDateTime;

/**
 * <p>
 * vehicle_gps;车辆位置数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleGpsService {
    private final TspVehicleRepository tspVehicleRepository;
    private final TspUserRepository tspUserRepository;
    private final TspVehicleModelRepository tspVehicleModelRepository;
    private final VehicleGpsRepository vehicleGpsRepository;
    private final VehicleGpsMapper vehicleGpsMapper;
    private final TspVehicleLicenseRepository tspVehicleLicenseRepository;
    private final TspVehicleStdModeRepository tspVehicleStdModeRepository;
    private final TspVehicleMapper tspVehicleMapper;
    private final RedisConnectionFactory redisConnectionFactory;
    RedisConfig redisConfig = new RedisConfig();
    private final TspEquipmentMapper tspEquipmentMapper;

    public VehicleGpsInfoDTO getGpsInfo(String vin) {
        VehicleGpsInfoDTO dto = new VehicleGpsInfoDTO();
        TspVehicle vehicle = tspVehicleRepository.getByVin(vin);
        if (vehicle == null) {
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }
        TspEquipment equipment = tspEquipmentMapper.selectById(vehicle.getTspEquipmentId());
        if (equipment != null) {
            dto.setTspEquipment(equipment);
            dto.setImei(equipment.getImei());
        }
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(vehicle.getId());

        TspUser tspUser = tspUserRepository.getById(vehicle.getTspUserId());
        TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(vehicle.getTspVehicleStdModelId());
        TspVehicleModel model = tspVehicleModelRepository.getById(stdModel.getTspVehicleModelId());
        BeanUtils.copyProperties(license == null ? new TspVehicleLicense() : license, dto);
        BeanUtils.copyProperties(tspUser == null ? new TspUser() : tspUser, dto);
        BeanUtils.copyProperties(model == null ? new TspVehicleModel() : model, dto);
        BeanUtils.copyProperties(vehicle, dto);
        // 读取redis中的gps数据
        RedisTemplate<Object, Object> redisTemplate = redisConfig.redisTemplate(redisConnectionFactory);
        HashOperations<Object, Object, Object> hashOps = redisTemplate.opsForHash();
        List<Object> values = hashOps.values("VehicleRealtimeData:" + vin);
        //如果redis里面没有数据则到mysql读取数据
        // 读取redis中的所有数据
        if (values != null) {
            LocalDateTime collectTime = null;
            // 整车数据
            Map<String, Object> integrateJson = (Map<String, Object>) readListFromCache("VehicleRealtimeData:" + vin, "VehicleIntegrate");
            if (integrateJson != null) {
                collectTime = convertToLocalDateTime((JSONArray) integrateJson.get("collectTime"));
                integrateJson.remove("collectTime");
                List<VehicleIntegrate> vehicleIntegrate = JSONArray.parseArray("[" + integrateJson + "]", VehicleIntegrate.class);
                if (vehicleIntegrate != null && vehicleIntegrate.size() != 0) {
                    BeanUtils.copyProperties(vehicleIntegrate.get(0), dto);
                    vehicleIntegrate.get(0).setCollectTime(collectTime);
                    dto.setChargeStatus(vehicleIntegrate.get(0).getChargeState() == 3 ? true : false);
                }
            }
            // 报警数据
            Map<String, Object> alertJson = (Map<String, Object>) readListFromCache("VehicleRealtimeData:" + vin, "VehicleAlert");
            if (alertJson != null) {
                alertJson.remove("collectTime");
                List<VehicleAlert> vehicleAlert = JSONArray.parseArray("[" + alertJson + "]", VehicleAlert.class);
                if (vehicleAlert != null && vehicleAlert.size() != 0) {
                    BeanUtils.copyProperties(vehicleAlert.get(0), dto);
                    dto.setAlertStatus(vehicleAlert.get(0).getGeneralAlarmSign() == 0 ? false : true);
                }
            }
            // 驱动电机数据
            List<Map<String, Object>> driveMotorJson = (List<Map<String, Object>>) readListFromCache("VehicleRealtimeData:" + vin, "VehicleDriveMotor");
            if (driveMotorJson != null && driveMotorJson.size() != 0) {
                Map<String, Object> driveMotorMap = driveMotorJson.get(0);
                driveMotorMap.remove("collectTime");
                List<VehicleDriveMotor> vehicleDriveMotor = JSONArray.parseArray("[" + driveMotorMap + "]", VehicleDriveMotor.class);
                BeanUtils.copyProperties(vehicleDriveMotor.get(0), dto);
            }
            // gps
            Map<String, Object> gpsJson = (Map<String, Object>) readListFromCache("VehicleRealtimeData:" + vin, "VehicleGps");
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
            Map<String, Object> loginJson = (Map<String, Object>) readListFromCache("VehicleRealtimeData:" + vin, "VehicleLogin");
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
            Map<String, Object> logoutJson = (Map<String, Object>) readListFromCache("VehicleRealtimeData:" + vin, "VehicleLogout");
            if (logoutJson != null) {
                logoutJson.remove("collectTime");
                logoutJson.remove("logoutTime");
                List<VehicleLogout> vehicleLogout = JSONArray.parseArray("[" + logoutJson + "]", VehicleLogout.class);
                if (vehicleLogout != null && vehicleLogout.size() != 0) {
                    VehicleLogout logout = vehicleLogout.get(0);
                    BeanUtils.copyProperties(logout == null ? new VehicleLogout() : logout, dto);
                }
            }
            dto.setCollectTime(collectTime);
        } else {
            ErrorEnum.TSP_VEHICLE_NOT_FOUND.throwErr();
        }
//        else {
//            VehicleGps vehicleGps = vehicleGpsMapper.getGpsByCollerctTime(vin);
//            VehicleDriveMotor driveMotor = vehicleDriveMotorRepository.getByVin(vehicle.getVin());
//            VehicleIntegrate integrate = vehicleIntegrateRepository.getByVin(vehicle.getVin());
//            VehicleAlert vehicleAlert = vehicleAlertRepository.getByVin(vehicle.getVin());
//            BeanUtils.copyProperties(vehicleGps == null ? new VehicleGps() : vehicleGps, dto);
//            BeanUtils.copyProperties(driveMotor == null ? new VehicleDriveMotor() : driveMotor, dto);
//            dto.setSoc(integrate.getSoc());
//            dto.setChargeStatus(integrate.getChargeState() == 3 ? true : false);
//            dto.setAlertStatus(vehicleAlert.getGeneralAlarmSign() == 0 ? false : true);
//            dto.setLongitude(String.valueOf(Double.parseDouble(dto.getLongitude()) * 0.000001));
//            dto.setLatitude(String.valueOf(Double.parseDouble(dto.getLatitude()) * 0.000001));
//        }
        dto.setVin(vehicle.getVin());
        dto.setOnlineStatus(vehicle.getTspEquipmentId() == null ? false : true);
        return dto;
    }

    public List<VehicleGpsSearchSelectListDTO> selectList(String search) {
        Wrapper<TspVehicle> snEw = tspVehicleRepository.selectListSnEw(search);
        Wrapper<TspVehicle> vinEw = tspVehicleRepository.selectListVinEw(search);
        Wrapper<TspVehicle> plateCodeEw = tspVehicleRepository.selectListPlateCodeEw(search);
        List<VehicleGpsSelectListDTO> snList = tspVehicleMapper.findSelectList(snEw);
        List<VehicleGpsSelectListDTO> vinList = tspVehicleMapper.findSelectList(vinEw);
        List<VehicleGpsSelectListDTO> plateCodeList = tspVehicleMapper.findSelectList(plateCodeEw);
        ArrayList<VehicleGpsSearchSelectListDTO> dtos = new ArrayList<>();
        for (VehicleGpsSelectListDTO dto : plateCodeList) {
            VehicleGpsSearchSelectListDTO selectListDTO = new VehicleGpsSearchSelectListDTO();
            selectListDTO.setLabel(dto.getPlateCode());
            selectListDTO.setValue(dto.getVin());
            selectListDTO.setType("车牌号:");
            dtos.add(selectListDTO);
        }
        for (VehicleGpsSelectListDTO dto : vinList) {
            VehicleGpsSearchSelectListDTO selectListDTO = new VehicleGpsSearchSelectListDTO();
            selectListDTO.setLabel(dto.getVin());
            selectListDTO.setValue(dto.getVin());
            selectListDTO.setType("VIN:");
            dtos.add(selectListDTO);
        }
        for (VehicleGpsSelectListDTO dto : snList) {
            VehicleGpsSearchSelectListDTO selectListDTO = new VehicleGpsSearchSelectListDTO();
            selectListDTO.setLabel(dto.getSn());
            selectListDTO.setValue(dto.getVin());
            selectListDTO.setType("SN:");
            dtos.add(selectListDTO);
        }
        List<VehicleGpsSearchSelectListDTO> unique = dtos.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(VehicleGpsSearchSelectListDTO::getValue))), ArrayList::new));
        return unique;
    }

    public List<VehicleGpsParsedDTO> findHistory(VehicleGpsHistoryVO vo) {
        //TODO:最多查询8小时历史轨迹数据
        final int MAX_INTERNAL_HOUR = 8;
        if (vo.getCollectStartTime() == null || vo.getCollectEndTime() == null) {
            LocalDateTime now = LocalDateUtils.getCurrentTime();
            vo.setCollectEndTime(now);
            vo.setCollectStartTime(now.minusHours(MAX_INTERNAL_HOUR));
        } else {
            Duration duration = Duration.between(vo.getCollectStartTime(), vo.getCollectEndTime());
            if (duration.toHours() > MAX_INTERNAL_HOUR) {
                throw new IllegalArgumentException("时间间隔必须小于" + MAX_INTERNAL_HOUR + "小时");
            }
        }

        Wrapper<VehicleGps> ew = vehicleGpsRepository.findHistoryEw(vo);
        List<VehicleGps> history = vehicleGpsMapper.findHistory(ew);
        ArrayList<VehicleGpsParsedDTO> dtos = new ArrayList<>();
        for (VehicleGps vehicleGps : history) {
            dtos.add(VehicleGpsParsedDTO.create(vehicleGps));
        }
        if (dtos.size() == 0) {
            throw new IllegalArgumentException("未找到该车辆的运行数据");
        }
        return dtos;
    }

    private <T> Object readListFromCache(String key, String hashKey) {
        try {
            Object o = redisConfig.redisTemplate(redisConnectionFactory).opsForHash().get(key, hashKey);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
