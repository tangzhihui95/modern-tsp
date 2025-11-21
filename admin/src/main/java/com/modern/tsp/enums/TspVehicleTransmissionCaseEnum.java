package com.modern.tsp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/1 11:31
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@ApiModel("车辆扩展信息 - 变速箱")
public enum TspVehicleTransmissionCaseEnum implements IEnum<Integer> {
    CVT("CVT变速箱",1),
    AT("AT变速箱",2),
    DCT("双离合变速箱",3);
    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspVehicleTransmissionCaseEnum(String key, int value) {
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
        return "TspVehicleTransmissionCaseEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
