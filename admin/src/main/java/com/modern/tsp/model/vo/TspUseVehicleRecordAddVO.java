package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 16:38
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("用户记录 - 请求对象 - 添加")
public class TspUseVehicleRecordAddVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("用户ID")
    private Long tspUserId;

    @ApiModelProperty("车辆ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleId;

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
}
