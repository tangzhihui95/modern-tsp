package com.modern.tsp.waring.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.annotation.Excel;
import com.modern.common.core.base.TableName;
import com.modern.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 【请填写功能名称】对象 warning_info
 *
 * @author piaomiao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "warning_info")
public class WarningInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 是否删除 1-是、0-否
     */
    @Excel(name = "是否删除 1-是、0-否")
    private Integer isDelete;

    /**
     * 规则名称
     */
    @Excel(name = "规则名称")
    private String ruleName;

    /**
     * 规则描述
     */
    @Excel(name = "规则描述")
    private String ruleDetail;

    /**
     * 规则来源
     */
    @Excel(name = "规则来源")
    private String ruleSource;

    /**
     * 推送方式
     */
    @Excel(name = "推送方式")
    private String steelPhone;

    /**
     * 推送次数
     */
    @Excel(name = "推送次数")
    private Integer sendCount;

    /**
     * 预警类型
     */
    @Excel(name = "预警类型")
    private Integer type;

    /**
     * 预警距离
     */
    @Excel(name = "预警距离")
    private Integer waringDistance;

}
