package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 17:24
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆分类 - 请求对象 - 添加")
public class TspVehicleTypeAddVO {

    @ApiModelProperty("车辆分类ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleTypeId;

    @NotEmpty(message = "车辆分类名称不能为空")
    @ApiModelProperty("车辆分类名称")
    private String typeName;
}
