package com.modern.exInterface.model.dto;

import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author piaomiao
 * @apiNode VehicleGpsSelectListDTO
 * @date 2022/6/2413:50
 * <p>
 * Copyright: Copyright (C) 2019 tlcd, Inc. All rights reserved.
 * <p>
 * Company: 湖南成为科技有限公司
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆位置数据 - 数据传输对象 - 模糊搜索下拉列表")
public class VehicleGpsSelectListDTO extends BaseDTO {

    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String plateCode;

    @ApiModelProperty("VIN")
    private String vin;


    @ApiModelProperty("sn")
    private String sn;



}
