package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.exInterface.entity.VehicleAlert;
import com.modern.exInterface.model.dto.VehicleAlertDTO;
import com.modern.exInterface.repository.VehicleAlertRepository;
import com.modern.tsp.domain.*;
import com.modern.tsp.enums.TspVehicleAlertStateEnum;
import com.modern.tsp.mapper.TspVehicleAlertMapper;
import com.modern.tsp.model.dto.TspVehicleAlertInfoDTO;
import com.modern.tsp.model.dto.TspVehicleAlertPageListDTO;
import com.modern.tsp.model.vo.TspVehicleAlertPageListVO;
import com.modern.tsp.model.vo.TspVehicleHandleStateVO;
import com.modern.tsp.repository.TspUserRepository;
import com.modern.tsp.repository.TspVehicleAlertRepository;
import com.modern.tsp.repository.TspVehicleLicenseRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 11:43
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleAlertService extends TspBaseService {
    private final TspVehicleAlertRepository tspVehicleAlertRepository;
    private final VehicleAlertRepository vehicleAlertRepository;
    private final TspVehicleLicenseRepository tspVehicleLicenseRepository;
    private final TspVehicleRepository tspVehicleRepository;
    private final TspUserRepository tspUserRepository;
    private final TspVehicleAlertMapper tspVehicleAlertMapper;
    public PageInfo<TspVehicleAlertPageListDTO> getPageList(TspVehicleAlertPageListVO vo) {
        Wrapper<TspVehicleAlert> ew = tspVehicleAlertRepository.getPageListEw(vo);
        IPage<TspVehicleAlertPageListDTO> page = tspVehicleAlertMapper.getPageList(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        return PageInfo.of(page);
    }

    public List<TspVehicleAlertPageListDTO> exportList(TspVehicleAlertPageListVO vo) {
        vo.setPageNum(1);
        vo.setPageSize(Integer.MAX_VALUE);
        return this.getPageList(vo).getList();
    }

    public TspVehicleAlertInfoDTO get(Long tspVehicleAlertId) {
        TspVehicleAlert vehicleAlert = tspVehicleAlertRepository.getById(tspVehicleAlertId);
        if (vehicleAlert == null){
            ErrorEnum.TSP_VEHICLE_ALERT_NULL_ERROR.throwErr();
        }
        // 根据vin和source查询历史报警
        List<VehicleAlert> source = vehicleAlertRepository.findByVinAndSource(vehicleAlert.getVin(), 1);
        ArrayList<VehicleAlertDTO> alertDTOS = new ArrayList<>();
        if (source.size() > 0){
            for (VehicleAlert alert : source) {
                VehicleAlertDTO dto = new VehicleAlertDTO();
                BeanUtils.copyProperties(alert,dto);
                alertDTOS.add(dto);
            }
        }
        TspVehicle vehicle = tspVehicleRepository.getByVin(vehicleAlert.getVin());
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(vehicle.getId());
        TspUser tspUser = tspUserRepository.getById(vehicle.getTspUserId());
        TspVehicleAlertInfoDTO dto = new TspVehicleAlertInfoDTO();
        BeanUtils.copyProperties(vehicleAlert,dto);
        dto.setAlertDTO(alertDTOS);
        dto.setRealName(tspUser == null ? null : tspUser.getRealName());
        dto.setPlateCode(license == null ? null : license.getPlateCode());
        return dto;
    }

    public void handleState(TspVehicleHandleStateVO vo) {
        TspVehicleAlert alert = tspVehicleAlertRepository.getById(vo.getTspVehicleAlertId());
        if (alert == null){
            ErrorEnum.TSP_VEHICLE_ALERT_NULL_ERROR.throwErr();
        }
        BeanUtils.copyProperties(vo,alert);
        alert.setState(TspVehicleAlertStateEnum.COMPLETE);
        tspVehicleAlertRepository.updateById(alert);
    }
}
