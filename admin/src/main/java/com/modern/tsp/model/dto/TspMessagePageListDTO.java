package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.TspMessageSendChannelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/1 20:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("通知推送 - 数据传输对象 - 分页列表")
public class TspMessagePageListDTO extends BaseDTO {

    @ApiModelProperty(value = "消息ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspMessageId;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 推送渠道
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @ApiModelProperty("推送渠道")
    private TspMessageSendChannelEnum sendChannel;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 发送类型
     */
    @ApiModelProperty("发送类型、1-立即发送、2-定时发送、3-事件发送、4-循环定时发送")
    private Integer sendType;

    /**
     * 状态
     */
    @ApiModelProperty("状态、1-启用、0-禁用")
    private Integer state;
}
