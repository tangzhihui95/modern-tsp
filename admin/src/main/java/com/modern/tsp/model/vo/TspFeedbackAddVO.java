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

import java.time.LocalDateTime;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 14:30
 * @Version 1.0.0
 */
@Data
@ApiModel("问题反馈 - 请求对象 - 添加")
public class TspFeedbackAddVO extends BaseVO {

    @ApiModelProperty(value = "问题反馈主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspFeedbackId;

    /**
     * 反馈内容
     */
    @Column(comment = "反馈内容",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private String feedbackContent;

    /**
     * 处理时间
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "处理时间",type = MySqlTypeConstant.DATETIME)
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
     * 处理反馈
     */
    @Column(comment = "处理反馈",type = MySqlTypeConstant.VARCHAR,length = 500)
    private String dealFeedback;


}
