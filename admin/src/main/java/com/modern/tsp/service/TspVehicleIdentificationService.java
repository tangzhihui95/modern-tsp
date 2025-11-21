package com.modern.tsp.service;

import com.alibaba.fastjson.JSON;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.RandomUtils;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.code.Base64Util;
import com.modern.common.utils.code.ZTSecurityUtil;
import com.modern.common.utils.http.HttpUtils;
import com.modern.tsp.domain.TspVehicleIdentificationReceive;
import com.modern.tsp.model.vo.TspVehicleIdentificationVO;
import com.modern.tsp.repository.TspVehicleIdentificationReceiveRepository;
import com.modern.tsp.domain.*;
import com.modern.tsp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.modern.common.utils.code.ZTRSAECB.encrypt;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/17 10:02
 * @Version 1.0.0
 */
@Service
@Slf4j
@PropertySource(value = {"classpath:application.yml"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleIdentificationService {
    private final TspVehicleRepository tspVehicleRepository;
    private final TspEquipmentRepository tspEquipmentRepository;
    private final TspVehicleMarketRepository tspVehicleMarketRepository;
    private final TspVehicleOtherRepository tspVehicleOtherRepository;
    private final TspVehicleLicenseRepository tspVehicleLicenseRepository;
    private final TspVehicleIdentificationReceiveRepository tspvehicleIdentificationReceiveRepository;
    private final TspVehicleEquipmentRepository tspVehicleEquipmentRepository;
    private final TspVehicleStdModeRepository tspVehicleStdModeRepository;
    private final TspVehicleModelRepository tspVehicleModelRepository;

    private Map<String, Object> Auth = new HashMap<>();

    @Value("${my.telecom-real-name-authentication.http}")
    private String http;

    @Value("${my.telecom-real-name-authentication.platform-id}")
    private String platformID;  // 平台标识

    @Value("${my.telecom-real-name-authentication.sign-nature}")
    private String signNature;  //（平台标识+密码）的SM3值（转大写），密码由实名登记管理平台分配

    @Value("${my.telecom-real-name-authentication.public-key}")
    private String publicKey;  //加密密钥

    private final String getRealNameUrl = "/realname/api/workFlow/notice";
    private final String sendVehicleMessageUrl = "/realname/api/push/vehicleInfo";
    private final String updateVehicleMessageUrl = "/realname/api/push/replaceCardData";


    /**
     * 实名认证接口服务层
     *
     * @param vo
     * @return
     */
    public Map<String, Object> getRealName(TspVehicleIdentificationVO vo) {
        // 前端传入参数皆为空
        if (vo.getVin() == null && vo.getTspVehicleId() == null) {
            ErrorEnum.TSP_PARAM_NULL_ERROR.throwErr();
        }
        TspVehicle tspVehicle = new TspVehicle();
        // 传入的是vin
        if (vo.getVin() != null) {
            tspVehicle = tspVehicleRepository.getByVin(vo.getVin());
        }
        // 传入的是车辆主键
        if (vo.getVin() == null && vo.getTspVehicleId() != null) {
            tspVehicle = tspVehicleRepository.getById(vo.getTspVehicleId());
        }
        // 如果未绑定设备则报出找不到该车辆
        if (tspVehicle == null || tspVehicle.getTspEquipmentId() == null) {
            ErrorEnum.IDENTIFICATION_EQUIPMENT_ERROR.throwErr();
        }
        // 通过车辆外键设备id得到iccid
        TspEquipment tspEquipment = tspEquipmentRepository.getById(tspVehicle.getTspEquipmentId());
        Map<String, Object> totalMap = new HashMap<>();
        // 认证信息
        Map<String, String> Auth = new HashMap<>();
        // 平台标识:PlatformID
        Auth.put("PlatformID", platformID);
        // （平台标识+密码）的SM3值（转大写），密码由实名登记管理平台分配：SignNature
        Auth.put("SignNature", signNature);
        // 请求唯一标识（20位随机字符串）
        String requestId = RandomUtils.getRequestId(20);
        totalMap.put("RequestID", requestId);
        // 道路机动车辆生产企业编码:MDQ1
        totalMap.put("Code", "MDQ1");
        // 车联网卡ICCID信息
        totalMap.put("ICCID", tspEquipment == null ? "" : tspEquipment.getIccId());
        // 出厂时车辆识别码，即VIN码
        totalMap.put("VIN", tspVehicle.getVin());
        totalMap.put("Auth", Auth);
        String json = JSONObject.valueToString(totalMap);
        log.info("getRealName requestUrl={}, stringEntity={}", http + getRealNameUrl, json);
        // 调用实名认证接口
        String resultStr = HttpUtils.toPost(http + getRealNameUrl, json);
        log.info("getRealName responseEntityToString={}", resultStr);
        if (resultStr != null) {
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resultStr);
            Map<String, Object> resultBody = jsonObject;
            return resultBody;
        }
        return null;
    }

    /**
     * 向电信推送车辆信息
     *
     * @param vo
     * @return
     */
    public Map<String, Object> sendVehicleMessage(TspVehicleIdentificationVO vo) {
        // 封装车辆数据
        Map<String, Object> totalMap = new HashMap<>();
        TspVehicle tspVehicle = new TspVehicle();
        // 传入的是车辆主键
        if (vo.getVin() == null && vo.getTspVehicleId() != null) {
            tspVehicle = tspVehicleRepository.getById(vo.getTspVehicleId());
        }
        // 传入的是vin
        if (vo.getVin() != null) {
            tspVehicle = tspVehicleRepository.getByVin(vo.getVin());
        }
        TspVehicleMarket market = tspVehicleMarketRepository.getByTspVehicleId(tspVehicle.getId());
        TspVehicleOther other = tspVehicleOtherRepository.getByTspVehicleId(tspVehicle.getId());
        TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(tspVehicle.getTspVehicleStdModelId());
        TspVehicleModel model = tspVehicleModelRepository.getById(stdModel.getTspVehicleModelId());
        TspVehicleLicense tspVehicleLicense = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicle.getId());
        // 车辆其他信息
        if (other == null) {
            ErrorEnum.IDENTIFICATION_OHTER_ERR.throwErr();
        }
        // 车牌号码
        String licensePlateNumber = "";
        if (tspVehicleLicense != null) {
            licensePlateNumber = tspVehicleLicense.getPlateCode();
        }
        TspEquipment tspEquipment;
        if (vo.getNewTspEquipmentId() == null) {
            if (tspVehicle.getTspEquipmentId() == null) {
                ErrorEnum.TSP_EQUIPMENT_NULL_ERR.throwErr();
            }
            tspEquipment = tspEquipmentRepository.getById(tspVehicle.getTspEquipmentId());
        }
        else {
            tspEquipment = tspEquipmentRepository.getById(vo.getNewTspEquipmentId());
        }
        Auth.put("SignNature", signNature);
        Auth.put("PlatformID", platformID);
        // 请求唯一标识（20位随机字符串）
        String requestId = RandomUtils.getRequestId(20);
        totalMap.put("RequestID", requestId);
        // 道路机动车辆生产企业编码:MDQ1
        totalMap.put("Code", "MDQ1");
        totalMap.put("Auth", Auth);
        // 以下为车辆信息详情，需进行加密处理-----------------------------------------------
        Map<String, Object> EncryptCarInfoMap = new HashMap<>();
        // 道路机动车辆生产企业名称
        String vehicleOrgName = "摩登汽车（盐城）有限公司";
        // vin
        String vin = tspVehicle.getVin();
        // 车辆数据类型
        String vehicleDataType = "2";
        // 车辆类型(1:乘用车及客车，2：货车，3：半挂牵引车，4：半挂车，5：两轮摩托，6：三轮摩托，7：三轮汽车，
        // 8：低速货车，9：专用汽车，10：货车底盘，11：特种车底盘，12：客车底盘，13：业车底盘，14：其他，格式为  14())
        String vehicleType = "1";
        // 车辆产地（1：国产，2：进口）
        String placeOfOriginOfVehicle = "1";
        // 车辆品牌
        String vehicleDepartment = "燕铃牌";
        // 车辆名称
        String vehicleName = "纯电动轿车";
        // 车辆型号(车辆一级型号)
        String vehicleNum = model.getVehicleModelName();
        // 车型通用名称
        String vehicleModel = "MODERN in";
        // 车身颜色
        String bodyColor = tspVehicle.getColor();
        // 燃料种类：1表示汽油，2表示柴油，3表示电，4表示混合油，5表示天然气，6表示液化石油气，7表示甲醇，
        // 8表示乙醇，9表示太阳能，10表示氢，11表示混合动力。多个燃料种类时，以逗号隔开。
        String fuelType = "3";
        String vehicleSalesTime = "";
        String vehicleSalesUpdateTime = "";
        String vehicleLogginAddress = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        if (market != null) {
            // 车辆销售时间
            LocalDateTime createTime = market.getCreateTime();
            vehicleSalesTime = createTime.format(formatter);
            // 车辆销售信息变更时间
            LocalDateTime updateTime = market.getUpdateTime();
            if (updateTime == null) {
                vehicleSalesUpdateTime = createTime.format(formatter);
            } else {
                vehicleSalesUpdateTime = updateTime.format(formatter);
            }
            // 车辆销售渠道地址
            vehicleLogginAddress = market.getSalesUnitAddress();
        }
        // 车辆出厂时间
        LocalDate exFactoryDate = tspVehicle.getExFactoryDate();
        LocalDateTime exFactoryDateTime = LocalDateUtils.toLocalDateTime(exFactoryDate);
        String vehicleDepartureTime = exFactoryDateTime.format(formatter);
        // 车辆销售渠道名称
        String vehicleChannelName = other.getSalesChannel();
        // 车辆销售渠道类型
        String vehicleChannelType = other.getChannelType().toString();
        // 车辆销售渠道员工姓名
        String vehicleStaffName = other.getEmployeeName();
        // 发动机号码
        String EngineNum = other.getEngineNum() == null ? null : tspVehicle.getEngineNum();
        // 电动机序列号
        String MotorNum = other.getMotorNum() == null ? null : tspVehicle.getMotorNum();
        if (EngineNum == null && MotorNum == null) {
            ErrorEnum.IDENTIFICATION_MOTOR_ENGINE_ERR.throwErr();
        }
        // 车辆状态:1表示生产期，2表示测试期，3表示待售，4表示已售，5表示过户，6表示报废，7表示其他。
        String vehicleStatus = other.getVehicleStatus().toString();
        // 新车标识:1 表示是新车辆，2表示非新车辆
        String newVehicleFlag = other.getNewVehicleFlag().toString();
        // 车联网卡详情
        List<Map<String, Object>> CardInfo = new ArrayList<>();
        // 车联网卡号码
        String MSISDN = tspEquipment.getSim();
        // ICCID
        String iccid = tspEquipment.getIccId();
        String ispCode = null;
        // 运营商
        switch (tspEquipment.getOperator()) {
            // 移动
            case 1: ispCode = "2000";
            break;
            // 联通
            case 2: ispCode = "3000";
            break;
            // 电信
            case 3: ispCode = "1000";
            break;
        }
        // 车联网卡类型
        String ispType = "0";
        Map<String,Object> CardInfoMap = new HashMap<>();
        CardInfoMap.put("MSISDN",MSISDN);
        CardInfoMap.put("ICCID",iccid);
        CardInfoMap.put("IspCode",ispCode);
        CardInfoMap.put("IspType",ispType);
        CardInfo.add(CardInfoMap);
        EncryptCarInfoMap.put("VehicleOrgName", vehicleOrgName);
        EncryptCarInfoMap.put("VIN", vin);
        EncryptCarInfoMap.put("VehicleDataType", vehicleDataType);
        EncryptCarInfoMap.put("CardInfo", CardInfo);
        EncryptCarInfoMap.put("VehicleType", vehicleType);
        EncryptCarInfoMap.put("PlaceOfOriginOfVehicle", placeOfOriginOfVehicle);
        EncryptCarInfoMap.put("VehicleDepartment", vehicleDepartment);
        EncryptCarInfoMap.put("VehicleName", vehicleName);
        EncryptCarInfoMap.put("VehicleNum", vehicleNum);
        EncryptCarInfoMap.put("VehicleModel", vehicleModel);
        EncryptCarInfoMap.put("BodyColor", bodyColor);
        EncryptCarInfoMap.put("FuelType", fuelType);
        EncryptCarInfoMap.put("VehicleDepartureTime", vehicleDepartureTime);
        EncryptCarInfoMap.put("VehicleSalesTime", vehicleSalesTime);
        EncryptCarInfoMap.put("VehicleSalesUpdateTime", vehicleSalesUpdateTime);
        EncryptCarInfoMap.put("LicensePlateNumber",licensePlateNumber);
        EncryptCarInfoMap.put("VehicleChannelName", vehicleChannelName);
        EncryptCarInfoMap.put("VehicleChannelType", vehicleChannelType);
        EncryptCarInfoMap.put("VehicleStaffName", vehicleStaffName);
        EncryptCarInfoMap.put("VehicleLogginAddress", vehicleLogginAddress);
        EncryptCarInfoMap.put("VehicleStatus", vehicleStatus);
        EncryptCarInfoMap.put("NewVehicleFlag", newVehicleFlag);
        EncryptCarInfoMap.put("EngineNum", EngineNum);
        EncryptCarInfoMap.put("MotorNum", MotorNum);
        // 对EncryptCarInfoMap进行加密
        String EncryptCarInfo = ZTSecurityUtil.toString(encrypt(JSONObject.valueToString(EncryptCarInfoMap).getBytes(), Base64Util.decode(publicKey.getBytes())));;
        totalMap.put("EncryptCarInfo", EncryptCarInfo);
        String sendJson = JSONObject.valueToString(totalMap);
        log.info("sendVehicleMessage requestUrl={}, stringEntity={}, rawData={}", http + sendVehicleMessageUrl, sendJson, JSONObject.valueToString(EncryptCarInfoMap));
        String resultStr = HttpUtils.toPost(http + sendVehicleMessageUrl, sendJson);
        log.info("sendVehicleMessage responseEntityToString={}", resultStr);
        if (resultStr != null) {
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resultStr);
            Map<String, Object> resultBody = jsonObject;
            if (resultBody == null || !"200".equals(resultBody.get("Code"))) {
                if (resultBody != null && resultBody.get("Message") != null) {
                    throw new ServiceException(ErrorEnum.IDENTIFICATION_SEND_ERR.getMsg() + "：" + resultBody.get("Message"),
                            ErrorEnum.IDENTIFICATION_SEND_ERR.getCode());
                } else {
                    ErrorEnum.IDENTIFICATION_SEND_ERR.throwErr();
                }
            }
            else {
                tspVehicle.setSendStatus(1);
                tspVehicleRepository.updateById(tspVehicle);
                List<TspVehicleEquipment> byEquipmentId = tspVehicleEquipmentRepository.getByEquipmentId(tspVehicle.getId(), tspVehicle.getTspEquipmentId());
                if (byEquipmentId != null && byEquipmentId.size() != 0) {
                    TspVehicleEquipment tspVehicleEquipment = byEquipmentId.get(0);
                    tspVehicleEquipment.setUploadTime(LocalDateTime.now());
                    tspVehicleEquipmentRepository.updateById(tspVehicleEquipment);
                }
            }
            return resultBody;
        }
        return null;
    }

    /**
     * 接收实名认证结果
     * @param receiveMap
     * @return
     */
    public Map<String, Object> receiveRealNameResult(Map<String,Object> receiveMap) {
        // 返回结果
        Map<String,Object> result = new HashMap<>();
        // 响应信息
        String Message;
        // 状态码
        String Code;
        // 请求id
        Object RequestID =  receiveMap.get("RequestID");
        if (RequestID == null || "".equals(RequestID)) {
            Code = "500";
            Message = "请求失败，请求ID为空值";
            return dealReturnMap(result,null,Message,Code);
        }
        Object vin = receiveMap.get("VIN");
        if (vin == null || "".equals(vin)) {
            Code = "510";
            Message = "请求失败，VIN为空值";
            return dealReturnMap(result,RequestID.toString(),Message,Code);
        }
        Object status = receiveMap.get("Status");
        if (status == null || "".equals(status)) {
            Code = "520";
            Message = "请求失败，Status为空值";
            return dealReturnMap(result,RequestID.toString(),Message,Code);
        }
        Map<String, Object> auth = (Map<String, Object>) receiveMap.get("Auth");
        if (auth == null || auth.isEmpty()) {
            Code = "530";
            Message = "请求失败，Auth为空值";
            return dealReturnMap(result,RequestID.toString(),Message,Code);
        }
        String platformID = (String) auth.get("PlatformID");
        String signNature = (String) auth.get("SignNature");
        Object date = receiveMap.get("Date");
        if ((date == null || "".equals(date)) || date.toString().length() != 19) {
            Code = "540";
            Message = "请求失败，Date为空值或格式不符合";
            return dealReturnMap(result,RequestID.toString(),Message,Code);
        }
        boolean success;
        TspVehicleIdentificationReceive identification = tspvehicleIdentificationReceiveRepository.getByVin((String) vin);
        String cardAuthInfo = JSON.toJSONString(receiveMap.get("CardAuthInfo"));
        log.info("receive realName result json=" + JSON.toJSONString(receiveMap));
        if (identification == null) {
            identification = new TspVehicleIdentificationReceive();
            identification.setVin((String) vin);
            identification.setPlatformId(platformID);
            identification.setSignNature(signNature);
            identification.setReceiveTime(date.toString());
            identification.setCode((String) receiveMap.get("Code"));
            identification.setStatus((String) receiveMap.get("Status"));
            identification.setRequestId(RequestID.toString());
            identification.setCardAuthInfo(cardAuthInfo);
            identification.setCreateBy(SecurityUtils.getUsername());
            identification.setUpdateBy(SecurityUtils.getUsername());
            success = tspvehicleIdentificationReceiveRepository.save(identification);
        }
        else {
            identification.setPlatformId(platformID);
            identification.setSignNature(signNature);
            identification.setReceiveTime(date.toString());
            identification.setCode((String) receiveMap.get("Code"));
            identification.setStatus((String) receiveMap.get("Status"));
            identification.setRequestId(RequestID.toString());
            identification.setCardAuthInfo(cardAuthInfo);
            identification.setUpdateBy(SecurityUtils.getUsername());
            success = tspvehicleIdentificationReceiveRepository.updateById(identification);
        }
        if (success == true) {
            Code = "200";
            Message = "操作成功";
        }
        else {
            Code = "550";
            Message = "数据存储发生异常";
        }
        return dealReturnMap(result,RequestID.toString(),Message,Code);
    }

    private Map<String,Object> dealReturnMap(Map<String,Object> resultMap,String RequestID,String Message,String Code) {
        resultMap.put("RequestID",RequestID);
        resultMap.put("Message",Message);
        resultMap.put("Code",Code);
        return resultMap;
    }

    public Map<String, Object> updateVehicleMessage(TspVehicleIdentificationVO vo) {
        // 前端传入参数皆为空
        if (vo.getVin() == null && vo.getTspVehicleId() == null) {
            ErrorEnum.TSP_PARAM_NULL_ERROR.throwErr();
        }
        TspVehicle tspVehicle = new TspVehicle();
        // 传入的是vin
        if (vo.getVin() != null) {
            tspVehicle = tspVehicleRepository.getByVin(vo.getVin());
        }
        // 传入的是车辆主键
        if (vo.getVin() == null && vo.getTspVehicleId() != null) {
            tspVehicle = tspVehicleRepository.getById(vo.getTspVehicleId());
        }
        // 车辆不存在
        if (tspVehicle == null) {
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }
        // 未绑定设备
        if (tspVehicle.getTspEquipmentId() == null) {
            ErrorEnum.IDENTIFICATION_EQUIPMENT_ERROR.throwErr();
        }
        // 通过车辆外键设备id得到设备
        List<TspVehicleEquipment> sendList = tspVehicleEquipmentRepository.getByVehicleIdSend(tspVehicle.getId());
        if (sendList == null || sendList.size() == 0) {
            ErrorEnum.IDENTIFICATION_EQUIPMENT_ERROR.throwErr();
        }
        if (sendList.size() < 2) {
            ErrorEnum.IDENTIFICATION_EQUIPMENT_ONE_ERROR.throwErr();
        }
        TspVehicleEquipment tve = sendList.get(1);
        TspEquipment tspEquipment = tspEquipmentRepository.getById(tve.getTspEquipmentId());
        TspEquipment newTspEquipment = tspEquipmentRepository.getById(tspVehicle.getTspEquipmentId());
        Map<String, Object> totalMap = new HashMap<>();
        // 认证信息
        Map<String, String> Auth = new HashMap<>();
        // 平台标识:PlatformID
        Auth.put("PlatformID", platformID);
        // （平台标识+密码）的SM3值（转大写），密码由实名登记管理平台分配：SignNature
        Auth.put("SignNature", signNature);
        // 请求唯一标识（20位随机字符串）
        String requestId = RandomUtils.getRequestId(20);
        totalMap.put("RequestID", requestId);
        totalMap.put("Auth",Auth);
        // 道路机动车辆生产企业编码
        totalMap.put("Code","MDQ1");
        // vin
        totalMap.put("VIN",tspVehicle.getVin());
        // MSISDN
        totalMap.put("MSISDN",tspEquipment.getSim());
        // ICCID
        totalMap.put("ICCID",tspEquipment.getIccId());
        // MSISDNNEW
        totalMap.put("MSISDNNEW",newTspEquipment.getSim());
        // ICCIDNEW
        totalMap.put("ICCIDNEW",newTspEquipment.getIccId());
        String updateJson = JSONObject.valueToString(totalMap);
        log.info("updateVehicleMessage requestUrl={}, stringEntity={}", http + updateVehicleMessageUrl, updateJson);
        String resultStr = HttpUtils.toPost(http + updateVehicleMessageUrl, updateJson);
        log.info("updateVehicleMessage responseEntityToString={}", resultStr);
        if (resultStr != null) {
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resultStr);
            Map<String, Object> resultBody = jsonObject;
            Object code = resultBody.get("Code");
            if (code == null || "404".equals(code)) {
                ErrorEnum.IDENTIFICATION_NOT_FOUND_ERR.throwErr();
            }
            if ("300".equals(code)) {
                ErrorEnum.IDENTIFICATION_DIANXIN_ERR.throwErr();
            }
            if ("230".equals(code)) {
                ErrorEnum.IDENTIFICATION_USERNAME_ERR.throwErr();
            }
            if ("130".equals(code)) {
                ErrorEnum.IDENTIFICATION_CHECK_ERR.throwErr();
            }
            return resultBody;
        }
        return null;
    }
}
