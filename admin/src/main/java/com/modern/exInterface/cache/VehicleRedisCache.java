package com.modern.exInterface.cache;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.modern.exInterface.entity.*;
import com.modern.exInterface.entity.command.VehicleCommand;
import com.modern.tsp.domain.TspVehicleLicense;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleRedisCache
{
    public static final String VEHICLE_REALTIME_DATA_KEY_PREFIX = "VehicleRealtimeData";
    public static final String TSP_TABLE = "TspTable";

    private final RedisTemplate<String, Object> vehicleRedisTemplate;

    private ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    public VehicleIntegrate readVehicleIntegrateCache(String vin) {
        return readRealtimeDataFromCache(vin, "VehicleIntegrate", VehicleIntegrate.class);
    }

    public VehicleAlert readVehicleAlertCache(String vin) {
        return readRealtimeDataFromCache(vin, "VehicleAlert", VehicleAlert.class);
    }

    public VehicleGps readVehicleGpsCache(String vin) {
        return  readRealtimeDataFromCache(vin, "VehicleGps", VehicleGps.class);
    }

    public VehicleExtreme readVehicleExtremeCache(String vin) {
        return  readRealtimeDataFromCache(vin, "VehicleExtreme", VehicleExtreme.class);
    }

    public VehicleLogin readVehicleLoginCache(String vin) {
        return  readRealtimeDataFromCache(vin, "VehicleLogin", VehicleLogin.class);
    }

    public VehicleLogout readVehicleLogoutCache(String vin) {
        return  readRealtimeDataFromCache(vin, "VehicleLogout", VehicleLogout.class);
    }

    public List<VehicleDriveMotor> readVehicleDriveMotorListCache(String vin) {
        return  readRealtimeDataListFromCache(vin, "VehicleDriveMotor", VehicleDriveMotor.class);
    }

    public List<VehicleEss> readVehicleEssListCache(String vin) {
        return  readRealtimeDataListFromCache(vin, "VehicleEss", VehicleEss.class);
    }

    public List<VehicleEssTemperature> readVehicleEssTemperatureListCache(String vin) {
        return  readRealtimeDataListFromCache(vin, "VehicleEssTemperature", VehicleEssTemperature.class);
    }

    public VehicleCommand readVehicleCommandCache(String vin) {
        return readFromCache("VehicleCommand" + ":" + vin, "VehicleCommand", VehicleCommand.class);
    }

    private <T> T readRealtimeDataFromCache(String key, String hashKey, Class<T> valueType) {
        return readFromCache(VEHICLE_REALTIME_DATA_KEY_PREFIX + ":" + key, hashKey, valueType);
    }

    private <T> T readFromCache(String key, String hashKey, Class<T> valueType) {
        try {
            Object o = vehicleRedisTemplate.opsForHash().get(key, hashKey);
            return objectMapper.readValue((String) o, valueType);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    private <T> List<T> readRealtimeDataListFromCache(String key, String hashKey, Class<T> valueType) {
        return readListFromCache(VEHICLE_REALTIME_DATA_KEY_PREFIX + ":" + key, hashKey, valueType);
    }

    private <T> List<T> readListFromCache(String key, String hashKey, Class<T> valueType) {
        try {
            Object o = vehicleRedisTemplate.opsForHash().get(key, hashKey);
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, valueType);
            return objectMapper.readValue((String) o, type);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    private boolean writeToCache(String key, String hashKey, Object o) {
        try {
            vehicleRedisTemplate.opsForHash().put(key, hashKey, objectMapper.writeValueAsString(o));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public TspVehicleLicense readTspVehicleLicenseByTspVehicleId(Long tspVehicleId) {
        return readFromCache(TSP_TABLE + ":" + "TspVehicleLicense", tspVehicleId + "", TspVehicleLicense.class);
    }

    public boolean writeTspVehicleLicenseByTspVehicleId(TspVehicleLicense tspVehicleLicense) {
        return writeToCache(TSP_TABLE + ":" + "TspVehicleLicense", tspVehicleLicense.getTspVehicleId() + "", tspVehicleLicense);
    }
}
