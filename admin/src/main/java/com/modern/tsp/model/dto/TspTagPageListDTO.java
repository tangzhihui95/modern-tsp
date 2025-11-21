package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @Date 2022/10/15 11:15
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("标签管理 - 数据传输对象 - 分页列表")
public class TspTagPageListDTO extends BaseDTO {

    @ApiModelProperty(value = "标签主键id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspTagId;

    /**
     * 标签名称
     */
    @Column(comment = "标签名称",type = MySqlTypeConstant.VARCHAR,length = 36)
    private String tagName;

    /**
     * 标签类型
     */
    @Column(comment = "标签类型",type = MySqlTypeConstant.INT,length = 11,isNull = false)
    private Integer tagType;

    /**
     * 关联数量
     */
    @Column(comment = "关联数量",type = MySqlTypeConstant.INT,length = 11,isNull = false)
    private Integer tagCount;
}
