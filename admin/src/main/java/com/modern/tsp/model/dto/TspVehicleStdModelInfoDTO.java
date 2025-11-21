package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.domain.TspVehicleStdModelExtra;
import com.modern.tsp.enums.TpsVehicleDataKeyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/1 14:39
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 请求对象 - 车型型号详情")
public class TspVehicleStdModelInfoDTO extends BaseDTO {


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型ID")
    private Long tspVehicleModelId;


    @ApiModelProperty("型号名称")
    private String stdModeName;


    @ApiModelProperty("能源类型")
    private TpsVehicleDataKeyEnum dataKey;


    @ApiModelProperty("公告批次")
    private String noticeBatch;


    @ApiModelProperty("公告型号")
    private String noticeModel;


    @ApiModelProperty("车辆厂商")
    private String firm;

    @ApiModelProperty("扩展信息")
    private TspVehicleStdModelExtra stdModelExtra;
}
