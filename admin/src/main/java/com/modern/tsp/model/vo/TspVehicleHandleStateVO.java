package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 15:32
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("历史报警 - 数据传输对象 - 处理")
public class TspVehicleHandleStateVO {

    @ApiModelProperty("告警事件ID")
    @NotNull(message = "告警事件ID不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleAlertId;

    @NotEmpty(message = "处理详情不能为空")
    @ApiModelProperty("处理详情")
    private String remark;
}
