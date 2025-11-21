package com.modern.web.enums.base;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2021 tojoy, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2021年10月23日 0023 17:28
 */
public enum StatusEnum implements IEnum<Integer> {
    OPEN(0,"开启"),
    CLOSE(1,"关闭");

    @EnumValue
    private int val;
    private String key;

    StatusEnum(int val, String key) {
        this.val = val;
        this.key = key;
    }

    @JsonValue
    public String getKey() {
        return key;
    }

    @Override
    public Integer getValue() {
        return this.val;
    }
}
