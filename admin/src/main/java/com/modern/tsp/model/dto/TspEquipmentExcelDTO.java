package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 15:34
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@Data
@ApiModel("设备信息 - 数据传输对象 - 导入导出")
public class TspEquipmentExcelDTO {

    @Excel(name = "设备型号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 1)
    @ApiModelProperty("设备型号名称")
    private String modelName;

    @Excel(name = "设备扩展信息类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 1)
    @ApiModelProperty("设备扩展信息类型")
    private String extraType;

    @Excel(name = "设备分类",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 2)
    @ApiModelProperty("设备分类")
    private String name;

    /**
     * VIN
     */
    @Excel(name = "版本号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 3)
    @ApiModelProperty("版本号")
    @NotEmpty(message = "版本号不能为空")
    @Pattern(regexp = "^([0-9A-Za-z]){1,10}(-([0-9A-Za-z]){1,10}){2}$",message = "版本号格式错误,版本号格式应为X-X-X组成")
    private String version;

    /**
     * 设备SN
     */
    @Excel(name = "设备SN",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 4)
    @ApiModelProperty("设备SN")
    private String sn;

    /**
     * ICCID
     */
    @Excel(name = "ICCID",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 5)
    @ApiModelProperty("ICCID")
    @NotEmpty(message = "ICCID不能为空")
    @Length(min = 19,max = 19,message = "ICCID必须由长度为19的字母数字组成")
    @Pattern(regexp = "^[A-Za-z\\d]+$",message = "ICCID必须由长度为19的字母数字组成")
    private String iccId;

    /**
     * IMSI
     */
    @Excel(name = "IMSI",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 6)
    @ApiModelProperty("IMSI")
    @NotEmpty(message = "IMSI不能为空")
    @Length(min = 1,max = 15,message = "IMSI只能由不超过15位的数字组成")
    @Pattern(regexp = "^[0-9]\\d*$",message = "IMSI只能由不超过15位的数字组成")
    private String imsi;

    /**
     * SIM
     */
    @Excel(name = "SIM",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 7)
    @ApiModelProperty("SIM")
    @NotEmpty(message = "SIM不能为空")
    @Length(min = 11,max = 11,message = "SIM必须长度为11位数字")
    @Pattern(regexp = "^[0-9]\\d*$",message = "SIM只能由数字组成")
    private String sim;

    /**
     * IMEI
     */
    @Excel(name = "IMEI",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 8)
    @ApiModelProperty("IMEI")
    @NotEmpty(message = "IMEI不能为空")
    @Length(min = 15,max = 15,message = "IMEI必须长度为15位数字")
    @Pattern(regexp = "^[0-9]\\d*$",message = "IMEI只能由数字组成")
    private String imei;

    /**
     * 是否为终端1-是、0-否
     */
    @Excel(name = "是否为终端",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "true=是,false=否",sort = 9)
    @ApiModelProperty("是否为终端1-是、0-否")
    private Boolean isTerminal;

    /**
     * 供应商代码
     */
    @Excel(name = "供应商代码",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 10)
    @ApiModelProperty("供应商代码")
    private String supplierCode;

    /**
     * 批次流水号
     */
    @Excel(name = "批次流水号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 11)
    @ApiModelProperty("批次流水号")
    private String serialNumber;

    /**
     * 是否在线1-在线、0-未在线
     */
//    @Excel(name = "是否在线",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "true=是,false=否",sort = 12)
    @ApiModelProperty("是否在线1-在线、0-未在线")
    private Boolean isOnline;

    /**
     * 是否注册1-是、0-否
     */
    @Excel(name = "是否注册",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "true=是,false=否",sort = 13)
    @ApiModelProperty("是否注册1-是、0-否")
    private Boolean isRegister;

    /**
     * 是否报废1-是、0-否
     */
//    @Excel(name = "是否报废",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "true=是,false=否",sort = 14)
    @ApiModelProperty("是否报废1-是、0-否")
    private Boolean isScrap;

    /**
     * 运营商
     */
    @Excel(name = "运营商",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 15)
    @ApiModelProperty("运营商")
    private String operator;
}
