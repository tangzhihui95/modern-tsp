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
public enum TspFenceShapeEnum implements IEnum<Integer> {
    /**
     * 圆型
     */
    CIRCLE("圆型",1),
    /**
     * 多边形
     */
    POLYGON("多边形",2)
    ;

    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspFenceShapeEnum(String key, int value) {
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
