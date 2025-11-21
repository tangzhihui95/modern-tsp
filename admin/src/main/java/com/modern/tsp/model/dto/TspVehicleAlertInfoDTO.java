package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.modern.common.core.domain.BaseDTO;
import com.modern.exInterface.entity.VehicleAlert;
import com.modern.exInterface.model.dto.VehicleAlertDTO;
import com.modern.tsp.enums.TspVehicleAlertStateEnum;
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
 * @date 2022/7/14 15:03
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("历史报警 - 数据传输对象 - 报警详情")
public class TspVehicleAlertInfoDTO extends BaseDTO {


    @ApiModelProperty("vin")
    private String vin;

    @ApiModelProperty("车主")
    private String realName;

    @ApiModelProperty("车牌号")
    private String plateCode;

    @ApiModelProperty("报警信息")
    private List<VehicleAlertDTO> alertDTO;

    @ApiModelProperty("处理状态")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleAlertStateEnum state;

    @ApiModelProperty("处理详情")
    private String remark;

}
