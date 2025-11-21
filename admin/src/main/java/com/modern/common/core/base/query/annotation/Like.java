package com.modern.common.core.base.query.annotation;

import java.lang.annotation.*;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/12 0:09
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Like {

    /**
     * 对应数据库字段名
     */
    public String value() default "";

    /**
     * 模糊查询方式
     */
    public String type() default "%%";

}
