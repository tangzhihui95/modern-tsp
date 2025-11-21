package com.modern.common.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/22 11:31
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
public class BaseExModel implements Serializable {

    /**
     * 采取雪花算法生成策略
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected Long id;

    /**
     * VIN;（应符合GB16735的规定）
     */
    @ApiModelProperty("vin")
    protected String vin;

    /**
     * 数据类型;（0x02实时数据，0x03补发数据）
     */
    @ApiModelProperty("数据类型")
    protected Integer dataType;

    /**
     * 数据采集时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("采集时间")
    protected LocalDateTime collectTime;

    /**
     * 数据插入数据库时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("接收时间")
    protected LocalDateTime createTime;

    /**
     * 逻辑删除;（1 表示删除，0 表示未删除）
     */
    @TableLogic(value = "0", delval = "1")
    @TableField(select = false)
    private Integer deleted;

    public static final String ID = "id";

    public static final String VIN = "vin";

    public static final String DATA_TYPE = "data_type";

    public static final String COLLECT_TIME = "collect_time";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETED = "deleted";
}
