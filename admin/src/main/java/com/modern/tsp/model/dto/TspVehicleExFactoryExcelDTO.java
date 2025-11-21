package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/16 9:52
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 请求对象 - 导入导出出厂信息")
public class TspVehicleExFactoryExcelDTO {

    /**
     * 车辆厂商
     */
    @Excel(name = "车辆厂商",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车辆厂商")
    private String providerName;

    /**
     * 用途
     */
    @Excel(name = "车辆用途",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车辆用途")
    private String purpose;

    /**
     * 配置名称
     */
    @Excel(name = "配置名称",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("配置名称")
    private String configureName;

    /**
     * 颜色
     */
    @Excel(name = "外观颜色",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("外观颜色")
    private String color;


    @Excel(name = "TBOX版本号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("TBOX版本号")
    private String tboxVersion;

    /**
     * 批次号
     */
    @Excel(name = "批次号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("批次号")
    private String batchNo;

    @Excel(name = "设备型号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("设备型号")
    private String name;

    /**
     * VIN
     */
    @Excel(name = "设备版本号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("设备版本号")
    private String version;

    /**
     * 出厂日期
     */
    @Excel(name = "出厂日期",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,dateFormat = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出厂日期")
    private LocalDate exFactoryDate;

    /**
     * 下线时间
     */
    @Excel(name = "下线时间",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,dateFormat = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("下线时间")
    private LocalDate operateDate;

    /**
     * 标签
     */
    @Excel(name = "标签",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("标签")
    private String label;


    /**
     * 备注
     */
    @Excel(name = "备注",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("备注")
    private String remark;

    @Excel(name = "一级车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("一级车型")
    private String vehicleModelName;

    @Excel(name = "二级车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("二级车型")
    private String stdModelName;
    //    @Excel(name = "设备类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
//    @ApiModelProperty("设备类型")
//    private String equipmentTypeName;
    @Excel(name = "发动机号码",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("发动机号码")
    private String engineNum;

    @Excel(name = "电动机序列号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("电动机序列号")
    private String motorNum;
}
