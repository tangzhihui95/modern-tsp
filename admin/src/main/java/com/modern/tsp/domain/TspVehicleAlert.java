package com.modern.tsp.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import com.modern.tsp.enums.TspVehicleAlertStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/13 16:36
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Alias("TspVehicleAlert")
@Table(name = "tsp_vehicle_alert",comment = "摩登 - TSP - 车辆告警处理")
@TableName(value = "tsp_vehicle_alert",autoResultMap = true)
public class TspVehicleAlert extends BaseModel {


    @Column(comment = "告警规则ID",type = MySqlTypeConstant.BIGINT,isNull = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspAlertEventId;


    @Column(comment = "vin",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    private String vin;


    @Column(comment = "处理状态",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleAlertStateEnum state;


    @Column(comment = "报警等级",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    private Integer level;


    @Column(comment = "备注",type = MySqlTypeConstant.VARCHAR,length = 255)
    private String remark;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @Column(comment = "上报时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime escalationTime;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @Column(comment = "解除时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime relieveTime;
}
