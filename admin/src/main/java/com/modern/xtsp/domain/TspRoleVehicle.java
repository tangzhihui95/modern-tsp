package com.modern.xtsp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.modern.common.core.domain.BaseModel;
import lombok.*;

/**
 * 角色车辆关联表
 *
 * @TableName tsp_role_vehicle
 */
@TableName(value = "tsp_role_vehicle")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TspRoleVehicle extends BaseModel {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * sys_role表role_id
     */
    private Long roleId;

    /**
     * tsp_vehicle表id
     */
    private Long vehicleId;

}