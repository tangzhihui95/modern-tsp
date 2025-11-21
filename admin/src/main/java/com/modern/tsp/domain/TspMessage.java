package com.modern.tsp.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import com.modern.tsp.enums.TspMessageCycleEnum;
import com.modern.tsp.enums.TspMessageSendChannelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/1 17:36
 * <p>
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_message",comment = "摩登 - TSP - 通知推送")
@Alias("TspMessage")
@TableName(value = "tsp_message", autoResultMap = true)
@Data
public class TspMessage extends BaseModel {

    /**
     * 标题
     */
    @Column(comment = "标题",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    private String title;

    /**
     * 推送渠道
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @Column(comment = "推送渠道、1-平台、2-APP",type = MySqlTypeConstant.INT,isNull = false)
    private TspMessageSendChannelEnum sendChannel;

    /**
     * 发送对象
     */
    @Column(comment = "发送对象",type = MySqlTypeConstant.VARCHAR,length = 255,isNull = false)
    private String userLabels;

    /**
     * 发送时间
     */
    @TableField(value = "send_time", typeHandler = FastjsonTypeHandler.class)
    @Column(comment = "发送时间",type = MySqlTypeConstant.VARCHAR,length = 500)
    private List<String> sendTimes = new ArrayList<>();

    /**
     * 内容
     */
    @Column(comment = "内容",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private String content;

    /**
     * 发送类型
     */
    @Column(comment = "发送类型、1-立即发送、2-定时发送、3-事件发送、4-循环定时发送",type = MySqlTypeConstant.INT)
    private Integer sendType;


    /**
     * 触发事件类型
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "触发事件类型、1-系统通知、2-告警通知、3-保养通知、0-未知",type = MySqlTypeConstant.INT)
    private Integer eventType;

    /**
     * 触发条件
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "触发条件、1-生日提醒",type = MySqlTypeConstant.INT)
    private Integer triggerCondition;

    /**
     * 状态
     */
    @Column(comment = "状态、1-启用、0-禁用",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    private Integer state;

    /**
     * 触发时间
     */
    @Column(comment = "触发时间",type = MySqlTypeConstant.VARCHAR,length = 50)
    private String time;

    /**
     * 触发周期
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "触发周期0-每日，1-每周，2-每月",type = MySqlTypeConstant.TINYINT,length = 1)
    private TspMessageCycleEnum cycle;

//    /**
//     * 用来记录已发送过的消息对应发送时间
//     */
//    @TableField(value = "send_count", typeHandler = FastjsonTypeHandler.class)
//    @Column(comment = "即时消息已发送消息(用来记录已发送过的消息对应发送时间)",type = MySqlTypeConstant.VARCHAR,length = 500)
//    private List<String> sendCount = new ArrayList<>();
}
