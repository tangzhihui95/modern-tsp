package com.modern.xtsp.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.modern.common.core.redis.RedisCache;
import com.modern.common.utils.http.HttpUtils;
import com.modern.xtsp.domain.vo.ZhiYuanVehicleTboxSettingsVO;
import com.modern.xtsp.util.DataUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class XZhiYuanTspServiceImpl {

    @Resource
    RedisCache redisCache;

    @Value("${my.command-server-http-address}")
    private String commandServerHttpAddress;

    @Value("${my.send-command-password}")
    private String SEND_COMMAND_PASSWORD;

    Map<String, Short> commandSequenceNumberMap = new HashMap<>();

    @Data
    public static class TspVehicleCommandDTO {
        private Long id;

        private String vin;
        private Integer dataType;  //0x80,0x81,0x82
        private Integer ackType;

        private String data;

        private String password;
    }

    @Data
    public static class TspVehicleControlCommandVO {
        private String vin;
        private String commandId;
        private String command;
    }

    public String sendQueryTboxSettingsCommand(String vin) {
        TspVehicleCommandDTO tspVehicleCommandDTO = new TspVehicleCommandDTO();
        tspVehicleCommandDTO.setVin(vin);
        tspVehicleCommandDTO.setDataType(0x80);
        tspVehicleCommandDTO.setAckType(0xFE);

        byte[] nowBytes = DataUtil.nowBytes();
        String data = HexUtil.encodeHexStr(nowBytes);  //参数查询时间（16进制字符串）
        data += "10";  //参数总数（16进制字符串）
        data += "0102030405060708090A0B0C0D0E0F10";  //参数ID（16进制字符串）
        tspVehicleCommandDTO.setData(data);
        tspVehicleCommandDTO.setPassword(SEND_COMMAND_PASSWORD);

        return doSendCommand(tspVehicleCommandDTO);
    }

    public JSONObject getQueryTboxSettingsResult(String vin) {
        JSONObject jsonObject = redisCache.getCacheMapValue("VehicleCommand" + ":" + vin, "Command");
        if (jsonObject.get("dataType") != null && jsonObject.get("dataType").equals(0x80) && jsonObject.get("ackType") == null) {
            return jsonObject;
        } else {
            return null;
        }
    }

    public String sendUpdateTboxSettingsCommand(ZhiYuanVehicleTboxSettingsVO s) {
        if (s == null) {
            throw new RuntimeException("发送命令失败：错误的参数值！");
        }

        int count = 0;
        List<Byte> dataList = new LinkedList<>();
        for (byte b : DataUtil.nowBytes()) {
            dataList.add(b);
        }
        dataList.add((byte) 0);  //参数总数

        if (s.getStoreCycle() != null) {
            dataList.add((byte) 0x01);
            byte[] bytes = ByteUtil.shortToBytes(s.getStoreCycle().shortValue(), ByteOrder.BIG_ENDIAN);
            for (byte b : bytes) {
                dataList.add(b);
            }
            count++;
        }
        if (s.getSendInterval() != null) {
            dataList.add((byte) 0x02);
            byte[] bytes = ByteUtil.shortToBytes(s.getSendInterval().shortValue(), ByteOrder.BIG_ENDIAN);
            for (byte b : bytes) {
                dataList.add(b);
            }
            count++;
        }
        if (s.getSendIntervalDuringAlarm() != null) {
            dataList.add((byte) 0x03);
            byte[] bytes = ByteUtil.shortToBytes(s.getSendIntervalDuringAlarm().shortValue(), ByteOrder.BIG_ENDIAN);
            for (byte b : bytes) {
                dataList.add(b);
            }
            count++;
        }
        if (s.getServerPlatformDomainName() != null) {
            dataList.add((byte) 0x04);
            dataList.add((byte) s.getServerPlatformDomainName().length());

            dataList.add((byte) 0x05);
            for (byte b : s.getServerPlatformDomainName().getBytes()) {
                dataList.add(b);
            }
            count += 2;
        }
        if (s.getServerPlatformPort() != null) {
            dataList.add((byte) 0x06);
            byte[] bytes = ByteUtil.shortToBytes(s.getServerPlatformPort().shortValue(), ByteOrder.BIG_ENDIAN);
            for (byte b : bytes) {
                dataList.add(b);
            }
            count++;
        }
        if (s.getHardwareVersion() != null) {
            dataList.add((byte) 0x07);
            for (byte b : s.getHardwareVersion().getBytes()) {
                dataList.add(b);
            }
            count++;
        }
        if (s.getFirmwareVersion() != null) {
            dataList.add((byte) 0x08);
            for (byte b : s.getFirmwareVersion().getBytes()) {
                dataList.add(b);
            }
            count++;
        }
        if (s.getHeartbeatInterval() != null) {
            dataList.add((byte) 0x09);
            byte b = s.getHeartbeatInterval().byteValue();
            dataList.add(b);
            count++;
        }
        if (s.getResponseTimeout() != null) {
            dataList.add((byte) 0x0A);
            byte[] bytes = ByteUtil.shortToBytes(s.getResponseTimeout().shortValue(), ByteOrder.BIG_ENDIAN);
            for (byte b : bytes) {
                dataList.add(b);
            }
            count++;
        }
        if (s.getServerResponseTimeout() != null) {
            dataList.add((byte) 0x0B);
            byte[] bytes = ByteUtil.shortToBytes(s.getServerResponseTimeout().shortValue(), ByteOrder.BIG_ENDIAN);
            for (byte b : bytes) {
                dataList.add(b);
            }
            count++;
        }
        if (s.getLoginIntervalAfterFailure() != null) {
            dataList.add((byte) 0x0C);
            byte b = s.getLoginIntervalAfterFailure().byteValue();
            dataList.add(b);
            count++;
        }
        if (s.getNationalPlatformDomainName() != null) {
            dataList.add((byte) 0x0D);
            dataList.add((byte) s.getNationalPlatformDomainName().length());

            dataList.add((byte) 0x0E);
            for (byte b : s.getNationalPlatformDomainName().getBytes()) {
                dataList.add(b);
            }
            count += 2;
        }
        if (s.getNationalPlatformPort() != null) {
            dataList.add((byte) 0x0F);
            byte[] bytes = ByteUtil.shortToBytes(s.getNationalPlatformPort().shortValue(), ByteOrder.BIG_ENDIAN);
            for (byte b : bytes) {
                dataList.add(b);
            }
            count++;
        }
        if (s.getWhetherSamplingMonitoring() != null) {
            dataList.add((byte) 0x10);
            byte b = s.getWhetherSamplingMonitoring().byteValue();
            dataList.add(b);
            count++;
        }

        dataList.set(6, (byte) count);  //更新参数总数

        if (count > 0) {
            byte[] data = DataUtil.toPrimitive(dataList);
            TspVehicleCommandDTO tspVehicleCommandDTO = new TspVehicleCommandDTO();
            tspVehicleCommandDTO.setVin(s.getVin());
            tspVehicleCommandDTO.setDataType(0x81);
            tspVehicleCommandDTO.setAckType(0xFE);

            String dataString =HexUtil.encodeHexStr(data);  //参数查询时间（16进制字符串）
            tspVehicleCommandDTO.setData(dataString);
            tspVehicleCommandDTO.setPassword(s.getPassword());

            return doSendCommand(tspVehicleCommandDTO);
        } else {
            throw new RuntimeException("发送命令失败：空的参数值！");
        }
    }

    public JSONObject getUpdateTboxSettingsResult(String vin) {
        JSONObject jsonObject = redisCache.getCacheMapValue("VehicleCommand" + ":" + vin, "Command");
        if (jsonObject.get("dataType") != null && jsonObject.get("dataType").equals(0x81)
                && jsonObject.get("ackType") != null && !jsonObject.get("ackType").equals(0xFE)) {
            return jsonObject;
        } else {
            return null;
        }
    }

    //下发车控指令
    public String sendVehicleControlCommand(TspVehicleControlCommandVO vo) {
        String commandId = vo.getCommandId();
        if (ObjectUtil.isNotEmpty(commandId) &&
                (commandId.equals("01") || commandId.equals("02")
                        || commandId.equals("03") || commandId.equals("04"))) {

            TspVehicleCommandDTO sendCommandDTO = new TspVehicleCommandDTO();

            sendCommandDTO.setVin(vo.getVin());
            sendCommandDTO.setDataType(0x92);
            sendCommandDTO.setAckType(0xFE);
//        Long id = new Snowflake().nextId();
//        tspVehicleCommandDTO.setId(id);

            byte[] nowBytes = DataUtil.nowBytes();
            String data = HexUtil.encodeHexStr(nowBytes);  //参数查询时间（16进制字符串）
            data += vo.getCommandId();  //命令ID（16进制字符串）
            data += getNextSequenceNumberHexString(vo.getVin());  //流水号（16进制字符串）
            data += vo.getCommand();  //命令数据（16进制字符串）
            sendCommandDTO.setData(data);
            sendCommandDTO.setPassword(SEND_COMMAND_PASSWORD);

            return doSendCommand(sendCommandDTO);
        } else {
            return "错误的命令！";
        }
    }

    public Object getVehicleControlCommandExecuteResult(String vin) {
        JSONObject jsonObject = redisCache.getCacheMapValue("VehicleCommand" + ":" + vin, "Command");
        if (jsonObject.get("dataType") != null && jsonObject.get("dataType").equals(0x12)
                && jsonObject.get("ackType") != null && jsonObject.get("ackType").equals(0xFE)) {
            byte[] data = Base64.getDecoder().decode(jsonObject.get("data").toString());
            return data[9];
        } else {
            return null;
        }
    }

    private String doSendCommand(TspVehicleCommandDTO tspVehicleCommandDTO) {
        HttpResponse response = HttpUtils.doPost(commandServerHttpAddress + "/zhiyuan/command/send", JSON.toJSONString(tspVehicleCommandDTO));

        try {
            String res = EntityUtils.toString(response.getEntity());

            if (ObjectUtil.isNotEmpty(res) && res.equals("success")) {
                return "success";
            } else {
                throw new RuntimeException(res != null ? res : "发送命令失败：未知错误！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("发送命令失败：服务错误！");
    }

    private String getNextSequenceNumberHexString(String vin) {
        Short sequenceNumber = commandSequenceNumberMap.get(vin);
        if (sequenceNumber == null) {
            sequenceNumber = 1;
        } else {
            sequenceNumber++;
        }
        commandSequenceNumberMap.put(vin, sequenceNumber);
        return HexUtil.encodeHexStr(ByteUtil.shortToBytes(sequenceNumber, ByteOrder.BIG_ENDIAN));
    }
}
