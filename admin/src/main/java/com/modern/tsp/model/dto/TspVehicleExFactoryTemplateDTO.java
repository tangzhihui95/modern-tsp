package com.modern.tsp.model.dto;

import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/20 15:03
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 数据传输对象 - 出厂信息模板")
public class TspVehicleExFactoryTemplateDTO {

    /**
     * vin
     */
    @Excel(name = "VIN",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("VIN")
    private String vin;

    /**
     * 车辆厂商
     */
    @Excel(name = "车辆厂商",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车辆厂商")
    private String providerName;


    /**
     * 配置名称
     */
    @Excel(name = "配置名称",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("配置名称")
    private String configureName;

    /**
     * 颜色
     */
    @Excel(name = "颜色",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("颜色")
    private String color;

    /**
     * 批次号
     */
    @Excel(name = "批次号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("批次号")
    private String batchNo;

    /**
     * 出厂日期
     */
    @Excel(name = "出厂日期",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("出厂日期")
    private String exFactoryDate;

    /**
     * 下线日期
     */
    @Excel(name = "下线日期",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("下线日期")
    private String operateDate;

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

    @Excel(name = "设备SN",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("设备SN")
    private String sn;

    @Excel(name = "设备类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("设备类型")
    private String name;

    @Excel(name = "设备型号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("设备型号")
    private String modelName;

    @Excel(name = "一级车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("一级车型")
    private String vehicleModelName;

    @Excel(name = "二级车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("二级车型")
    private String stdModelName;
//    @Excel(name = "设备类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
//    @ApiModelProperty("设备类型")
//    private String equipmentTypeName;
    @Excel(name = "发动机序列号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("发动机号码")
    private String engineNum;

    @Excel(name = "CDU序列号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("CDU序列号")
    private String cduNum;

    @Excel(name = "电池包规格",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("电池包规格")
    private String essModel;

    @Excel(name = "电池包编号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("电池包编号")
    private String essNum;

    @Excel(name = "电动机品牌",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("电动机品牌")
    private String motorBrand;

    @Excel(name = "电动机序列号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("电动机序列号")
    private String motorNum;
}
