package com.modern.tsp.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.tsp.domain.TypeJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 16:11
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("告警规则 - 请求对象 - 添加")
public class TspAlertEventAddVO {

    @ApiModelProperty("告警规则ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspAlertEventId;

    @ApiModelProperty("告警规则名称")
    private String eventName;

    @ApiModelProperty("告警类型")
    private Integer eventType;


    @TableField(typeHandler = FastjsonTypeHandler.class,value = "type_json")
    @ApiModelProperty("规则类型")
    private List<List<TypeJson>> typeJson;


    @ApiModelProperty("持续分钟")
    private Integer continueMinute;


    @ApiModelProperty("告警级别")
    private Integer eventLevel;


    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty("监控开始时间")
    private Time monitorStartTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty("监控结束时间")
    private Time monitorEndTime;


    @ApiModelProperty("备注")
    private String remark;
}
