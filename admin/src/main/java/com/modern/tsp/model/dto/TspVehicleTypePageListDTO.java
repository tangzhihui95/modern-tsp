package com.modern.tsp.model.dto;

import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 17:11
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆分类 - 数据传输对象 - 分页列表")
public class TspVehicleTypePageListDTO extends BaseDTO {

    @ApiModelProperty("车型分类名称")
    private String typeName;
}
