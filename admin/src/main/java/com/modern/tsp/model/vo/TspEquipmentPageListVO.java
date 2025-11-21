package com.modern.tsp.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.BaseVO;
import com.modern.tsp.enums.TspVehicleStateEnum;
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
 * @date 2022/6/14 15:35
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("设备信息 - 请求对象 - 分页列表")
public class TspEquipmentPageListVO extends BaseVO {

    @ApiModelProperty("型号ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentModelId;

    @ApiModelProperty("SN")
    private String sn;

    @ApiModelProperty("SIM")
    private String sim;

    @ApiModelProperty("报废状态")
    private String showScrap;

    @ApiModelProperty("设备绑定状态")
    private String bindStatus;

    @ApiModelProperty("车辆状态")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleStateEnum state;

    @ApiModelProperty("设备ids")
    private List<Long> tspEquipmentIds;
}
