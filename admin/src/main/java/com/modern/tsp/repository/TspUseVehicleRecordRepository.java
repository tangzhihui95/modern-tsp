package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.tsp.domain.TspUseVehicleRecord;
import com.modern.tsp.mapper.TspUseVehicleRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 16:19
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspUseVehicleRecordRepository extends ServicePlusImpl<TspUseVehicleRecordMapper, TspUseVehicleRecord,TspUseVehicleRecord> {
    public List<TspUseVehicleRecord> findByMobile(String mobile) {
        QueryWrapper<TspUseVehicleRecord> ew = new QueryWrapper<>();
        ew.eq(TspUseVehicleRecord.MOBILE,mobile);
        return this.list(ew);
    }
}
