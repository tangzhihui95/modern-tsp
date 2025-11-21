package com.modern.exInterface.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleEssTemperature;
import com.modern.exInterface.entity.VehicleExtreme;
import com.modern.exInterface.entity.VehicleGps;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.exInterface.mapper.VehicleExtremeMapper;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.tsp.domain.TspEquipment;
import org.springframework.stereotype.Service;

/**
 * <p>
 * vehicle_extreme;极值数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
public class VehicleExtremeRepository extends ServicePlusImpl<VehicleExtremeMapper, VehicleExtreme,VehicleExtreme> {

    public VehicleExtreme getByVin(String vin) {
        QueryWrapper<VehicleExtreme> ew = new QueryWrapper<>();
        ew.eq(VehicleExtreme.VIN,vin);
        ew.orderByDesc(VehicleGps.COLLECT_TIME);
        ew.last(" LIMIT " + 0 + "," + 1);
        return this.list(ew).get(0);
    }

    public Wrapper<VehicleExtreme> getPageListEw(VehicleSearchVO vo) {
        QueryWrapper<VehicleExtreme> ew = new QueryWrapper<>();
//        ew.eq("t.deleted",0);
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like(Constants.JOIN_TABLE_PREFIX_T + VehicleExtreme.VIN, vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_C + "plate_code", vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_B + TspEquipment.SN, vo.getSearch()));
        ew.and(StringUtils.isNotNull(vo.getCollectStartTime()) && StringUtils.isNotNull(vo.getCollectEndTime()),
                q -> q.ge(Constants.JOIN_TABLE_PREFIX_T + VehicleExtreme.COLLECT_TIME, vo.getCollectStartTime())
                        .le(Constants.JOIN_TABLE_PREFIX_T + VehicleExtreme.COLLECT_TIME, vo.getCollectEndTime()));
        ew.orderByDesc(Constants.JOIN_TABLE_PREFIX_T + VehicleExtreme.COLLECT_TIME);
        return ew;
    }
}
