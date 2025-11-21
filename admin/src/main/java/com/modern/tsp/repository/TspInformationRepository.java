package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspInformation;
import com.modern.tsp.mapper.TspInformationMapper;
import com.modern.tsp.model.vo.TspInformationPageListVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2022/10/13 20:42
 */
@Service
public class TspInformationRepository extends ServicePlusImpl<TspInformationMapper, TspInformation, TspInformation> {
    public TspInformation getByTitle(String title) {
        QueryWrapper<TspInformation> ew = new QueryWrapper<>();
        ew.eq("information_title", title);
        return this.getOne(ew);
    }

    public Wrapper<TspInformation> pageListEw(TspInformationPageListVO vo) {
        QueryWrapper<TspInformation> ew = new QueryWrapper<>();
        ew.eq(Objects.nonNull(vo.getInformationStatus()),"information_status", vo.getInformationStatus());
        ew.eq(Objects.nonNull(vo.getInformationType()),"information_type", vo.getInformationType());
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like("information_title", vo.getSearch()));
        ew.and(StringUtils.isNotNull(vo.getStartTime()) && StringUtils.isNotNull(vo.getEndTime()),
                q -> q.ge("create_time", vo.getStartTime() )
                        .le("create_time", vo.getEndTime()));
        ew.orderByDesc("create_time");
        return ew;
    }

}
