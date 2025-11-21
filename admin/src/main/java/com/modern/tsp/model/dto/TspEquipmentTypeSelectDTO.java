package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
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
 * @date 2022/6/16 20:33
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 * 符合前端数据结构规范  ID设置为value  选择供应商时ID为null
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("设备分类 - 数据传输对象 - 下拉列表")
public class TspEquipmentTypeSelectDTO extends BaseDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("ID")
    private Long value;

    @ApiModelProperty("类型")
    private String label;

    @ApiModelProperty("设备类型")
    private List<TspEquipmentTypeSelectDTO> children;
}
