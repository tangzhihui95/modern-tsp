package com.modern.web.domain.dto.employee;

import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2021 tojoy, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2021年10月25日 0025 13:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeDropDownDTO extends BaseDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("员工名称")
    private String employeeName;
}
