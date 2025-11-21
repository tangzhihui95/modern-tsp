package com.modern.tsp.abstracts.sendMsg.data.base;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/26 17:16
 */
public interface TspSendMsgBaseService {

    public void sendMsg(Long tspAlertEventId, String vin, Integer eventLevel);
}
