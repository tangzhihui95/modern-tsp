package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.tsp.domain.TspDealer;
import com.modern.tsp.domain.TspUserVehicle;
import com.modern.tsp.mapper.TspUserVehicleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 15:55
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspUserVehicleRepository extends ServicePlusImpl<TspUserVehicleMapper, TspUserVehicle,TspUserVehicle> {

    public TspUserVehicle getByUserVehicle(Long tspUserId, Long tspVehicleId) {
        QueryWrapper<TspUserVehicle> ew = new QueryWrapper<>();
        ew.eq("tsp_user_id",tspUserId);
        ew.eq("tsp_vehicle_id",tspVehicleId);
        return this.getOne(ew);
    }
}
