package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 摩登 - TSP - TSP用户
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("标签管理 - 数据传输对象 - 详情信息")
public class TspModelDTO extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 短信模板标题
     */
    @ApiModelProperty(value = "短信模板标题")
    private String modelTitle;

    /**
     * 短信模板内容
     */
    @ApiModelProperty(value = "短信模板内容")
    private String modelContent;


}
