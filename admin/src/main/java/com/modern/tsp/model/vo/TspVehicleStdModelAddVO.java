package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.tsp.enums.TpsVehicleDataKeyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 17:17
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 请求对象 - 添加车型型号")
public class TspVehicleStdModelAddVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("型号ID")
    private Long tspVehicleStdModelId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型ID")
    private Long tspVehicleModelId;


    @NotEmpty(message = "型号名称不能为空")
    @ApiModelProperty("型号名称")
    private String stdModeName;


    @ApiModelProperty("能源类型")
    private TpsVehicleDataKeyEnum dataKey;


    @NotEmpty(message = "公告批次不能为空")
    @ApiModelProperty("公告批次")
    private String noticeBatch;


    @NotEmpty(message = "公告型号不能为空")
    @ApiModelProperty("公告型号")
    private String noticeModel;


    @ApiModelProperty("车辆厂商")
    private String firm;

    @Valid
    @ApiModelProperty("扩展信息")
    private TspVehicleStdModelExtraAddVO stdModelExtraAddVO;
}
