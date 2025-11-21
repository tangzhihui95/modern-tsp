package com.modern.tsp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 22:49
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@ApiModel("车辆信息 - 车辆状态")
public enum TspVehicleStateEnum implements IEnum<Integer> {
    /**
     * 状态1-已创建、2-已销售、3-已绑定、4-已解绑、5-已报废、6-已注册
     */
    @ApiModelProperty("全部")
    ALL("全部",0),
    @ApiModelProperty("已创建")
    CREATED("已创建",1),
    @ApiModelProperty("已销售")
    SOLD("已销售",2),
    @ApiModelProperty("已绑定")
    BOUND("已绑定",3),
    @ApiModelProperty("已解绑")
    UNBOUND("已解绑",4),
    @ApiModelProperty("已报废")
    SCRAPPED("已报废",5),
    @ApiModelProperty("已注册")
    ALREADY("已注册",6);
    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TspVehicleStateEnum(String key, int value) {
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
