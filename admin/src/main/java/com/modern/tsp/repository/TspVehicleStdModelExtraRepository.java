package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.tsp.domain.TspVehicleStdModelExtra;
import com.modern.tsp.mapper.TspVehicleStdModelExtraMapper;
import com.modern.tsp.service.TspVehicleStdModelExtraService;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/1 11:27
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspVehicleStdModelExtraRepository extends ServicePlusImpl<TspVehicleStdModelExtraMapper,TspVehicleStdModelExtra,TspVehicleStdModelExtra> {
    public TspVehicleStdModelExtra getByTspStdModelId(Long tspVehicleStdModelId) {
        QueryWrapper<TspVehicleStdModelExtra> ew = new QueryWrapper<>();
        ew.eq("tsp_vehicle_std_model_id",tspVehicleStdModelId);
        return this.getOne(ew);
    }
}
