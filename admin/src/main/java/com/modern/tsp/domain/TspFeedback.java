package com.modern.tsp.domain;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 10:20
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_feedback",comment = "摩登 - TSP - 问题反馈")
@Alias("TspFeedback")
@TableName(value = "tsp_feedback", autoResultMap = true)
@Data
public class TspFeedback extends BaseModel {

    /**
     * 反馈内容
     */
    @Column(comment = "反馈内容",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private String feedbackContent;

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
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "处理时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime dealTime;

    /**
     * 处理状态
     */
    @Column(comment = "处理状态",type = MySqlTypeConstant.INT,length = 11)
    private Integer dealStatus;

    /**
     * 处理反馈
     */
    @Column(comment = "处理反馈",type = MySqlTypeConstant.VARCHAR,length = 500)
    private String dealFeedback;

}
