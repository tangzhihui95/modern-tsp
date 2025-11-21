package com.modern.xtsp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.modern.common.core.domain.BaseModel;
import lombok.*;

/**
 * 角色车辆关联表
 *
 * @TableName tsp_vehicle_sys_role
 */
@TableName(value = "tsp_vehicle_sys_role")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TspVehicleSysRole extends BaseModel {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * tsp_vehicle表id
     */
    private Long vehicleId;

    /**
     * sys_role表role_id
     */
    private Long roleId;
}