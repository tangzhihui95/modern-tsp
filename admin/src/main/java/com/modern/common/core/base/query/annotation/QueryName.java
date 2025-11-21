package com.modern.common.core.base.query.annotation;

import org.apache.poi.ss.formula.functions.T;

import java.lang.annotation.*;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/12 0:17
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 * 联合查询注解，目前只支持 外连接，不支持内连接
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryName {

    /**
     * 数据库表名称(主表)
     */
    public Class name();

    /**
     * 连表名称(从表)
     */
    public Class[] followName() default Exception.class;

    /**
     * 连接方式
     */
    public String join() default "left";
}
