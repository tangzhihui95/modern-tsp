package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 14:44
 * @Version 1.0.0
 */
@Data
@ApiModel("标签管理 - 请求对象 - 分页列表")
public class TspTagPageListVO extends BaseVO {

    @ApiModelProperty(value = "标签主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspTagId;

    /**
     * 标签名称
     */
    @Column(comment = "标签名称",type = MySqlTypeConstant.VARCHAR,length = 50,isNull = false)
    private String tagName;

    /**
     * 标签类型
     */
    @Column(comment = "标签类型",type = MySqlTypeConstant.INT,length = 11,isNull = false)
    private Integer tagType;
}
