package com.modern.exInterface.model.dto;

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
 * @date 2022/9/23 18:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆位置数据 - 数据传输对象 - 模糊搜索下拉列表")
public class VehicleGpsSearchSelectListDTO extends BaseDTO {

    @ApiModelProperty("label")
    private String label;

    @ApiModelProperty("value")
    private String value;

    @ApiModelProperty("type")
    private String type;
}
