package com.modern.common.core.base.query.builder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.common.core.base.query.annotation.Like;
import com.modern.common.core.base.query.annotation.QueryName;
import com.modern.common.core.base.query.domain.Query;
import com.modern.common.core.base.query.mapper.QueryMapper;
import com.modern.common.utils.spring.SpringUtils;
import jodd.exception.UncheckedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Arrays;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/12 23:05
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 * 条件构建器
 */
@Slf4j
public class QueryBuilder {

    @Autowired
    private static QueryMapper<T> queryMapper;

    public static List<T> builderEw(Object queryClass) {

        QueryName queryName = AnnotationUtils.findAnnotation(queryClass.getClass(), QueryName.class);
        Like like = AnnotationUtils.findAnnotation(queryClass.getClass(), Like.class);
        // 获取Mapper对象
        BaseMapper beanMapper = SpringUtils.getBean(queryClass + "Mapper");
        QueryWrapper<Object> ew = new QueryWrapper<>();
        if (like != null) {
            if (queryName == null) {
                log.info("未找到QueryName注解信息,错误类:{}", queryClass);
                throw new UncheckedException("未找到QueryName注解信息");
            }
            Query query = new Query();
            query.setName(queryName.name());
            query.setJoin(query.getJoin());
            query.setFollowName(Arrays.asList(queryName.followName()));
            return queryMapper.queryList(ew, query);
        }
        return null;
    }
}
