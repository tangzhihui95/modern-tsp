package com.modern.tsp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/14 16:03
 * @Version 1.0.0
 */
public enum TspVehicleSendEnum implements IEnum<Integer> {

    /**
     * 状态0-未推送、1-已推送、2-全部、
     */
    @ApiModelProperty("未推送")
    NOSEND("未推送",0),
    @ApiModelProperty("已推送")
    SENDED("已推送",1),
    @ApiModelProperty("全部")
    ALL("全部",2);
    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspVehicleSendEnum(String key, int value) {
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
        return "TspVehicleEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
