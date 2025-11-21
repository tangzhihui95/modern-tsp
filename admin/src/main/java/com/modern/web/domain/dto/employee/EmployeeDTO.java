package com.modern.web.domain.dto.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.modern.common.core.domain.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2021 tojoy, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2021年10月23日 0023 18:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeDTO extends BaseDTO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 员工名称
     */
    private String employeeName;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除0-否、1-是
     */
    private Integer isDelete;
}
