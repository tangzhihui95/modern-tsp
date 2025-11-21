package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspDealer;
import com.modern.tsp.mapper.TspDealerMapper;
import com.modern.tsp.model.vo.TspDealerPageListVO;
import org.springframework.stereotype.Service;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 15:26
 * @Version 1.0.0
 */
@Service
public class TspDealerRepository extends ServicePlusImpl<TspDealerMapper, TspDealer, TspDealer> {

    public Wrapper<TspDealer> PageListEw(TspDealerPageListVO vo) {
        QueryWrapper<TspDealer> ew = new QueryWrapper<>();
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like("dealer_name", vo.getSearch()));
        ew.and(StringUtils.isNotEmpty(vo.getDealerAddress()),
                q -> q.like("dealer_address", vo.getDealerAddress()));
        ew.eq("is_delete",0);
        ew.orderByDesc("create_time");
        return ew;
    }

    public TspDealer getByDealerName(String dealerName) {
        QueryWrapper<TspDealer> ew = new QueryWrapper<>();
        ew.eq("dealer_name",dealerName);
        return this.getOne(ew);
    }

    public TspDealer getByCode(String dealerCode) {
        QueryWrapper<TspDealer> ew = new QueryWrapper<>();
        ew.eq("dealer_code",dealerCode);
        return this.getOne(ew);
    }
}
