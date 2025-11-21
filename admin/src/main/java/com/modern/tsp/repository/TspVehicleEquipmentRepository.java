package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.tsp.domain.*;
import com.modern.tsp.enums.TspVehicleEnumCertificationState;
import com.modern.tsp.enums.TspVehicleStateEnum;
import com.modern.tsp.mapper.TspVehicleEquipmentMapper;
import com.modern.tsp.mapper.TspVehicleMapper;
import com.modern.tsp.model.dto.TspVehicleRelationMobileOptionDTO;
import com.modern.tsp.model.vo.TspVehicleAddVO;
import com.modern.tsp.model.vo.TspVehicleOnlineDataVO;
import com.modern.tsp.model.vo.TspVehiclePageListVO;
import com.modern.tsp.model.vo.TspVehicleRangeDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 15:17
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspVehicleEquipmentRepository extends ServicePlusImpl<TspVehicleEquipmentMapper, TspVehicleEquipment, TspVehicleEquipment> {



    public List<TspVehicleEquipment> getByVehicleId(Long id) {
        QueryWrapper<TspVehicleEquipment> ew = new QueryWrapper<>();
        ew.eq("tsp_vehicle_id",id);
        ew.orderByDesc(TspVehicleEquipment.CREATE_TIME);
        return this.list(ew);
    }

    public List<TspVehicleEquipment> getByVehicleIdSend(Long id) {
        QueryWrapper<TspVehicleEquipment> ew = new QueryWrapper<>();
        ew.eq("tsp_vehicle_id",id);
        ew.isNotNull("upload_time");
        ew.orderByDesc(TspVehicleEquipment.CREATE_TIME);
        return this.list(ew);
    }

    public List<TspVehicleEquipment> getByEquipmentId(Long vehicleId,Long equipmentId) {
        QueryWrapper<TspVehicleEquipment> ew = new QueryWrapper<>();
        ew.eq("tsp_equipment_id",equipmentId);
        ew.eq("tsp_vehicle_id",vehicleId);
        ew.orderByDesc(TspVehicleEquipment.CREATE_TIME);
        return this.list(ew);
    }

    public QueryWrapper<TspVehicleEquipment> getPageListEw(Long tspVehicleId) {
        QueryWrapper<TspVehicleEquipment> ew = new QueryWrapper<>();
        ew.eq("a.tsp_vehicle_id",tspVehicleId);
        ew.orderByDesc(TspVehicleEquipment.CREATE_TIME);
        return ew;
    }
}
