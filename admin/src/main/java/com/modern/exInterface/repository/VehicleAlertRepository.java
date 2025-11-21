package com.modern.exInterface.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleAlert;
import com.modern.exInterface.entity.VehicleGps;
import com.modern.exInterface.mapper.VehicleAlertMapper;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.tsp.model.vo.TspVehicleAlertDataVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * vehicle_alert;报警数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
public class VehicleAlertRepository extends ServicePlusImpl<VehicleAlertMapper, VehicleAlert, VehicleAlert> {

    public VehicleAlert getByVin(String vin) {
        QueryWrapper<VehicleAlert> ew = new QueryWrapper<>();
        ew.select(
                VehicleAlert.ID,
                VehicleAlert.VIN,
                VehicleAlert.GENERAL_ALARM_SIGN,
                VehicleAlert.DATA_TYPE,
                "MAX(collect_time) as collect_time",
                VehicleAlert.CREATE_TIME,
                VehicleAlert.ALERT_SOURCE,
                VehicleAlert.DRIVE_MOTOR_FAULT_CODES,
                VehicleAlert.DRIVE_MOTOR_TOTAL_FAULT,
                VehicleAlert.ESS_FAULT_CODES,
                VehicleAlert.MAX_ALARM_LEVEL,
                VehicleAlert.OTHER_FAULT_CODES,
                VehicleAlert.OTHER_TOTAL_FAULT
        );
        ew.eq(VehicleAlert.VIN,vin);
        return this.getOne(ew);
    }


    public List<VehicleAlert> findByVinAndSource(String vin, int source) {
        QueryWrapper<VehicleAlert> ew = new QueryWrapper<>();
        ew.eq(VehicleAlert.ALERT_SOURCE,source);
        ew.eq(VehicleAlert.VIN, vin);
        ew.orderByDesc(VehicleGps.COLLECT_TIME);
        return this.list(ew);
    }

    public Wrapper<VehicleAlert> getPageListEw(VehicleSearchVO vo) {
        QueryWrapper<VehicleAlert> ew = new QueryWrapper<>();
//        ew.eq("t.deleted",0);
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),e->
                e.eq(Constants.JOIN_TABLE_PREFIX_T + VehicleAlert.VIN, vo.getSearch())
                        .or()
                        .eq(Constants.JOIN_TABLE_PREFIX_C + "plate_code", vo.getSearch())
                        .or()
                        .eq(Constants.JOIN_TABLE_PREFIX_B + "sn", vo.getSearch())
        );
        //ew.and(StringUtils.isNotEmpty(vo.getSearch()),
        //        q -> q.eq(Constants.JOIN_TABLE_PREFIX_T + VehicleAlert.VIN, vo.getSearch())
        //                .or().eq(Constants.JOIN_TABLE_PREFIX_C + "plate_code", vo.getSearch())
        //                .or().eq(Constants.JOIN_TABLE_PREFIX_B + TspEquipment.SN, vo.getSearch()));
        //!=0 and   开始时间<=采集时间<=结束时间
        ew.ne(Constants.JOIN_TABLE_PREFIX_T + VehicleAlert.GENERAL_ALARM_SIGN, 0);
        ew.eq(Constants.JOIN_TABLE_PREFIX_T + VehicleAlert.DELETED,0);
        ew.eq(Constants.JOIN_TABLE_PREFIX_A+"is_delete",0);
        ew.ne(Constants.JOIN_TABLE_PREFIX_A+"state",5);
        ew.and(StringUtils.isNotNull(vo.getCollectStartTime()) && StringUtils.isNotNull(vo.getCollectEndTime()),
                q -> q.ge(Constants.JOIN_TABLE_PREFIX_T + VehicleAlert.COLLECT_TIME, vo.getCollectStartTime())
                        .le(Constants.JOIN_TABLE_PREFIX_T + VehicleAlert.COLLECT_TIME, vo.getCollectEndTime()));
        ew.orderByDesc(Constants.JOIN_TABLE_PREFIX_T + VehicleAlert.COLLECT_TIME);
        return ew;
    }

    public Integer countByAlert() {
        QueryWrapper<VehicleAlert> ew = new QueryWrapper<>();
        ew.ne(VehicleAlert.GENERAL_ALARM_SIGN,0);
        return this.count(ew);
    }
}
