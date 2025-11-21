package com.modern.tsp.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2023/3/27 0:10
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 请求对象 - 车辆流量统计")
public class TspVehicleFlowDataVO extends BaseVO {

//    @NotEmpty(message = "号码不能为空")
//    @ApiModelProperty("号码")
//    private String mdn;

//    @NotEmpty(message ="查询时间不能为空")
//    @Pattern(regexp = "[0-9]{4}[0-1][0-9]",message = "查询时间格式错误,格式应为yyyyMM")

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMM")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @ApiModelProperty("查询时间")
//    private String month;
//
//    @ApiModelProperty("RG(非必填)")
//    private String rg;
//
//    @ApiModelProperty("APN(非必填)")
//    private String apn;

    @ApiModelProperty("设备号码")
    private String accNbr;

    @ApiModelProperty("起始时间")
    private String startDate;

    @ApiModelProperty("截至时间")
    private String endDate;

    @ApiModelProperty("iccId")
    private String iccId;

    @ApiModelProperty("vin")
    private String vin;

}
