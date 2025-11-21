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
 * @date 2022/10/12 11:12
 */
@ApiModel("信息发布 - 信息格式")
public enum TspInformationDataEnum implements IEnum<Integer> {
    /**
     * 平台
     */
    PICTURE("图文",0),
    /**
     * APP
     */
    LINK("链接",1)
    ;

    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspInformationDataEnum(String key, int value) {
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
        return "TspInformationDataEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
