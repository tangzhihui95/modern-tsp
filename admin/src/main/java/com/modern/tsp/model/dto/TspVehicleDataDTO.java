package com.modern.tsp.model.dto;

import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/5 9:18
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆信息 - 传输对象 - 数据统计")
public class TspVehicleDataDTO extends BaseDTO {

    @ApiModelProperty("在线车辆")
    private Integer onLineCount;

    @ApiModelProperty("离线车辆")
    private Integer offLineCount;

    @ApiModelProperty("总车辆")
    private Integer totalCount;

    @ApiModelProperty("报警车辆")
    private Integer alertCount;
}
