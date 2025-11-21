package com.modern.common.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 17:15
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("查询 - 请求对象 - 条件查询")
public class FrontQuery extends BaseVO{

    @ApiModelProperty("是否需要全部")
    private Integer needAll;

    @ApiModelProperty("类型ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentTypeId;

    @ApiModelProperty("型号ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentModelId;
}
