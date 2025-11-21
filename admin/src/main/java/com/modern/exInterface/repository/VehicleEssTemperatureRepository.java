package com.modern.exInterface.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleEss;
import com.modern.exInterface.entity.VehicleEssTemperature;
import com.modern.exInterface.entity.VehicleGps;
import com.modern.exInterface.mapper.VehicleEssTemperatureMapper;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.tsp.domain.TspEquipment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * vehicle_ess_temperature;可充电储能装置温度数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
public class VehicleEssTemperatureRepository extends ServicePlusImpl<VehicleEssTemperatureMapper, VehicleEssTemperature,VehicleEssTemperature> {

    public VehicleEssTemperature getByVin(String vin) {
        QueryWrapper<VehicleEssTemperature> ew = new QueryWrapper<>();
        ew.eq(VehicleEssTemperature.VIN,vin);
        ew.orderByDesc(VehicleGps.COLLECT_TIME);
        ew.last(" LIMIT " + 0 + "," + 1);
        List<VehicleEssTemperature> list = this.list(ew);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    public Wrapper<VehicleEssTemperature> getPageListEw(VehicleSearchVO vo) {
        QueryWrapper<VehicleEssTemperature> ew = new QueryWrapper<>();
//        ew.eq("t.deleted",0);
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like(Constants.JOIN_TABLE_PREFIX_T + VehicleEssTemperature.VIN, vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_C + "plate_code", vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_B + TspEquipment.SN, vo.getSearch()));
        ew.and(StringUtils.isNotNull(vo.getCollectStartTime()) && StringUtils.isNotNull(vo.getCollectEndTime()),
                q -> q.ge(Constants.JOIN_TABLE_PREFIX_T + VehicleEssTemperature.COLLECT_TIME, vo.getCollectStartTime())
                        .le(Constants.JOIN_TABLE_PREFIX_T + VehicleEssTemperature.COLLECT_TIME, vo.getCollectEndTime()));
        ew.orderByDesc(Constants.JOIN_TABLE_PREFIX_T + VehicleEssTemperature.COLLECT_TIME);
        return ew;
    }
}
