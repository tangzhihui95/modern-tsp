package com.modern.common.utils.bean;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.BeanCopier;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * bean深拷贝工具
 *
 * @author Lion Li
 */
public class BeanCopyUtils {

	/**
	 * 单对象基于class创建拷贝
	 *
	 * @param source      数据来源实体
	 * @param copyOptions copy条件
	 * @param desc        描述对象 转换后的对象
	 * @return desc
	 */
	public static <T, V> V oneCopy(T source, CopyOptions copyOptions, Class<V> desc) {
		V v = ReflectUtil.newInstanceIfPossible(desc);
		return oneCopy(source, copyOptions, v);
	}

	/**
	 * 单对象基于对象创建拷贝
	 *
	 * @param source      数据来源实体
	 * @param copyOptions copy条件
	 * @param desc        转换后的对象
	 * @return desc
	 */
	public static <T, V> V oneCopy(T source, CopyOptions copyOptions, V desc) {
		return BeanCopier.create(source, desc, copyOptions).copy();
	}

	/**
	 * 列表对象基于class创建拷贝
	 *
	 * @param sourceList  数据来源实体列表
	 * @param copyOptions copy条件
	 * @param desc        描述对象 转换后的对象
	 * @return desc
	 */
	public static <T, V> List<V> listCopy(List<T> sourceList, CopyOptions copyOptions, Class<V> desc) {
		return sourceList.stream()
			.map(source -> oneCopy(source, copyOptions, desc))
			.collect(Collectors.toList());
	}


	public static <T>List<T> listToListDTO(List<T> list, Class<T> clazz){
		return JSON.parseArray(JSON.toJSONString(list), clazz);
	}


	/**
	 * @param input 输入集合
	 * @param clzz  输出集合类型
	 * @param <E>   输入集合类型
	 * @param <T>   输出集合类型
	 * @return 返回集合
	 */
	public static <E, T> List<T> convertList2List(List<E> input, Class<T> clzz) {
		List<T> output = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(input)) {
			for (E source : input) {
				T target = org.springframework.beans.BeanUtils.instantiate(clzz);
				BeanUtil.copyProperties(source, target);
				output.add(target);
			}
		}
		return output;
	}

}
