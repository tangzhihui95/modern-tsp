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
import com.modern.tsp.enums.TspVehicleStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 15:34
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("设备信息 - 数据传输对象 - 分页列表")
public class TspEquipmentPageListDTO extends BaseDTO {

    @ApiModelProperty("设备绑定的车辆ID（若未绑定则为NULL）")
    private Long tspVehicleId;

    @ApiModelProperty("设备绑定的车辆VIN（若未绑定则为NULL）")
    private String tspVehicleVin;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备类型ID")
    private Long tspEquipmentTypeId;

    @ApiModelProperty("设备扩展信息类型")
    private String extraType;

    /**
     * 型号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备型号ID")
    private Long tspEquipmentModelId;

    @ApiModelProperty("设备分类")
    private String name;

    @ApiModelProperty("设备绑定状态")
    private String bindStatus;

    @Excel(name = "车辆状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车辆状态")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleStateEnum state;


    @ApiModelProperty("设备型号")
    private String modelName;

    @ApiModelProperty("设备型号")
    private String typeModel;

    @ApiModelProperty("供应商")
    private String suppliers;

    /**
     * VIN
     */
    @ApiModelProperty("版本号")
    private String version;

    /**
     * 设备SN
     */
    @ApiModelProperty("设备SN")
    private String sn;

    @ApiModelProperty("iccId")
    private String iccId;

    @ApiModelProperty("IMSI")
    private String imsi;


    @ApiModelProperty("sim")
    private String sim;


    @ApiModelProperty("imei")
    private String imei;

    /**
     * 是否为终端1-是、0-否
     */
    @ApiModelProperty("是否为终端1-是、0-否")
    private Boolean isTerminal;

    /**
     * 供应商代码
     */
    @ApiModelProperty("供应商代码")
    private String supplierCode;

    @ApiModelProperty("运营商")
    private Integer operator;

    /**
     * 批次流水号
     */
    @ApiModelProperty("批次流水号")
    private String serialNumber;

    /**
     * 是否在线1-在线、0-未在线
     */
    @ApiModelProperty("是否在线1-在线、0-未在线")
    private Boolean isOnline;

    /**
     * 是否注册1-是、0-否
     */
    @ApiModelProperty("是否注册1-是、0-否")
    private Boolean isRegister;

    /**
     * 是否报废1-是、0-否
     */
    @ApiModelProperty("是否报废1-是、0-否")
    private Boolean isScrap;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("报废时间")
    private LocalDateTime scrapTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("解绑时间")
    private LocalDateTime unBindTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("上传时间")
    private LocalDateTime uploadTime;
}
