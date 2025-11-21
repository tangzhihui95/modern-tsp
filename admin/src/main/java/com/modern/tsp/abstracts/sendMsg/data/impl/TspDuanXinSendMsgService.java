package com.modern.tsp.abstracts.sendMsg.data.impl;

import com.modern.tsp.abstracts.sendMsg.data.base.TspSendMsgBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/26 17:19
 * 短信消息推送
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspDuanXinSendMsgService implements TspSendMsgBaseService {
    @Override
    public void sendMsg(Long tspAlertEventId, String vin, Integer eventLevel) {
        // TODO 短信推送消息异步方式
    }
}
