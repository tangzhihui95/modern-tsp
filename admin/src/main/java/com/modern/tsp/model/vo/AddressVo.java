package com.modern.tsp.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Admin
 */
@Data
public class AddressVo {

    @ApiModelProperty("省份")
    private String provinceValue;

    @ApiModelProperty("市")
    private String cityValue;

    @ApiModelProperty("区")
    private String areaValue;
}
