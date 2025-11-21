package com.modern.tsp.model.dto;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
@ApiModel("问题反馈 - 传输对象 - 处理反馈")
public class TspFeedbackInfoDTO extends BaseDTO {

    /**
     * 反馈账号
     */
    @ApiModelProperty("反馈账号")
    private String createBy;

    /**
     * 反馈内容
     */
    @ApiModelProperty("反馈内容")
    private String feedbackContent;

    /**
     * 处理状态
     */
    @ApiModelProperty("处理状态")
    private Integer dealStatus;

    /**
     * 处理反馈
     */
    @ApiModelProperty("处理反馈")
    private String dealFeedback;

//    /**
//     * 反馈时间
//     */
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
//    @Column(comment = "反馈时间",type = MySqlTypeConstant.DATETIME)
//    private LocalDateTime createTime;
}
