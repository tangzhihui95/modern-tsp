package com.modern.tsp.abstracts.sendMsg.data.impl;

import com.modern.common.constant.ErrorEnum;
import com.modern.common.utils.LocalDateUtils;
import com.modern.tsp.abstracts.sendMsg.data.base.TspSendMsgBaseService;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TspVehicleAlert;
import com.modern.tsp.repository.TspAlertEventRepository;
import com.modern.tsp.repository.TspVehicleAlertRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/26 17:16
 * 平台发送消息
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspPlatformSendMsgService implements TspSendMsgBaseService {

    private final TspVehicleAlertRepository tspVehicleAlertRepository;
    private final TspVehicleRepository tspVehicleRepository;
    @Override
    public void sendMsg(Long tspAlertEventId, String vin, Integer eventLevel) {
        TspVehicle vehicle = tspVehicleRepository.getByVin(vin);
        if (vehicle == null){
            log.error("平台推送消息失败未找到该车辆错误VIN：{}",vin);
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        }
        TspVehicleAlert alert = new TspVehicleAlert();
        alert.setLevel(eventLevel);
        alert.setTspAlertEventId(tspAlertEventId);
        alert.setVin(vin);
        alert.setEscalationTime(LocalDateUtils.getCurrentDateTime());
        tspVehicleAlertRepository.save(alert);
    }
}
