package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.tsp.domain.TspVehicleAudit;
import com.modern.tsp.repository.TspVehicleAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/20 11:40
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspVehicleAuditService extends TspBaseService {

    @Autowired
    private TspVehicleAuditRepository tspVehicleAuditRepository;
    public TspVehicleAudit getByTspVehicleId(Long tspVehicleId) {
        QueryWrapper<TspVehicleAudit> ew = new QueryWrapper<>();
            ew.eq(TspVehicleAudit.TSP_VEHICLE_ID,tspVehicleId);
            ew.orderByDesc("create_time");
            ew.last("limit 1");
            return tspVehicleAuditRepository.getOne(ew);
        }


}
