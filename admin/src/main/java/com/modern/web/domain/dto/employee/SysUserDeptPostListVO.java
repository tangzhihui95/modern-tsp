package com.modern.web.domain.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.base.QueryBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author pm
 * @apiNode piaomiao
 * @date 2022/6/811:46
 * <p>
 * Copyright: Copyright (C) 2019 tlcd, Inc. All rights reserved.
 * <p>
 * Company: 湖南成为科技有限公司
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户管理 - 请求对象 - 获取用户部门岗位列表")
public class SysUserDeptPostListVO extends QueryBase {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
}
