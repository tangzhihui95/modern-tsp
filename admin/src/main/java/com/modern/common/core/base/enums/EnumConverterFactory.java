//package com.ruoyi.common.core.base.enums;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.core.convert.converter.ConverterFactory;
//import org.springframework.stereotype.Component;
//
///**
// * 应用模块名称<p>
// * 代码描述<p>
// * Copyright: Copyright (C) 2021 tojoy, Inc. All rights reserved. <p>
// * Company: <p>
// *
// * @author piaomiao
// * @since 2021年10月27日 0027 15:46
// */
//@Component
//public class EnumConverterFactory implements ConverterFactory<Object,BaseEnum> {
//
//    @Override
//    public <T extends BaseEnum> Converter<Object, T> getConverter(Class<T> targetType) {
//        T[] enums = targetType.getEnumConstants();
//
//        return source -> {
//            for (T e : enums) {
//                if (e.getKey().equals(source)) {
//                    return e;
//                }
//            }
//            throw new IllegalArgumentException("枚举不正确");
//        };
//    }
//}
