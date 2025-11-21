package com.modern.xtsp.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class TspVehicleSysRoleVO {
    /**
     * tsp_vehicle表id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long vehicleId;

    /**
     * sys_role表role_id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;

    /**
     * 车辆VIN 号
     */
    private String vin;

    /**
     * 角色名称字符串拼接
     */
    private String roleNames;

    /**
     * 车辆型号（一级车型+二级车型）
     */
    private String vehicleType;

    /**
     * 经销商id
     */
    private String dealerId;

    /**
     * 经销商名称
     */
    private String dealerName;

    /**
     * 经销商地址
     */
    private String dealerAddress;

}