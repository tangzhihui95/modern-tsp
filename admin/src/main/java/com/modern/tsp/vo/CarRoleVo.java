package com.modern.tsp.vo;

import lombok.Data;

/**
 * @author Admin
 */
@Data
public class CarRoleVo {

    /**
     * 权限ID
     */
    private Long roleId;

    /**
     * 车辆ID
     */
    private Long carId ;

    /**
     * 车辆信息
     */
    private String carInfoText;
}
