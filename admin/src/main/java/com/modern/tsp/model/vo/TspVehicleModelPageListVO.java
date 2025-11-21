package com.modern.tsp.model.vo;

import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 18:00
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆车型 - 请求对象 - 分页列表")
public class TspVehicleModelPageListVO extends BaseVO {


    @ApiModelProperty("车型/型号")
    private String vehicleModelName;

    @ApiModelProperty("车型/型号")
    private Long tspVehicleStdModelId;

    @ApiModelProperty("需要导出的id集合")
    private List<Long> ids;
}
