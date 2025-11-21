package com.modern.web.domain.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author piaomiao
 * @apiNode SysUserPasswordReset
 * @date 2022/6/910:19
 * <p>
 * Copyright: Copyright (C) 2019 tlcd, Inc. All rights reserved.
 * <p>
 * Company: 湖南成为科技有限公司
 */
@Data
@ApiModel("用户管理 - 请求对象 - 密码重置")
public class SysUserPasswordResetVO{

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;


    @NotEmpty(message = "新密码不能为空")
    @Size(max = 16,min = 6, message = "长度必须大于等于6或小于等于16")
    private String password;

    @NotEmpty(message = "确认密码不能为空")
    private String conferMaPassword;
}
