package com.modern.tsp.model.dto;

import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 14:32
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("设备分类 - 数据传输对象 - 分页列表")
public class TspEquipmentTypePageListDTO extends BaseDTO {

    @ApiModelProperty("设备类型")
    private String name;

    @ApiModelProperty("供应商")
    private String suppliers;

    @ApiModelProperty("是否为终端")
    private Boolean isTerminal;

    @ApiModelProperty("扩展信息类型")
    private String extraType;

    @ApiModelProperty("关联设备")
    private Integer count = 0;

    @ApiModelProperty("型号")
    private List<TspEquipmentModelPageListDTO> children = new ArrayList<>();
}
