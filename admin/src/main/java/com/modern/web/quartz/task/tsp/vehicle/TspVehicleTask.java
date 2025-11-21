package com.modern.web.quartz.task.tsp.vehicle;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.utils.SecurityUtils;
import com.modern.exInterface.entity.VehicleAlert;
import com.modern.exInterface.repository.VehicleAlertRepository;
import com.modern.tsp.abstracts.alertEvent.TspEventDataFactory;
import com.modern.tsp.abstracts.alertEvent.data.base.TspEventDataBaseService;
import com.modern.tsp.abstracts.sendMsg.TspSendMsgFactory;
import com.modern.tsp.abstracts.sendMsg.data.base.TspSendMsgBaseService;
import com.modern.tsp.domain.TspAlertEvent;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TypeJson;
import com.modern.tsp.enums.TspVehicleStateEnum;
import com.modern.tsp.repository.TspAlertEventRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 21:43
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Slf4j
@Component("tspVehicleTask")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleTask {

    private final TspVehicleRepository tspVehicleRepository;
    private final TspAlertEventRepository tspAlertEventRepository;
    @Autowired
    private TspEventDataFactory tspEventDataFactory;

    @Autowired
    private TspSendMsgFactory tspSendMsgFactory;

    private VehicleAlertRepository vehicleAlertRepository;

    public void scrapTask() {
        log.info("TSP - scrapTask: 车辆报废任务开始执行 ");
        List<TspVehicle> vehicles = tspVehicleRepository
                .getNotInStateScrapTimeLeCrtTime(TspVehicleStateEnum.SCRAPPED.getValue(), TspVehicleStateEnum.BOUND.getValue());
        if (!CollectionUtils.isEmpty(vehicles)) {
            vehicles.forEach(item -> {
                item.setUpdateBy(SecurityUtils.getUsername());
                item.setState(TspVehicleStateEnum.SCRAPPED);
                tspVehicleRepository.updateById(item);
            });
        }
        log.info("TSP - scrapTask: 车辆报废任务执行结束 ");
    }


    public void vehicleAlert() {
        log.info("TSP - scrapTask: 车辆告警任务开始执行");
        List<TspAlertEvent> events = tspAlertEventRepository.list();
        for (TspAlertEvent event : events) {
            List<String> alerts = this.isAlerts(event.getMonitorStartTime(), event.getMonitorEndTime(), event.getTypeJson());
            if (CollectionUtils.isNotEmpty(alerts)) {
                for (String vin : alerts) {
                    // TODO 发送报警消息
                    TspSendMsgBaseService baseService = tspSendMsgFactory.setFindType(event.getEventType());
                    baseService.sendMsg(event.getId(),vin,event.getEventLevel());
                    // TODO 生成历史报警记录 暂时这么生成后续做更改
                    VehicleAlert alert = new VehicleAlert();
                    alert.setDataType(0x02);
                    alert.setVin(vin);
                    alert.setDriveMotorFaultCodes(new byte[0]);
                    alert.setEngineTotalFault(0);
                    alert.setEngineFaultCodes(new byte[0]);
                    alert.setDriveMotorTotalFault(0xfe);
                    alert.setMaxAlarmLevel(event.getEventLevel());
                    alert.setGeneralAlarmSign(0x01);
                    alert.setOtherTotalFault(0);
                    alert.setOtherFaultCodes(new byte[0x02]);
                    alert.setEssFaultCodes(new byte[0x02]);
                    alert.setEssTotalFault(0);
                    alert.setCollectTime(LocalDateTime.now());
                    alert.setCreateTime(LocalDateTime.now());
                    alert.setAlertSource(1);
                    alert.setDeleted(0);
                    vehicleAlertRepository.save(alert);
                }

            }

        }
        log.info("TSP - scrapTask: 车辆告警任务执行结束 ");
    }

    @SneakyThrows
    public List<String> isAlerts(Time monitorStartTime, Time monitorEndTime, List<List<TypeJson>> typeJsonList) {
        List<String> list = new ArrayList<>();
        for (List<TypeJson> typeJsons : typeJsonList) {
            TypeJson ruleType = JSON.parseObject(String.valueOf(typeJsons.get(0)),TypeJson.class);
            TypeJson operator = JSON.parseObject(String.valueOf(typeJsons.get(1)),TypeJson.class);
            TypeJson value = JSON.parseObject(String.valueOf(typeJsons.get(2)),TypeJson.class);
            TspEventDataBaseService eventBaseService = tspEventDataFactory.setFindType(ruleType.getValue());
            List<String> vins = eventBaseService.isAlert(monitorStartTime, monitorEndTime, operator.getValue(), value.getValue());
            list.addAll(vins);
        }
        if (CollectionUtils.isNotEmpty(list)){
            return list.stream().collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                    .entrySet().stream()
                    // 过滤出元素出现次数大于等于子条件长度得,则为满足条件得车辆vin
                    .filter(entry -> entry.getValue() >= typeJsonList.size())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
