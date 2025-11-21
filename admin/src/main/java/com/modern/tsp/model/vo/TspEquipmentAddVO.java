package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 16:10
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("设备信息 - 请求对象 - 添加")
@Valid
public class TspEquipmentAddVO {


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备ID")
    private Long tspEquipmentId;

    @NotNull(message = "设备分类不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备型号ID")
    private Long tspEquipmentModelId;

//    @ApiModelProperty("设备型号")
//    private String name;

    @NotEmpty(message = "ICCID不能为空")
    @Length(min = 19,max = 19,message = "ICCID必须由长度为19的字母数字组成")
    @Pattern(regexp = "^[A-Za-z\\d]+$",message = "ICCID必须由长度为19的字母数字组成")
    @ApiModelProperty("iccId")
    private String iccId;


    @NotEmpty(message = "IMSI不能为空")
    @Length(min = 1,max = 15,message = "IMSI只能由不超过15位的数字组成")
    @Pattern(regexp = "^[0-9]\\d*$",message = "IMSI只能由不超过15位的数字组成")
    @ApiModelProperty("IMSI")
    private String imsi;

    @NotEmpty(message = "SIM不能为空")
    @Length(min = 11,max = 11,message = "SIM必须长度为11位数字")
    @Pattern(regexp = "^[0-9]\\d*$",message = "SIM只能由数字组成")
    @ApiModelProperty("sim")
    private String sim;

    @NotEmpty(message = "IMEI不能为空")
    @Length(min = 15,max = 15,message = "IMEI必须长度为15位数字")
    @Pattern(regexp = "^[0-9]\\d*$",message = "IMEI只能由数字组成")
    @ApiModelProperty("imei")
    private String imei;

    /**
     * 版本号
     */
    @NotEmpty(message = "版本号不能为空")
    @Pattern(regexp = "^([0-9A-Za-z]){1,10}(-([0-9A-Za-z]){1,10}){2}$",message = "版本号格式错误,版本号格式应为X-X-X组成")
    @ApiModelProperty("版本号")
    private String version;

    /**
     * 设备SN
     */
    @NotEmpty(message = "设备SN不能为空")
    @Length(min = 1 ,max = 24,message = "设备SN不得超过24位、区分大小写")
    @ApiModelProperty("设备SN")
    private String sn;

    /**
     * 是否为终端1-是、0-否
     */
    @ApiModelProperty("是否为终端")
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
    @NotEmpty(message = "批次流水号不能为空")
    @Length(min = 1,max = 24,message = "批次流水号不得超过24位、区分大小写")
    @ApiModelProperty("批次流水号")
    private String serialNumber;

    /**
     * 是否在线1-在线、0-未在线
     */
    @ApiModelProperty("是否在线")
    private Boolean isOnline;

    /**
     * 是否注册1-是、0-否
     */
    @ApiModelProperty("是否注册")
    private Boolean isRegister;

    /**
     * 是否报废1-是、0-否
     */
    @ApiModelProperty("是否报废")
    private Boolean isScrap;
}
