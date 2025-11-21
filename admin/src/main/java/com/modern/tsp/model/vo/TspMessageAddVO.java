package com.modern.tsp.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.tsp.enums.TspMessageCycleEnum;
import com.modern.tsp.enums.TspMessageSendChannelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/1 20:44
 */
@Data
@ApiModel("通知推送 - 请求对象 - 添加")
public class TspMessageAddVO {

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
    @ApiModelProperty("推送渠道、1-平台、2-APP、0-未知")
    private TspMessageSendChannelEnum sendChannel;

    /**
     * 发送对象
     */
    @ApiModelProperty("发送对象")
    private List<Long> userLabels;

    /**
     * 发送时间
     */
    @TableField(value = "send_time", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("发送时间")
    private List<String> sendTimes;

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
     * 触发事件类型
     */
    @ApiModelProperty("触发事件类型")
    private Integer eventType;

    /**
     * 触发条件
     */
    @ApiModelProperty("触发条件")
    private Integer triggerCondition;

    /**
     * 状态
     */
    @ApiModelProperty("状态、1-启用、0-禁用")
    private Integer state;

    /**
     * 触发时间
     */
    @ApiModelProperty("触发时间")
    private String time;

    /**
     * 触发周期
     */
    @ApiModelProperty("触发周期")
    private TspMessageCycleEnum cycle;
}
