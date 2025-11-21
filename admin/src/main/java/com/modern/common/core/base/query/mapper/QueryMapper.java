package com.modern.common.core.base.query.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.modern.common.core.base.query.domain.Query;
import com.modern.common.core.base.query.provider.QueryProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/13 0:01
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
public interface QueryMapper<T> extends BaseMapper<T> {

    @SelectProvider(type= QueryProvider.class, method="queryList")
    public List<T> queryList(@Param(Constants.WRAPPER) QueryWrapper<Object> ew, @Param("query") Query query);
}
