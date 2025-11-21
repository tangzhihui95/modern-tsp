package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.TspVehicleAlertStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 11:38
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("历史报警 - 数据传输对象 - 分页列表")
public class TspVehicleAlertPageListDTO extends BaseDTO {

    @Excel(name = "车牌号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车牌号")
    private String plateCode;

    @Excel(name = "告警等级",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("告警等级")
    private Integer level;

    @Excel(name = "vin",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("vin")
    private String vin;

    @Excel(name = "处理状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "1=已处理,0-未处理")
    @ApiModelProperty("处理状态")
    private Integer state;

    @Excel(name = "备注",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("备注")
    private String remark;

    @Excel(name = "上报时间",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("上报时间")
    private LocalDateTime escalationTime;

    @Excel(name = "解除时间",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("解除时间")
    private LocalDateTime relieveTime;

}
