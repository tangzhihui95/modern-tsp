package com.modern.tsp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 17:04
 * @Version 1.0.0
 */
public enum TspFenceStatusEnum implements IEnum<Integer> {
    /**
     * 垫用
     */
    STATUS("开启",0),
    /**
     * 开启
     */
    STARTED("开启",1),
    /**
     * 关闭
     */
    CLOSED("关闭",2)
    ;

    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspFenceStatusEnum(String key, int value) {
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
        return "TspFenceShapeEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
