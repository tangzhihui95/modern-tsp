package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.domain.TspVehicleStdModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 17:59
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆车型 - 数据传输对象 - 分页列表")
public class TspVehicleModelPageListDTO extends BaseDTO {

//    /**
//     * 车辆分类ID
//     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private Long tspVehicleTypeId;


//    @ApiModelProperty("车辆分类")
//    private String typeName;

    /**
     * 车辆名称
     */
    @ApiModelProperty("车型")
    private String vehicleModelName;


    @ApiModelProperty("关联车辆")
    private Integer vehicleCount;

    @ApiModelProperty("二级车型")
    private List<TspVehicleStdModePageListDTO> children;
}
