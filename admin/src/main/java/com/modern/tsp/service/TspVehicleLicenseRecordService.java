package com.modern.tsp.service;

import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspVehicleLicenseRecord;
import com.modern.tsp.model.vo.TspVehicleLicenseRecordAddVO;
import com.modern.tsp.repository.TspVehicleLicenseRecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 * 摩登 - TSP - 车辆上牌操作记录 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Service
public class TspVehicleLicenseRecordService extends TspBaseService {

    @Autowired
    private TspVehicleLicenseRecordRepository tspVehicleLicenseRecordRepository;

    public void add(TspVehicleLicenseRecordAddVO recordAddVO) {
        TspVehicleLicenseRecord upPlateRecord = new TspVehicleLicenseRecord();
        BeanUtils.copyProperties(recordAddVO,upPlateRecord);
        upPlateRecord.setCreateBy(SecurityUtils.getUsername());
        upPlateRecord.setUpdateBy(SecurityUtils.getUsername());
        upPlateRecord.setVersion(1);
        upPlateRecord.setUpPlaceDate(LocalDate.now());
        upPlateRecord.setPlateCode(recordAddVO.getPlateCodeName() + recordAddVO.getPlateCode());
        tspVehicleLicenseRecordRepository.save(upPlateRecord);
    }

}
