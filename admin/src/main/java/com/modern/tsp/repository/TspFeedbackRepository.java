package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspDealer;
import com.modern.tsp.domain.TspFeedback;
import com.modern.tsp.mapper.TspDealerMapper;
import com.modern.tsp.mapper.TspFeedbackMapper;
import com.modern.tsp.model.vo.TspDealerPageListVO;
import com.modern.tsp.model.vo.TspFeedbackPageListVO;
import org.springframework.stereotype.Service;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 15:26
 * @Version 1.0.0
 */
@Service
public class TspFeedbackRepository extends ServicePlusImpl<TspFeedbackMapper, TspFeedback, TspFeedback> {

    public Wrapper<TspFeedback> PageListEw(TspFeedbackPageListVO vo) {
        QueryWrapper<TspFeedback> ew = new QueryWrapper<>();
        ew.and(StringUtils.isNotNull(vo.getStartTime()) && StringUtils.isNotNull(vo.getEndTime()),
                //ge：大于等于 le：小于等于
                //xx>=start  xx<=end
                q -> q.ge("feedback_time", vo.getStartTime())
                        .le("feedback_time", vo.getEndTime()));
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like("feedback_content", vo.getSearch()));
        ew.eq(vo.getDealStatus() != null,"deal_status",vo.getDealStatus());
        ew.orderByDesc("feedback_time");
        ew.orderByDesc("create_time");
        return ew;
    }

}
