package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.TspVehicleSendEnum;
import com.modern.tsp.enums.TspVehicleStateEnum;
import com.modern.tsp.enums.TspVehicleEnumCertificationState;
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
 * @date 2022/6/14 22:45
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆信息 - 数据传输对象 - 分页列表")
public class TspVehiclePageListDTO extends BaseDTO {

    /**
     * 车牌号
     */
    @Excel(name = "车牌号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车牌号")
    private String plateCode;


    @ApiModelProperty("车型ID")
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
     * SIM卡号
     */
    @Excel(name = "SIM",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("SIM")
    private String sim;


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


    @Excel(name = "车辆状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车辆状态")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleStateEnum state;

    @ApiModelProperty("绑定状态")
    private String bindStatus;

//    @ApiModelProperty("设备sn")
//    private String sn;

    @Excel(name = "认证状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("认证状态")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleEnumCertificationState certificationState;

    @ApiModelProperty("实名认证状态")
    private String identificationStatus;

    @ApiModelProperty("是否在线1-在线、0-未在线")
    private Boolean isOnline;

    /**
     * 车卡信息是否推送1-已推送、0-未推送
     */
    @Excel(name = "推送状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车卡信息是否推送1-已推送、0-未推送")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleSendEnum sendStatus;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("推送时间")
    private LocalDateTime sendTime;

    /**
     * 推送方式
     */
    @ApiModelProperty("推送方式")
    private String mode;
    /**
     * 设备ID
     */
    @ApiModelProperty("设备ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentId;
}
