package com.modern.common.core.base;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2019 tianjiugongxiang, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author wangzhiliang
 * @since 2019-10-09 15:29
 */
@Slf4j
public class DaoProvider {

    static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static final DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final String format = "#{%s}";

    /**
     * 获取所有列名
     */
    public static <T> String getColumns(T any) {
        StringBuilder sb = new StringBuilder();
        for (Field field : getAllFields(any)) {
            if ("id".equals(field.getName())) {
                continue;
            }
            Transient annotation = field.getAnnotation(Transient.class);
            if (annotation != null) {
                continue;
            }
            sb.append(field.getName()).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 获取所有列名
     */
    public static <T> String getColumnsWithTable(T any, String tableName) {
        StringBuilder sb = new StringBuilder();
        for (Field field : getAllFields(any)) {
            Transient annotation = field.getAnnotation(Transient.class);
            if (annotation != null) {
                continue;
            }
            sb.append(tableName).append(".").append(field.getName()).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }


    /**
     * 获取所有列值
     */
    public static <T> String getValues(T any) {
        StringBuilder sb = new StringBuilder();
        for (Field field : getAllFields(any)) {
            if ("id".equals(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            Object value = null;
            Transient annotation = field.getAnnotation(Transient.class);
            if (annotation != null) {
                continue;
            }
            try {
                if (field.get(any) == null) {
                    sb.append(value).append(",");
                    continue;
                }
                if (field.get(any).getClass().isEnum()) {
                    Method ordinal = field.get(any).getClass().getMethod("ordinal");
                    value = ordinal.invoke(field.get(any));
                    sb.append(value).append(",");
                    continue;
                }
            } catch (NoSuchMethodException | IllegalAccessException e) {
                log.error("DaoProvider获取枚举信息错误", e);
            } catch (InvocationTargetException e) {
                log.error("DaoProvider获取枚举信息错误", e);
            }
            sb.append(String.format(format, field.getName())).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }


    /**
     * 获取所有列值
     */
    public static <T> String getValues2(T any) {
        StringBuilder sb = new StringBuilder();
        for (Field field : getAllFields(any)) {
            if ("id".equals(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            Object value = null;
            try {
                Transient annotation = field.getAnnotation(Transient.class);
                if (annotation != null) {
                    continue;
                }
                if (field.get(any) == null) {
                    sb.append(value).append(",");
                    continue;
                }
                if (field.get(any) == "") {
                    value = "\'\'";
                } else {
                    value = field.get(any);
                }
                if (field.get(any).getClass() == String.class) {
                    value = "'" + field.get(any) + "'";
                }
                if (field.get(any).getClass() == LocalDateTime.class) {
                    value = "'" + df.format((LocalDateTime) field.get(any)) + "'";
                }
                if (field.get(any).getClass() == java.time.LocalDate.class) {
                    value = "'" + dateformatter.format((java.time.LocalDate) field.get(any)) + "'";
                }
                //处理枚举类型数据
                try {
                    if (field.get(any).getClass().isEnum()) {
                        Method ordinal = field.get(any).getClass().getMethod("ordinal");
                        value = ordinal.invoke(field.get(any));
                    }
                } catch (NoSuchMethodException e) {
                    log.error("DaoProvider获取枚举信息错误", e);
                } catch (InvocationTargetException e) {
                    log.error("DaoProvider获取枚举信息错误", e);
                }
            } catch (IllegalAccessException e) {
                log.error("DaoProvider复制信息错误", e);
            }
            sb.append(value).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    public static String getStringFromLocalDateTime(LocalDateTime localDateTime) {
        return dateformatter.format(localDateTime);
    }
}
