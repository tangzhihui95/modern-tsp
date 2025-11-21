package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.TpsVehicleDataKeyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 15:09
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@Data
@ApiModel("车型 - 数据传输对象 - 二级车型车型管理分页列表")
public class TspVehicleStdModePageListDTO extends BaseDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型ID")
    private Long tspVehicleModelId;


    @ApiModelProperty("车型")
    private String vehicleModelName;


    @ApiModelProperty("型号名称")
    private String stdModeName;


    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @ApiModelProperty("能源类型")
    private TpsVehicleDataKeyEnum dataKey;


    @ApiModelProperty("公告批次")
    private String noticeBatch;


    @ApiModelProperty("公告型号")
    private String noticeModel;


    @ApiModelProperty("二级车型关联车辆")
    private Integer stdModeCount;


}
