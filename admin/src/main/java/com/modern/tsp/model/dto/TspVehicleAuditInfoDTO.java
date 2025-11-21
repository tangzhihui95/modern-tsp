package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.domain.TspUser;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.enums.TspVehicleEnumCertificationState;
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
 * @date 2022/6/20 12:10
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆信息 - 传输对象 - 认证信息")
public class TspVehicleAuditInfoDTO extends BaseDTO {
    /**
     * 身份证正反面
     */
    @TableField(value = "card_img_urls", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("身份证正反面")
    private List<String> cardImgUrls;
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @ApiModelProperty("认证状态")
    private TspVehicleEnumCertificationState certificationState;
    @ApiModelProperty("不通过原因")
    private String auditRemark;
    @ApiModelProperty("用户信息")
    private TspUser tspUser;
    @ApiModelProperty("车辆及用户信息")
    private TspVehicle tspVehicle;
    @ApiModelProperty("车牌号")
    private String plateCode;

}
