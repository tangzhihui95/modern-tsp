package com.modern.tsp.abstracts.sendMsg.data.impl;

import com.modern.common.constant.ErrorEnum;
import com.modern.common.constant.PlatformConstant;
import com.modern.common.jiguang.domain.JiGuangMsgRecord;
import com.modern.common.jiguang.service.JiGuangMsgRecordService;
import com.modern.tsp.abstracts.sendMsg.data.base.TspSendMsgBaseService;
import com.modern.tsp.domain.TspAlertEvent;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TypeJson;
import com.modern.tsp.repository.TspAlertEventRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/26 17:19
 * APP消息推送
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspAppSendMsgService implements TspSendMsgBaseService {
    private final JiGuangMsgRecordService jiGuangMsgRecordService;
    private final TspVehicleRepository tspVehicleRepository;
    private final TspAlertEventRepository tspAlertEventRepository;

    @Override
    public void sendMsg(Long tspAlertEventId, String vin, Integer eventLevel) {
        TspVehicle vehicle = tspVehicleRepository.getByVin(vin);
        if (vehicle == null) {
            log.error("平台推送消息失败未找到该车辆错误VIN：{}", vin);
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }
        TspAlertEvent alertEvent = tspAlertEventRepository.getById(tspAlertEventId);
        List<List<TypeJson>> typeJson = alertEvent.getTypeJson();
        StringBuilder builder = new StringBuilder();
        for (List<TypeJson> typeJsons : typeJson) {
            builder.append(typeJsons.get(0).getValue());
        }
        JiGuangMsgRecord msg = new JiGuangMsgRecord();
        msg.setTitle("车辆设备异常");
        msg.setContent("您的车辆设备存在异常,异常设备信息为:" + builder + "。请检查设备");
        msg.setBusinessId(tspAlertEventId);
        msg.setPushSource(PlatformConstant.TSP);
        msg.setTargetSource(PlatformConstant.APP);
        msg.setAlias("ALL");
        // TODO 第一次推送消息APP手机端会自动生成SDK
//        msg.setRegistrationId(String.valueOf(vehicle.getTspUserId()));
        msg.setTspUserId(vehicle.getTspUserId());
        // TODO APP推送消息异步方式
        jiGuangMsgRecordService.sendJiGuangMsg(msg);
    }
}
