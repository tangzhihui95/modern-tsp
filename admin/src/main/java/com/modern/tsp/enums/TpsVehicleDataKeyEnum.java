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
 * @date 2022/7/1 9:34
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@ApiModel("车辆型号 - 能源类型")
public enum TpsVehicleDataKeyEnum implements IEnum<Integer> {

    UNDEFINE("未定义",0),
    HYBRID("混合动力",1),
    BE_VS("纯电动",2),
    FUEL_CELL_ELECTRIC("燃料电池电动",3),
    PLUG_IN_HYBRID("插电式混动",4),
    INCREMENTAL_HYBRID("增程式混动",5);
    @JsonValue
    private String key;
    @EnumValue
    private int value;

    TpsVehicleDataKeyEnum(String key, int value) {
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
        return "TpsVehicleDataKeyEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
