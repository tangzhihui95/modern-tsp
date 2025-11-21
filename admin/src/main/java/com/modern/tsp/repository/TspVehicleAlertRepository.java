package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.tsp.domain.TspEquipment;
import com.modern.tsp.domain.TspVehicleAlert;
import com.modern.tsp.mapper.TspVehicleAlertMapper;
import com.modern.tsp.model.vo.TspVehicleAlertPageListVO;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 11:41
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspVehicleAlertRepository extends ServicePlusImpl<TspVehicleAlertMapper, TspVehicleAlert, TspVehicleAlert> {
    public Wrapper<TspVehicleAlert> getPageListEw(TspVehicleAlertPageListVO vo) {
        QueryWrapper<TspVehicleAlert> ew = new QueryWrapper<>();
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like(Constants.JOIN_TABLE_PREFIX_T + "vin", vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_C + "plate_code", vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_B + TspEquipment.SN, vo.getSearch()));
        ew.and(StringUtils.isNotNull(vo.getEscalationStartTime()) && StringUtils.isNotNull(vo.getEscalationEndTime()),
                q -> q.ge(Constants.JOIN_TABLE_PREFIX_T + "escalation_time", vo.getEscalationStartTime() + Constants.TIME)
                        .le(Constants.JOIN_TABLE_PREFIX_T + "escalation_time", vo.getEscalationEndTime() + Constants.LAST_TIME));
        ew.orderByDesc(Constants.JOIN_TABLE_PREFIX_T + "escalation_time");
        return ew;
    }
}
