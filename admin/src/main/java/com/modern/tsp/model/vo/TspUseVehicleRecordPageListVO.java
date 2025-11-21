package com.modern.tsp.model.vo;

import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/19 1:19
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("绑定记录 - 请求对象 - 分页列表")
public class TspUseVehicleRecordPageListVO extends BaseVO {

    @ApiModelProperty("用户ID")
    private Long tspUserId;


    /**
     * 车辆ID
     */
    @ApiModelProperty
    private Long tspVehicleId;
}
