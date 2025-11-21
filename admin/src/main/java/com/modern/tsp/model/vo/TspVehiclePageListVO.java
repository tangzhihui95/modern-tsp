package com.modern.tsp.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.modern.common.core.domain.BaseVO;
import com.modern.tsp.enums.TspVehicleEnumCertificationState;
import com.modern.tsp.enums.TspVehicleSendEnum;
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
 * @date 2022/6/14 22:45
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆信息 - 请求对象 - 分页列表")
public class TspVehiclePageListVO extends BaseVO {

    @ApiModelProperty("关联账号")
    private String mobile;

    @ApiModelProperty("车型ID")
    private Long tspVehicleStdModelId;

    @ApiModelProperty("vin")
    private String vin;

    @ApiModelProperty("车牌号")
    private String plateCode;

    //设备ids
    @ApiModelProperty("设备ids")
    private List<Long> tspEquipmentIds;

    //新增bindStatus
    @ApiModelProperty("设备绑定状态")
    private String bindStatus;


    @ApiModelProperty("车辆状态")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleStateEnum state;

    @ApiModelProperty("关联账号")
    private Integer needAll;

    @ApiModelProperty("推送状态")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleSendEnum sendStatus;

    @ApiModelProperty("认证状态")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private TspVehicleEnumCertificationState certificationState;
    private List<Long> carIds;
}
