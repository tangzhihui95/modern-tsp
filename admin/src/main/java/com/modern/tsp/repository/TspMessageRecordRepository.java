package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspMessageRecord;
import com.modern.tsp.mapper.TspMessageRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/7 19:45
 */
@Service
public class TspMessageRecordRepository extends ServicePlusImpl<TspMessageRecordMapper, TspMessageRecord,TspMessageRecord> {
    public void removeByIdAndSendState(Long id, int sendState) {
        UpdateWrapper<TspMessageRecord> ew = new UpdateWrapper<>();
        ew.eq("tsp_message_id",id);
        ew.eq("send_state",sendState);
        ew.set("is_delete",1);
        ew.set("update_by", SecurityUtils.getUsername());
        this.update(ew);
    }

    public List<TspMessageRecord> findByTspMessageId(Long id) {
        QueryWrapper<TspMessageRecord> ew = new QueryWrapper<>();
        ew.eq("tsp_message_id",id);
        ew.eq("send_state",0);
        return this.list(ew);
    }
}
