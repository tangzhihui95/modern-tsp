package com.modern.tsp.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2022/10/17 10:39
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("实名认证 - 请求对象 - 实名认证数据传输对象")
public class TspVehicleIdentificationDataDTO {

    @ApiModelProperty("认证信息")
    private Map<String,String> Auth = new HashMap<>();

    @ApiModelProperty("请求唯一标识")
    private String RequestID;

    @ApiModelProperty("道路机动车辆生产企业编码")
    private String Code;

    @ApiModelProperty("VIN")
    private String VIN;

    @ApiModelProperty("车联网卡ICCID信息")
    private String ICCID;

}
