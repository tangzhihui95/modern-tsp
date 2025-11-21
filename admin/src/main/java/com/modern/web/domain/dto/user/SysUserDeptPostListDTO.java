package com.modern.web.domain.dto.user;

import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * @author pm
 * @apiNode piaomiao
 * @date 2022/6/811:52
 * <p>
 * Copyright: Copyright (C) 2019 tlcd, Inc. All rights reserved.
 * <p>
 * Company: 湖南成为科技有限公司
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户管理 - 传输对象 - 用户部门岗位列表")
public class SysUserDeptPostListDTO extends BaseDTO {
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("岗位ID")
    private Long postId;
    @ApiModelProperty("所属部门ID")
    private Long deptId;
    @ApiModelProperty("组织名称")
    private String deptName;
    @ApiModelProperty("组织类型 1-主职、2-兼职")
    private Integer type;
    @ApiModelProperty("职务名称")
    private String postName;
    @ApiModelProperty("职务排序")
    private Integer postSort;
    @ApiModelProperty("备注")
    private String remark;

}
