package com.modern.tsp.model.dto;

import lombok.Data;

/**
 * @author Admin
 */
@Data
public class VolumeDTO {

    /**
     * 日期
     */
    private String date;

    /**
     * 车型
     */
    private String carType;

    /**
     * 接入量
     */
    private Integer count;

}
