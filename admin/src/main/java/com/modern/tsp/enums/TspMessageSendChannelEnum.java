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
 * @date 2022/9/5 11:12
 */
@ApiModel("通知推送 - 推送渠道")
public enum TspMessageSendChannelEnum implements IEnum<Integer> {
    /**
     * 未知
     */
    UNKNOWN("未知",0),
    /**
     * 短信
     */
    MESSAGE("短信",1),
    /**
     * APP
     */
    APP("APP",2)
    ;

    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspMessageSendChannelEnum(String key, int value) {
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
        return "TspMessageSendChannelEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
