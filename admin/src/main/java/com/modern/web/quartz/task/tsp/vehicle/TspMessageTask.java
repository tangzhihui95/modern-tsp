package com.modern.web.quartz.task.tsp.vehicle;

import com.modern.common.constant.Constants;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspMessage;
import com.modern.tsp.domain.TspMessageRecord;
import com.modern.tsp.domain.TspUser;
import com.modern.tsp.repository.TspMessageRecordRepository;
import com.modern.tsp.repository.TspMessageRepository;
import com.modern.tsp.repository.TspUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/7 10:19
 */
@Slf4j
@Component("tspMessageTask")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspMessageTask {


    private final TspMessageRepository tspMessageRepository;
    private final TspMessageRecordRepository tspMessageRecordRepository;
    private final TspUserRepository tspUserRepository;

    /**
     * 发送即时消息
     * TODO 一次性消息推送，只发送一次
     */
    public void message(){
        log.info("TSP - message: 发送即时定时消息任务开始执行");
        // 查询出所有定时即时消息
        List<TspMessage> disposableMessageList = tspMessageRepository.findDisposableMessageList();
        for (TspMessage message : disposableMessageList) {
            this.sendObj(message);
            message.setUpdateBy(SecurityUtils.getUsername());
            message.setIsDelete(Constants.HAS_DELETED);
        }
        log.info("TSP - message: 发送即时定时消息任务执行结束");
    }

    /**
     * 推送消息
     * @param message message
     */
    public void sendObj(TspMessage message){
        List<TspMessageRecord> records = tspMessageRecordRepository.findByTspMessageId(message.getId());
        String userLabels = message.getUserLabels();
        if ("APP".equals(userLabels)){
            this.sendAppAll(records);
        }
    }

    /**
     * APP推送
     * @param records records
     */
    public void sendAppAll(List<TspMessageRecord> records){
        List<TspUser> users = tspUserRepository.list();
        // TODO 看是群发还是单体批量发送
        if (records.size() > 0){
            for (TspMessageRecord record : records) {
                // TODO 推送消息
                record.setUpdateBy(SecurityUtils.getUsername());
                record.setSendState(1);
            }
        }
    }

    /**
     * 发送事件消息
     */
    public void eventMessage(){
        log.info("TSP - eventMessage: 发送事件消息任务开始执行");
        // 查询出所有事件消息
        List<TspMessage> eventMessageList = tspMessageRepository.findEventMessageList();
        for (TspMessage message : eventMessageList) {
            // TODO 业务不完善，待确认
            this.sendObj(message);
        }
        log.info("TSP - eventMessage: 发送事件消息任务执行结束");
    }

    /**
     * 发送固定定时消息
     */
    public void pollMessage(){
        log.info("TSP - pollMessage: 发送固定定时消息任务开始执行");
        // 查询出所有固定定时消息
        List<TspMessage> pollMessageList = tspMessageRepository.findPollMessageList();
        for (TspMessage message : pollMessageList) {
            // TODO 业务不完善，待确认
            this.sendObj(message);
        }
        log.info("TSP - pollMessage: 发送固定定时消息任务执行结束");
    }
}
