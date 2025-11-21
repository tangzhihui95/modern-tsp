package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspUser;
import com.modern.tsp.mapper.TspUserMapper;
import com.modern.tsp.model.vo.TspUserPageListVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 15:55
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EnableCaching
@Service
public class TspUserRepository extends ServicePlusImpl<TspUserMapper, TspUser,TspUser> {
    public TspUser getByMobile(String mobile) {
        QueryWrapper<TspUser> ew = new QueryWrapper<>();
        ew.eq(TspUser.MOBILE,mobile);
        ew.orderByDesc(TspUser.CREATE_TIME);
        ew.last(" limit 1");
        return this.getOne(ew);
    }

    public QueryWrapper<TspUser> getPageListEw(TspUserPageListVO vo) {
        QueryWrapper<TspUser> ew = new QueryWrapper<>();
        ew.in(CollectionUtils.isNotEmpty(vo.getIds()),"id",vo.getIds());
        ew.like(StringUtils.isNotEmpty(vo.getMobile()), TspUser.MOBILE, vo.getMobile());
        ew.like(StringUtils.isNotEmpty(vo.getRealName()), TspUser.REAL_NAME, vo.getRealName());
        ew.orderByDesc("create_time");
        return ew;
    }

    @Override
//    @Cacheable(value = "TspUser", key = "methodName + #id")
    public TspUser getById(Serializable id) {
        return super.getById(id);
    }
}
