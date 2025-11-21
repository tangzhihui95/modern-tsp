package com.modern.web.domain.dto.user;

import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * @author piaomiao
 * @apiNode piaomiao
 * @date 2022/6/820:30
 * <p>
 * Copyright: Copyright (C) 2019 tlcd, Inc. All rights reserved.
 * <p>
 * Company: 湖南成为科技有限公司
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("数据传输对象 - 根据部门获取岗位信息列表")
@Data
public class SysPostOptionsByDeptIdDTO extends BaseDTO {

    /** 岗位序号 */
    @ApiModelProperty(name = "岗位序号")
    private Long postId;

    /** 岗位名称 */
    @ApiModelProperty(name = "岗位名称")
    private String postName;
}
