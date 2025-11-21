package com.modern.tsp.service;

import com.modern.common.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AIOTService {
    private final String CONSUMER = "modeng";
    private final String PASSWORD = "9HI&007a@KhY%*42";
    private final String URL = "https://tep-api.ctwing.cn:8443/api/service";

    /**
     * 流量查询（当月）
     *
     * @param accNbr  ICCID
     * @param needDtl 0:仅总流量；1:表示返回流量使用明细，有时使用明细报文量过大，会影响传输速度
     * @return
     */
    //TODO: @Cacheable
//    Map<String, Object> queryTraffic(String accNbr, String needDtl) {
    String queryTraffic(String accNbr, String needDtl) {
        Map<String, Object> entity = new HashMap<>();

        entity.put("msgId", createMsgId(accNbr));  //自定义流水号（保证每次都不一样）
        entity.put("version", 1.0);
        entity.put("consumer", CONSUMER);
        entity.put("password", PASSWORD);
        entity.put("service_name", "AIOTQueryTraffic");

        Map<String, String> serviceParameter = new HashMap<>();
        serviceParameter.put("accNbr", accNbr);
        serviceParameter.put("needDtl", needDtl);
        entity.put("service_parameter", serviceParameter);

        String result = HttpUtils.toPost(URL, JSONObject.valueToString(entity));
        if (result == null) {
            return null;
        } else {
//            return com.alibaba.fastjson.JSONObject.parseObject(result);
            return result;
        }
    }

    private String createMsgId(String accNbr) {
        return accNbr.substring(accNbr.length() - 6) + System.currentTimeMillis();
    }
}
