package com.modern.tsp.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


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
@ApiModel("实名认证 - 请求对象 - 实名认证前端请求参数")
public class TspVehicleIdentificationVO {

    @ApiModelProperty("vin")
    private String vin;

    @ApiModelProperty("tspVehicleId:车辆主键")
    private Long tspVehicleId;

    @ApiModelProperty("newTspEquipmentId:新设备主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long newTspEquipmentId;

}
