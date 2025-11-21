package com.modern.tsp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2022/12/3 11:12
 */
@ApiModel("通知推送 - 触发周期")
public enum TspMessageCycleEnum implements IEnum<Integer> {
    /**
     * 每日
     */
    PERDAY("每日",0),
    /**
     * 每周
     */
    PERWEEK("每周",1),
    /**
     * 每月
     */
    PERMONTH("每月",2)
    ;

    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspMessageCycleEnum(String key, int value) {
        this.key = key;
        this.value = value;
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
        return "TspMessageCycleEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
