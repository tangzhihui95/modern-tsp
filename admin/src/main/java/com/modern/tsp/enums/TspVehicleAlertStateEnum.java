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
 * @date 2022/7/15 10:51
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@ApiModel("历史报警 - 处理状态")
public enum TspVehicleAlertStateEnum implements IEnum<Integer> {
    /**
     * 状态1-处理、0-未处理
     */

    @ApiModelProperty("未处理")
    NOT_COMPLETE("未处理",0),

    @ApiModelProperty("已处理")
    COMPLETE("已处理",1);

    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspVehicleAlertStateEnum(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TspVehicleAlertStateEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
