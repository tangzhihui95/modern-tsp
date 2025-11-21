package com.modern.exInterface.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.exInterface.entity.VehicleLogin;
import com.modern.exInterface.mapper.VehicleLoginMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * vehicle_login;车辆登入 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
public class VehicleLoginRepository extends ServicePlusImpl<VehicleLoginMapper, VehicleLogin,VehicleLogin> {

    public VehicleLogin getByVin(String vin) {
        QueryWrapper<VehicleLogin> ew = new QueryWrapper<>();
        ew.select(
                VehicleLogin.ID,
                VehicleLogin.VIN,
                VehicleLogin.LOGIN_TIME,
                VehicleLogin.ESS_CODE_LENGTH,
                VehicleLogin.ESS_CODES,
                VehicleLogin.ESS_NUMBER,
                VehicleLogin.DATA_TYPE,
                "MAX(collect_time) as collect_time",
                VehicleLogin.CREATE_TIME,
                VehicleLogin.SEQUENCE_NUMBER,
                VehicleLogin.ICCID
        );
        ew.eq(VehicleLogin.VIN,vin);
        return this.getOne(ew);
    }
}
