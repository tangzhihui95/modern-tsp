package com.modern.web.domain.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author piaomiao
 * @apiNode
 * @date 2022/6/918:08
 * <p>
 * Copyright: Copyright (C) 2019 tlcd, Inc. All rights reserved.
 * <p>
 * Company: 湖南成为科技有限公司
 */
@Data
@ApiModel(value = "用户管理 - 请求对象 - 分配权限")
public class SysUserPermissionVO {

    @ApiModelProperty("用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @ApiModelProperty("角色ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;

    @ApiModelProperty("数据权限部门ID集合")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long[] deptIds;

    @ApiModelProperty("所属部门ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;
}
