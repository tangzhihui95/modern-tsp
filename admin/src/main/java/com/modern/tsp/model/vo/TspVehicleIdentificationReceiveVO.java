package com.modern.tsp.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2022/10/31 10:39
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("实名认证 - 请求对象 - 接收实名认证结果")
public class TspVehicleIdentificationReceiveVO {

    /**
     * 认证信息
     */
    private Map<String,Object> auth;

    /**
     * 请求标识
     */
    private String requestID;

    /**
     * 道路机动车辆生产企业编码
     */
    private String code;

    /**
     * 车辆识别代号或车架号
     */
    private String vin;

    /**
     * 实名状态
     */
    private String status;

    /**
     * 车联网卡认证详情
     */
    private List<Map<String,Object>> CardAuthInfo;

    /**
     * 同步时间
     */
    private String date;
}
