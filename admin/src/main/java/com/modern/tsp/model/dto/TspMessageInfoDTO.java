package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.TspMessageCycleEnum;
import com.modern.tsp.enums.TspMessageSendChannelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/7 20:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("通知推送 - 传输对象 - 详情信息")
public class TspMessageInfoDTO extends BaseDTO {

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 推送渠道
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @ApiModelProperty(value = "推送渠道")
    private TspMessageSendChannelEnum sendChannel;

    /**
     * 发送对象
     */
    @TableField(value = "user_labels", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty(value = "发送对象")
    @JsonSerialize(contentUsing = ToStringSerializer.class)
    private List<Long> userLabels;

    /**
     * 发送时间
     */
    @TableField(value = "send_time", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty(value = "发送时间")
    private List<String> sendTimes = new ArrayList<>();

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 发送类型
     */
    @ApiModelProperty(value = "发送类型、1-立即发送、2-定时发送、3-事件发送、4-循环定时发送")
    private Integer sendType;


    /**
     * 触发事件类型
     */
    @ApiModelProperty(value = "触发事件类型")
    private Integer eventType;

    /**
     * 触发条件
     */
    @ApiModelProperty(value = "触发条件、1-生日提醒")
    private Integer triggerCondition;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态、1-启用、0-禁用")
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
