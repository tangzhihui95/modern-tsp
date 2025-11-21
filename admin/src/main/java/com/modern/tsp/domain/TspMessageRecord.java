package com.modern.tsp.domain;

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
 * @date 2022/9/7 19:42
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_message_record",comment = "摩登 - TSP - 通知推送记录")
@Alias("TspMessageRecord")
@TableName(value = "tsp_message_record", autoResultMap = true)
@Data
public class TspMessageRecord extends BaseModel {


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "消息ID", type = MySqlTypeConstant.BIGINT)
    private Long tspMessageId;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "发送时间", type = MySqlTypeConstant.DATETIME,isNull = false)
    private LocalDateTime sendTime;


    @Column(comment = "发送状态1-已发送、0-待发送", type = MySqlTypeConstant.TINYINT,isNull = false,defaultValue = "0")
    private Integer sendState;
}
