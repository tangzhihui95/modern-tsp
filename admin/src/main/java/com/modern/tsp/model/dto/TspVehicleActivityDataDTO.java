package com.modern.tsp.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/8/16 17:42
 */
@Data
@ApiModel("统计分析 - 数据传输对象 - 车辆告警统计")
public class TspVehicleActivityDataDTO {

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("活跃度")
    private Double count;

    @ApiModelProperty("活跃数")
    private Integer activityNum;
}
