package com.modern.exInterface.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.exInterface.entity.VehicleLogin;
import com.modern.exInterface.entity.VehicleLogout;
import com.modern.exInterface.mapper.VehicleLogoutMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * vehicle_logout;车辆登出 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
public class VehicleLogoutRepository extends ServicePlusImpl<VehicleLogoutMapper, VehicleLogout, VehicleLogout> {

    public VehicleLogout getByVin(String vin) {
        QueryWrapper<VehicleLogout> ew = new QueryWrapper<>();
        ew.select(
                VehicleLogout.ID,
                VehicleLogout.VIN,
                VehicleLogout.LOGOUT_TIME,
                VehicleLogout.DATA_TYPE,
                "MAX(collect_time) as collect_time",
                VehicleLogout.CREATE_TIME,
                VehicleLogout.SEQUENCE_NUMBER
        );
        ew.eq(VehicleLogout.VIN,vin);
        return this.getOne(ew);
    }
}
