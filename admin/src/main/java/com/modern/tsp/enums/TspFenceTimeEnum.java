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
public enum TspFenceTimeEnum implements IEnum<Integer> {
    /**
     * 00:00~01:59
     */
    ONE("00:00~01:59",1),
    /**
     * 02:00~03:59
     */
    TWO("02:00~03:59",2),
    /**
     * 04:00~05:59
     */
    THREE("04:00~05:59",3),
    /**
     * 06:00~07:59
     */
    FOUR("06:00~07:59",4),
    /**
     * 08:00~09:59
     */
    FIVE("08:00~09:59",5),
    /**
     * 10:00~11:59
     */
    SIX("10:00~11:59",6),
    /**
     * 12:00~13:59
     */
    SEVEN("12:00~13:59",7),
    /**
     * 14:00~15:59
     */
    EIGHT("14:00~15:59",8),
    /**
     * 16:00~17:59
     */
    NINE("16:00~17:59",9),
    /**
     * 18:00~19:59
     */
    TEN("18:00~19:59",10),
    /**
     * 20:00~21:59
     */
    ELEVEN("20:00~21:59",11),
    /**
     * 22:00~23:59
     */
    TWELVE("22:00~23:59",12)
    ;

    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspFenceTimeEnum(String key, int value) {
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
