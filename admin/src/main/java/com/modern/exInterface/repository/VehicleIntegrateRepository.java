package com.modern.exInterface.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleDriveMotor;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.exInterface.mapper.VehicleIntegrateMapper;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * vehicle_integrate;整车数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
public class VehicleIntegrateRepository extends ServicePlusImpl<VehicleIntegrateMapper, VehicleIntegrate, VehicleIntegrate> {
//    @Autowired
//    private VehicleIntegrateMapper vehicleIntegrateMapper;
//    public VehicleIntegrate getByVin(String vin) {
//        QueryWrapper<VehicleIntegrate> ew = new QueryWrapper<>();
//        ew.eq(VehicleIntegrate.DELETED, Constants.DEL_NO);
//        ew.eq(VehicleIntegrate.VIN, vin);
//        ew.orderByDesc(VehicleGps.COLLECT_TIME);
//        ew.last(" LIMIT " + 0 + "," + 1);
//        return vehicleIntegrateMapper.findByVin(vin).get(0);
//        return this.list(ew).get(0);
//    }

    public Wrapper<VehicleIntegrate> getPageListEw(VehicleSearchVO vo) {
//        QueryWrapper<VehicleIntegrate> ew = new QueryWrapper<>();
//        ew.eq("t.collect_time % 10", 0);
//        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
//                q -> q.like(Constants.JOIN_TABLE_PREFIX_T + VehicleIntegrate.VIN, vo.getSearch())
//                        .or().like(Constants.JOIN_TABLE_PREFIX_C + "plate_code", vo.getSearch())
//                        .or().like(Constants.JOIN_TABLE_PREFIX_B + TspEquipment.SN, vo.getSearch()));
//        ew.and(StringUtils.isNotNull(vo.getCollectStartTime()) && StringUtils.isNotNull(vo.getCollectEndTime()),
//                q -> q.ge(Constants.JOIN_TABLE_PREFIX_T + VehicleIntegrate.COLLECT_TIME, vo.getCollectStartTime())
//                        .le(Constants.JOIN_TABLE_PREFIX_T + VehicleIntegrate.COLLECT_TIME, vo.getCollectEndTime()));
//        ew.orderByDesc(Constants.JOIN_TABLE_PREFIX_T + VehicleIntegrate.COLLECT_TIME);
        QueryWrapper<VehicleIntegrate> ew = new QueryWrapper<>();
        ew.eq("t.deleted",0);
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                        q-> q.like("t.vin",vo.getSearch())
                                .or()
                                .like("b.sn",vo.getSearch())
                                .or()
                                .like("c.plate_code",vo.getSearch()))
                                .between(Objects.nonNull(vo.getCollectStartTime())&&Objects.nonNull(vo.getCollectEndTime()),"t.collect_time",
                                        (vo.getCollectStartTime()),vo.getCollectEndTime());
        ew.orderByDesc(Constants.JOIN_TABLE_PREFIX_T + VehicleDriveMotor.COLLECT_TIME);
        return ew;
    }
}
