package com.modern.common.core.base;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.ParameterizedType;
import java.time.format.DateTimeFormatter;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2019 tianjiugongxiang, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author wangzhiliang
 * @since 2019-10-24 19:39
 */
public abstract class BaseSqlProvider<T> implements ProviderMethodResolver {

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String insert(T t) {
        String tableName = getTableName(t);
        String finalTableName = tableName;
        return new SQL() {{
            INSERT_INTO(finalTableName);
            VALUES(DaoProvider.getColumns(t), DaoProvider.getValues(t));
        }}.toString();
    }


    private String getTableName(T t) {
        String tableName;
        TableName annotation = t.getClass().getAnnotation(TableName.class);
        if (annotation != null) {
            tableName = annotation.value();
        } else {
            tableName = t.getClass().getSimpleName();
        }
        return tableName;
    }

    private String getTableName(Class<?> clazz) {
        String tableName;
        TableName annotation = clazz.getAnnotation(TableName.class);
        if (annotation != null) {
            tableName = annotation.value();
        } else {
            tableName = clazz.getSimpleName();
        }
        return tableName;
    }


    public String delete(long id) {
        return new SQL() {{
            DELETE_FROM(getTableName(getRealType()));
            SET("is_delete = 1");
            WHERE("id = #{id}");
        }}.toString();
    }


    // 使用反射技术得到T的真实类型
    private Class getRealType() {
        // 获取当前new的对象的泛型的父类类型
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        // 获取第一个类型参数的真实类型
        return (Class<T>) pt.getActualTypeArguments()[0];
    }
}
