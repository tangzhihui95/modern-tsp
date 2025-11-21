package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspAlertEvent;
import com.modern.tsp.mapper.TspAlertEventMapper;
import com.modern.tsp.model.vo.TspAlertEventPageListVO;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 15:37
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspAlertEventRepository extends ServicePlusImpl<TspAlertEventMapper, TspAlertEvent, TspAlertEvent> {
    public Wrapper<TspAlertEvent> getPageListEw(TspAlertEventPageListVO vo) {
        QueryWrapper<TspAlertEvent> ew = new QueryWrapper<>();
        ew.like(StringUtils.isNotEmpty(vo.getEventName()), "event_name", vo.getEventName());
        return ew;
    }

    public TspAlertEvent getByEventName(String eventName) {
        QueryWrapper<TspAlertEvent> ew = new QueryWrapper<>();
        ew.eq("event_name",eventName);
        return this.getOne(ew);
    }
}
