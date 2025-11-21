package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 18:16
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@Data
@ApiModel("车辆车型 - 请求对象 - 添加")
public class TspVehicleModelAddVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleModelId;

//    @NotNull(message = "车辆分类ID不能为空")
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private Long tspVehicleTypeId;

    @NotEmpty(message = "车型名称不能为空")
    @ApiModelProperty("车型名称")
    private String vehicleModelName;


}
