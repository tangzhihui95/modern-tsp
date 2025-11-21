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
public enum TspFenceNumEnum implements IEnum<Integer> {
    /**
     * 常提醒
     */
    OFTEN("常提醒",1),
    /**
     * 偶尔提醒
     */
    OCCASIONALLY("偶尔提醒",2)
    ;

    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspFenceNumEnum(String key, int value) {
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
