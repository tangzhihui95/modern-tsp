package com.modern.common.core.base;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.modern.common.core.base.page.PagePlus;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 自定义 Service 接口, 实现 数据库实体与 DTO 对象转换返回
 *
 * @author Lion Li
 * @since 2021-05-13
 */
public interface IServicePlus<T, K> extends IService<T> {

	/**
	 * @param id          主键id
	 * @param copyOptions copy条件
	 * @return K对象
	 */
	K getDTOById(Serializable id, CopyOptions copyOptions);

	default K getDTOById(Serializable id) {
		return getDTOById(id, new CopyOptions());
	}

	/**
	 * @param convertor 自定义转换器
	 */
	default K getDTOById(Serializable id, Function<T, K> convertor) {
		return convertor.apply(getById(id));
	}

	/**
	 * @param idList      id列表
	 * @param copyOptions copy条件
	 * @return K对象
	 */
	List<K> listDTOByIds(Collection<? extends Serializable> idList, CopyOptions copyOptions);

	default List<K> listDTOByIds(Collection<? extends Serializable> idList) {
		return listDTOByIds(idList, new CopyOptions());
	}

	/**
	 * @param convertor 自定义转换器
	 */
	default List<K> listDTOByIds(Collection<? extends Serializable> idList,
								Function<Collection<T>, List<K>> convertor) {
		List<T> list = getBaseMapper().selectBatchIds(idList);
		if (list == null) {
			return null;
		}
		return convertor.apply(list);
	}

	/**
	 * @param columnMap   表字段 map 对象
	 * @param copyOptions copy条件
	 * @return K对象
	 */
	List<K> listDTOByMap(Map<String, Object> columnMap, CopyOptions copyOptions);

	default List<K> listDTOByMap(Map<String, Object> columnMap) {
		return listDTOByMap(columnMap, new CopyOptions());
	}

	/**
	 * @param convertor 自定义转换器
	 */
	default List<K> listDTOByMap(Map<String, Object> columnMap,
								Function<Collection<T>, List<K>> convertor) {
		List<T> list = getBaseMapper().selectByMap(columnMap);
		if (list == null) {
			return null;
		}
		return convertor.apply(list);
	}

	/**
	 * @param queryWrapper 查询条件
	 * @param copyOptions  copy条件
	 * @return K对象
	 */
	K getDTOOne(Wrapper<T> queryWrapper, CopyOptions copyOptions);

	default K getDTOOne(Wrapper<T> queryWrapper) {
		return getDTOOne(queryWrapper, new CopyOptions());
	}

	/**
	 * @param convertor 自定义转换器
	 */
	default K getDTOOne(Wrapper<T> queryWrapper, Function<T, K> convertor) {
		return convertor.apply(getOne(queryWrapper, true));
	}

	/**
	 * @param queryWrapper 查询条件
	 * @param copyOptions  copy条件
	 * @return K对象
	 */
	List<K> listDTO(Wrapper<T> queryWrapper, CopyOptions copyOptions);

	default List<K> listDTO(Wrapper<T> queryWrapper) {
		return listDTO(queryWrapper, new CopyOptions());
	}

	/**
	 * @param convertor 自定义转换器
	 */
	default List<K> listDTO(Wrapper<T> queryWrapper, Function<Collection<T>, List<K>> convertor) {
		List<T> list = getBaseMapper().selectList(queryWrapper);
		if (list == null) {
			return null;
		}
		return convertor.apply(list);
	}

	default List<K> listDTO() {
		return listDTO(Wrappers.emptyWrapper());
	}

	/**
	 * @param convertor 自定义转换器
	 */
	default List<K> listDTO(Function<Collection<T>, List<K>> convertor) {
		return listDTO(Wrappers.emptyWrapper(), convertor);
	}

	/**
	 * @param page         分页对象
	 * @param queryWrapper 查询条件
	 * @param copyOptions  copy条件
	 * @return K对象
	 */
	PagePlus<T, K> pageDTO(PagePlus<T, K> page, Wrapper<T> queryWrapper, CopyOptions copyOptions);

	default PagePlus<T, K> pageDTO(PagePlus<T, K> page, Wrapper<T> queryWrapper) {
		return pageDTO(page, queryWrapper, new CopyOptions());
	}

	/**
	 * @param convertor 自定义转换器
	 */
	default PagePlus<T, K> pageDTO(PagePlus<T, K> page, Wrapper<T> queryWrapper,
								  Function<Collection<T>, List<K>> convertor) {
		PagePlus<T, K> result = getBaseMapper().selectPage(page, queryWrapper);
		return result.setRecordsDTO(convertor.apply(result.getRecords()));
	}

	default PagePlus<T, K> pageDTO(PagePlus<T, K> page) {
		return pageDTO(page, Wrappers.emptyWrapper());
	}

	/**
	 * @param convertor 自定义转换器
	 */
	default PagePlus<T, K> pageDTO(PagePlus<T, K> page, Function<Collection<T>, List<K>> convertor) {
		return pageDTO(page, Wrappers.emptyWrapper(), convertor);
	}

	boolean saveAll(Collection<T> entityList);

}

