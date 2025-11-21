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
public enum TspFenceDayEnum implements IEnum<Integer> {
    /**
     * 星期一
     */
    MONDAY("星期一",1),
    /**
     * 星期二
     */
    TUESDAY("星期二",2),
    /**
     * 星期三
     */
    WEDNESDAY("星期三",3),
    /**
     * 星期四
     */
    THURSDAY("星期四",4),
    /**
     * 星期五
     */
    FRIDAY("星期五",5),
    /**
     * 星期六
     */
    SATURDAY("星期六",6),
    /**
     * 星期日
     */
    SUNDAY("星期日",7)
    ;

    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspFenceDayEnum(String key, int value) {
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
