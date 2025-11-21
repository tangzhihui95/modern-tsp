package com.modern.tsp.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 11:14
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("标签管理 - 数据传输对象 - 下拉框信息")
public class TspTagLabelDTO extends BaseDTO {

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String label;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long value;
}
