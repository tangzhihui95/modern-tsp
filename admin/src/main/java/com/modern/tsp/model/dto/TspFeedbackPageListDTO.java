package com.modern.tsp.model.dto;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 10:25
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("问题反馈 - 传输对象 - 分页列表")
public class TspFeedbackPageListDTO extends BaseDTO {

    @ApiModelProperty(value = "反馈ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspFeedbackId;

    /**
     * 反馈内容
     */
    @ApiModelProperty("反馈内容")
    private String feedbackContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("处理时间")
    private LocalDateTime dealTime;

    /**
     * 反馈时间
     */

    @ApiModelProperty(value = "反馈时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "反馈时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime feedbackTime;


    /**
     * 处理状态
     */
    @ApiModelProperty("处理状态")
    private Integer dealStatus;

    /**
     * 反馈时间
     */
    @ApiModelProperty("反馈时间")
    private LocalDateTime createTime;

    /**
     * 反馈账号
     */
    @ApiModelProperty("反馈账号")
    private String createBy;
}
