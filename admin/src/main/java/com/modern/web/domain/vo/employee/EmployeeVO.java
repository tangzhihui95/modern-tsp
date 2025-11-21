package com.modern.web.domain.vo.employee;

import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工类型表
 * </p>
 *
 * @author piaomiao
 * @since 2021-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EmployeeVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 员工名称
     */
    @ApiModelProperty("员工名称")
    private String employeeName;


}
