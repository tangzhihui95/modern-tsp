package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2022/6/14 15:34
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("设备信息 - 数据传输对象 - 模糊搜索下拉列表")
public class TspEquipmentLikeSelectDTO extends BaseDTO {


    @ApiModelProperty("设备型号")
    private String modelName;
}
