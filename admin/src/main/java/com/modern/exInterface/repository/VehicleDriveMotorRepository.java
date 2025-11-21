package com.modern.exInterface.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleAlert;
import com.modern.exInterface.entity.VehicleDriveMotor;
import com.modern.exInterface.entity.VehicleGps;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.exInterface.mapper.VehicleDriveMotorMapper;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.tsp.domain.TspEquipment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * vehicle_drive_motor;驱动电机数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
public class VehicleDriveMotorRepository extends ServicePlusImpl<VehicleDriveMotorMapper, VehicleDriveMotor,VehicleDriveMotor>  {

    public VehicleDriveMotor getByVin(String vin) {
        QueryWrapper<VehicleDriveMotor> ew = new QueryWrapper<>();
        ew.eq(VehicleDriveMotor.VIN,vin);
        ew.orderByDesc(VehicleGps.COLLECT_TIME);
        ew.last(" LIMIT " + 0 + "," + 1);
        List<VehicleDriveMotor> list = this.list(ew);
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public Wrapper<VehicleDriveMotor> getPageListEw(VehicleSearchVO vo) {
        QueryWrapper<VehicleDriveMotor> ew = new QueryWrapper<>();
//        ew.eq("t.deleted",0);
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like(Constants.JOIN_TABLE_PREFIX_T + VehicleDriveMotor.VIN, vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_C + "plate_code", vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_B + TspEquipment.SN, vo.getSearch()));
        ew.and(StringUtils.isNotNull(vo.getCollectStartTime()) && StringUtils.isNotNull(vo.getCollectEndTime()),
                q -> q.ge(Constants.JOIN_TABLE_PREFIX_T + VehicleDriveMotor.COLLECT_TIME, vo.getCollectStartTime())
                        .le(Constants.JOIN_TABLE_PREFIX_T + VehicleDriveMotor.COLLECT_TIME, vo.getCollectEndTime()));
        ew.orderByDesc(Constants.JOIN_TABLE_PREFIX_T + VehicleDriveMotor.COLLECT_TIME);
        return ew;
    }
}
