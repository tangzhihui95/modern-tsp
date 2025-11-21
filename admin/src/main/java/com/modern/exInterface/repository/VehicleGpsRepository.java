package com.modern.exInterface.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleExtreme;
import com.modern.exInterface.entity.VehicleGps;
import com.modern.exInterface.mapper.VehicleGpsMapper;
import com.modern.exInterface.model.dto.VehicleGpsSelectListDTO;
import com.modern.exInterface.model.vo.VehicleGpsHistoryVO;
import com.modern.tsp.domain.TspEquipment;
import com.modern.tsp.domain.TspUser;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TspVehicleLicense;
import com.modern.tsp.repository.TspVehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * vehicle_gps;车辆位置数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleGpsRepository extends ServicePlusImpl<VehicleGpsMapper, VehicleGps, VehicleGps> {
    private final VehicleGpsMapper vehicleGpsMapper;

//    public VehicleGps getByVin(String vin) {
//        QueryWrapper<VehicleGps> ew = new QueryWrapper<>();
//        ew.eq(VehicleGps.VIN, vin);
//        ew.eq(VehicleGps.GPS_TYPE,0);
//        ew.eq(VehicleGps.DELETED, Constants.DEL_NO);
//        ew.orderByDesc(VehicleGps.COLLECT_TIME);
//        ew.last(" LIMIT " + 0 + "," + 1);
//        List<VehicleGps> list = this.list(ew);
//        if (list.size() > 0){
//            return list.get(0);
//        }
//        return null;
//    }

    public List<VehicleGpsSelectListDTO> selectList(String plateCode) {
        List<VehicleGpsSelectListDTO> dtos = new ArrayList<>();
        QueryWrapper<VehicleGps> ew = new QueryWrapper<>();
        this.list(ew).forEach(item -> {
            VehicleGpsSelectListDTO dto = new VehicleGpsSelectListDTO();
            BeanUtils.copyProperties(item, dto);
            dtos.add(dto);
        });
        return dtos;
    }


    public Wrapper<VehicleGps> findAllByMaxCollectTimeEw(){
        QueryWrapper<VehicleGps> ew = new QueryWrapper<>();
        ew.eq(Constants.JOIN_TABLE_PREFIX_A + VehicleGps.GPS_TYPE,0);
        ew.orderByDesc("collect_time");
        return ew;
    }

    public Wrapper<VehicleGps> findHistoryEw(VehicleGpsHistoryVO vo) {
        QueryWrapper<VehicleGps> ew = new QueryWrapper<>();
        ew.eq(Constants.JOIN_TABLE_PREFIX_T + VehicleGps.GPS_TYPE,0);
        ew.and(StringUtils.isNotEmpty(vo.getSearch()), q ->
                q.like(Constants.JOIN_TABLE_PREFIX_T + VehicleGps.VIN, vo.getSearch())
                        .or()
                        .like(Constants.JOIN_TABLE_PREFIX_B + TspEquipment.SN, vo.getSearch())
                        .or()
                        .like(Constants.JOIN_TABLE_PREFIX_C + "plate_code", vo.getSearch()));
        if (StringUtils.isNotNull(vo.getCollectStartTime()) && StringUtils.isNotNull(vo.getCollectEndTime())){
            ew.and(q -> q.ge(Constants.JOIN_TABLE_PREFIX_T + VehicleGps.COLLECT_TIME, vo.getCollectStartTime())
                    .le(Constants.JOIN_TABLE_PREFIX_T + VehicleGps.COLLECT_TIME, vo.getCollectEndTime()));
        }
//        else {
//            String startTime = LocalDate.now() + Constants.TIME;
//            String endTime = LocalDate.now() + Constants.LAST_TIME;
//            // 默认显示当天的经纬度轨迹
//            ew.and(q -> q.ge(Constants.JOIN_TABLE_PREFIX_T + VehicleGps.COLLECT_TIME, startTime)
//                    .le(Constants.JOIN_TABLE_PREFIX_T + VehicleGps.COLLECT_TIME, endTime));
//        }
        ew.orderByAsc(Constants.JOIN_TABLE_PREFIX_T + VehicleGps.COLLECT_TIME);
        return ew;
    }

    public VehicleGps getByVinAndMaxCollectTime(String vin) {
        QueryWrapper<VehicleGps> ew = new QueryWrapper<>();
        ew.select(VehicleGps.ID,
                VehicleGps.LATITUDE,
                VehicleGps.LONGITUDE,
                VehicleGps.VIN,
                VehicleGps.COLLECT_TIME,
                VehicleGps.DATA_TYPE,
                VehicleGps.GPS_TYPE,
                "max(collect_time) collect_time");
        ew.eq(VehicleGps.GPS_TYPE,0);
        ew.eq(VehicleGps.VIN,vin);
        ew.orderByDesc(VehicleGps.ID,VehicleGps.COLLECT_TIME);
        return this.getOne(ew);
    }
}
