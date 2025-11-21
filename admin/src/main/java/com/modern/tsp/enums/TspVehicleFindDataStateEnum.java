package com.modern.tsp.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/19 11:47
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@ApiModel("车辆信息 - 统计分析 - 车辆接入量查询方式")
public enum TspVehicleFindDataStateEnum {
    /**
     * SEVEN_DAYS-近七天、FOURTEEN-近14天、THIRTY-30天、THREE_MONTH-3个月、SIX_MONTH-6个月、TWELVE_MONTH-12个月、THREE_YEAR-3年、FIVE_YEAR-5年
     */
    @ApiModelProperty("近七天")
    SEVEN_DAYS,
    @ApiModelProperty("近14天")
    FOURTEEN_DAYS,
    @ApiModelProperty("30天")
    THIRTY_DAYS,
    @ApiModelProperty("3个月")
    THREE_MONTH,
    @ApiModelProperty("6个月")
    SIX_MONTH,
    @ApiModelProperty("12个月")
    TWELVE_MONTH,
    @ApiModelProperty("3年")
    THREE_YEAR,
    @ApiModelProperty("5年")
    FIVE_YEAR


}
