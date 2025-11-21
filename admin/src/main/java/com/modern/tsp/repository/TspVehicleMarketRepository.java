package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.tsp.domain.TspVehicleMarket;
import com.modern.tsp.mapper.TspVehicleMarketMapper;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 9:30
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspVehicleMarketRepository extends ServicePlusImpl<TspVehicleMarketMapper, TspVehicleMarket,TspVehicleMarket> {
    public TspVehicleMarket getByTspVehicleId(Long tspVehicleId) {
        QueryWrapper<TspVehicleMarket> ew = new QueryWrapper<>();
        ew.eq("tsp_vehicle_id",tspVehicleId);
        return this.getOne(ew);
    }
}
