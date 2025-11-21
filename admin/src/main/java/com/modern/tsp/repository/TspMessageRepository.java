package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspEquipment;
import com.modern.tsp.domain.TspMessage;
import com.modern.tsp.mapper.TspMessageMapper;
import com.modern.tsp.model.vo.TspMessagePageListVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/1 20:42
 */
@Service
public class TspMessageRepository extends ServicePlusImpl<TspMessageMapper, TspMessage, TspMessage> {
    public TspMessage getByTitle(String title) {
        QueryWrapper<TspMessage> ew = new QueryWrapper<>();
        ew.eq("title", title);
        return this.getOne(ew);
    }

    public Wrapper<TspMessage> pageListEw(TspMessagePageListVO vo) {
        QueryWrapper<TspMessage> ew = new QueryWrapper<>();
        ew.eq(vo.getState() != null, "state", vo.getState());
        if (vo.getMessageType() != null) {
            if (vo.getMessageType() == 1) {
                ew.in("send_type", 1, 2);
            }else {
                ew.in("send_type", 3, 4);
            }
        }


        ew.and(StringUtils.isNotNull(vo.getStartTime()) && StringUtils.isNotNull(vo.getEndTime()),
                q -> q.ge("create_time", vo.getStartTime())
                        .le("create_time", vo.getEndTime()));
//        ew.and(Objects.nonNull(vo.getStartTime())&&Objects.nonNull(vo.getEndTime()),
//                q->q.between("create_time",vo.getStartTime(),vo.getEndTime())
//        );
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like("title", vo.getSearch())
                        .or().like("create_by", vo.getSearch())
                        .or().like("content", vo.getSearch()));

        ew.orderByDesc("create_time");
        return ew;

    }

    public List<TspMessage> findDisposableMessageList() {
        QueryWrapper<TspMessage> ew = new QueryWrapper<>();
        ew.eq("send_type",2);
        ew.eq("state",1);
        return this.list(ew);
    }

    public List<TspMessage> findEventMessageList() {
        QueryWrapper<TspMessage> ew = new QueryWrapper<>();
        ew.eq("send_type",3);
        ew.eq("state",1);
        return this.list(ew);
    }

    public List<TspMessage> findPollMessageList() {
        QueryWrapper<TspMessage> ew = new QueryWrapper<>();
        ew.eq("send_type",4);
        ew.eq("state",1);
        return this.list(ew);
    }
}
