package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspDealer;
import com.modern.tsp.domain.TspTag;
import com.modern.tsp.mapper.TspDealerMapper;
import com.modern.tsp.mapper.TspTagMapper;
import com.modern.tsp.model.vo.TspDealerPageListVO;
import com.modern.tsp.model.vo.TspTagPageListVO;
import org.springframework.stereotype.Service;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 15:26
 * @Version 1.0.0
 */
@Service
public class TspTagRepository extends ServicePlusImpl<TspTagMapper, TspTag, TspTag> {

    public Wrapper<TspTag> PageListEw(TspTagPageListVO vo) {
        QueryWrapper<TspTag> ew = new QueryWrapper<>();
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like("tag_name", vo.getSearch()));
        ew.orderByDesc("create_time");
        ew.eq("is_delete",0);
        return ew;
    }

    public TspTag getByDealerName(String tagName) {
        QueryWrapper<TspTag> ew = new QueryWrapper<>();
        ew.eq("tag_name",tagName);
        return this.getOne(ew);
    }

}
