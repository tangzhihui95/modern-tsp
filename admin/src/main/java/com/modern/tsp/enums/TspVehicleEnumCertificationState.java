package com.modern.tsp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 9:36
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@ApiModel("车辆信息 - 认证状态")
public enum TspVehicleEnumCertificationState implements IEnum<Integer> {

    /**
     * 认证状态1-未认证、2-认证中、3-认证失败、4-已认证
     */
    @ApiModelProperty("全部")
    ALL(0,"全部"),
    @ApiModelProperty("未认证")
    NOT_CERTIFIED(1,"未认证"),
    @ApiModelProperty("认证中")
    UNDER_CERTIFICATION(2,"认证中"),
    @ApiModelProperty("认证失败")
    FAILED_CERTIFICATION(3,"认证失败"),
    @ApiModelProperty("已认证")
    COMPLETE_CERTIFICATION(4,"已认证")
    ;
    @EnumValue
    private int value;
    @JsonValue
    private String key;

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    TspVehicleEnumCertificationState(int value, String key) {
        this.value = value;
        this.key = key;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
