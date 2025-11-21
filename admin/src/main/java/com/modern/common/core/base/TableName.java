package com.modern.common.core.base;

import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2019 tianjiugongxiang, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author wangzhiliang
 * @since 2019-10-09 15:47
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Bean
public @interface TableName {
    String value();
}
