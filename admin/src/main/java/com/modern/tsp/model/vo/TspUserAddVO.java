package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

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
@ApiModel("用户 - 请求对象 - 添加")
public class TspUserAddVO {


    @ApiModelProperty("用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspUserId;

    /**
     * 手机号(账号)
     */
    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty("手机号(账号)")
    private String mobile;

    /**
     * 真实姓名
     */
    @NotEmpty(message = "真实姓名不能为空")
    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @NotEmpty(message = "身份证号不能为空")
    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("用户性别1-男、2-女、0-未知")
    private Integer sex;


    /**
     * 出生日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出生日期")
    private LocalDate birthday;

//    /**
//     * 身份证正反面
//     */
//    @TableField(value = "card_img_urls",typeHandler = FastjsonTypeHandler.class)
//    @ApiModelProperty("身份证正反面")
//    private List<String> cardImgUrls;

    @ApiModelProperty("身份证正面")
    private String userCardFrontImg;


    @ApiModelProperty("身份证反面")
    private String userCardBackImg;

    /**
     * 注册地址
     */
//    @ApiModelProperty("注册地址")
//    private String registerAddress;

    @ApiModelProperty("注册地址(省)")
    private String province;


    @ApiModelProperty("注册地址(市)")
    private String city;

    @ApiModelProperty("注册地址(区)")
    private String area;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String address;

    /**
     * 用户标签
     */
    @ApiModelProperty("用户标签")
    @JsonSerialize(contentUsing = ToStringSerializer.class)
    private List<Long> label;


}
