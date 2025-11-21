package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspFence;
import com.modern.tsp.mapper.TspFenceMapper;
import com.modern.tsp.model.vo.TspFencePageListVO;
import org.springframework.stereotype.Service;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 17:53
 * @Version 1.0.0
 */
@Service
public class TspFenceRepository extends ServicePlusImpl<TspFenceMapper, TspFence, TspFence> {
    public Wrapper<TspFence> PageListEw(TspFencePageListVO vo) {
        QueryWrapper<TspFence> ew = new QueryWrapper<>();
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like("fence_name", vo.getSearch()));
        ew.eq("is_delete",0);
        ew.orderByDesc("create_time");
        return ew;
    }

    public TspFence getByDealerName(String dealerName) {
        QueryWrapper<TspFence> ew = new QueryWrapper<>();
        ew.eq("fence_name",dealerName);
        return this.getOne(ew);
    }

}
