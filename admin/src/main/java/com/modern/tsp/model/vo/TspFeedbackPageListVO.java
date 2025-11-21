package com.modern.tsp.model.vo;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 14:34
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("问题反馈 - 请求对象 - 分页列表")
public class TspFeedbackPageListVO extends BaseVO {

    /**
     * 反馈内容
     */
    @Column(comment = "反馈内容",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private String feedbackContent;

    /**
     * 处理状态
     */
    @Column(comment = "处理状态",type = MySqlTypeConstant.INT,length = 11)
    private Integer dealStatus;

    /**
     * 反馈时间
     */
    @ApiModelProperty(value = "反馈时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "反馈时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime feedbackTime;

}
