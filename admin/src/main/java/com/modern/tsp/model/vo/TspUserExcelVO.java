package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 15:43
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("用户 - 请求对象 - 导入")
public class TspUserExcelVO {

    /**
     * 真实姓名
     */
    @NotEmpty(message = "真实姓名不能为空")
    @ApiModelProperty("车主姓名")
    @Excel(name = "车主姓名",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String realName;

    /**
     * 手机号(账号)
     */
    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty("手机号(账号)")
    @Excel(name = "手机号(账号)",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String mobile;

    /**
     * 身份证号
     */
    @NotEmpty(message = "身份证号不能为空")
    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String idCard;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出生日期")
    @Excel(name = "出生日期",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String birthday;

    @ApiModelProperty("用户性别1-男、2-女、0-未知")
    @Excel(name = "用户性别",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "1=男,2=女")
    private Integer sex;

    @ApiModelProperty("注册地址(省)")
    @Excel(name = "注册地址(省)",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String province;

    @ApiModelProperty("注册地址(市)")
    @Excel(name = "注册地址(市)",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String city;

    @ApiModelProperty("注册地址(区)")
    @Excel(name = "注册地址(区)",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String area;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    @Excel(name = "详细地址",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String address;

    /**
     * 用户标签
     */
    @ApiModelProperty("用户标签")
    @Excel(name = "用户标签",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String label;

//    /**
//     * 身份证正反面
//     */
//    @TableField(value = "card_img_urls",typeHandler = FastjsonTypeHandler.class)
//    @ApiModelProperty("身份证正反面")
//    private List<String> cardImgUrls;

//    @ApiModelProperty("身份证正面")
//    @Excel(name = "身份证正面",cellType = Excel.ColumnType.IMAGE,type = Excel.Type.ALL)
//    private String userCardFrontImg;
//
//
//    @ApiModelProperty("身份证反面")
//    @Excel(name = "身份证反面",cellType = Excel.ColumnType.IMAGE,type = Excel.Type.ALL)
//    private String userCardBackImg;

    /**
     * 注册地址
     */
//    @ApiModelProperty("注册地址")
//    private String registerAddress;




}
