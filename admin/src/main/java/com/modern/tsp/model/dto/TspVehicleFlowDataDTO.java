package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.TspVehicleEnumCertificationState;
import com.modern.tsp.enums.TspVehicleSendEnum;
import com.modern.tsp.enums.TspVehicleStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2023/3/27 0:04
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("统计分析 - 数据传输对象 - 流量使用情况统计")
public class TspVehicleFlowDataDTO extends BaseDTO {
    /**
     * 流量统计
     */
    @ApiModelProperty("查询的流量结果")
    private String traffic;

    @ApiModelProperty("iccid")
    private String iccid;

    @ApiModelProperty("流量数值 单位：B")
    private Long bytes;

    /**
     * 车牌号
     */
    @Excel(name = "车牌号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车牌号")
    private String plateCode;


    @ApiModelProperty("二级车型ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleStdModelId;

    @ApiModelProperty("车型ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleModelId;

    /**
     * 一级车型
     */
    @Excel(name = "一级车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("一级车型")
    private String vehicleModelName;

    /**
     * 二级车型
     */
    @Excel(name = "二级车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("二级车型")
    private String stdModeName;

    @Excel(name = "VIN",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("VIN")
    private String vin;


    /**
     * 手机号(账号)
     */
    @Excel(name = "关联账号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("关联账号")
    private String mobile;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("真实姓名")
    private String realName;


}
