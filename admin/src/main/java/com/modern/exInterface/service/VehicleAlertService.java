package com.modern.exInterface.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.DataConstant;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.constant.HttpStatus;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.exInterface.entity.VehicleAlert;
import com.modern.exInterface.mapper.VehicleAlertMapper;
import com.modern.exInterface.model.dto.VehicleAlertDTO;
import com.modern.exInterface.model.dto.VehicleAlertPageListDTO;
import com.modern.exInterface.model.dto.VehicleAlertParsedDTO;
import com.modern.exInterface.model.vo.VehicleAlertAddVO;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.exInterface.repository.VehicleAlertRepository;
import com.modern.tsp.domain.TspUser;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TspVehicleLicense;
import com.modern.tsp.repository.TspUserRepository;
import com.modern.tsp.repository.TspVehicleLicenseRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * vehicle_alert;报警数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleAlertService extends VehicleAlertRepository {
    private final VehicleAlertRepository vehicleAlertRepository;
    private final VehicleAlertMapper vehicleAlertMapper;
    private final TspVehicleRepository tspVehicleRepository;
    private final TspVehicleLicenseRepository tspVehicleLicenseRepository;
    private final TspUserRepository tspUserRepository;

    public PageInfo<VehicleAlertParsedDTO> getPageList(VehicleSearchVO vo) {
        Wrapper<VehicleAlert> ew = vehicleAlertRepository.getPageListEw(vo);
        IPage<VehicleAlert> pageList = vehicleAlertMapper.getPageList(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        List<VehicleAlertParsedDTO> dtoList = new ArrayList<>();
        for (VehicleAlert record : pageList.getRecords()) {
            VehicleAlertParsedDTO dto = VehicleAlertParsedDTO.create(record);
            //TODO: dto == null
            dtoList.add(dto);
        }
        return PageInfo.of(dtoList, pageList.getCurrent(), pageList.getSize(), pageList.getTotal());
    }

    /**
     * 历史报警
     *
     * @param vo
     * @return
     */
    public PageInfo<VehicleAlertPageListDTO> getHistoryPageList(VehicleSearchVO vo) {
        Wrapper<VehicleAlert> ew = vehicleAlertRepository.getPageListEw(vo);
        IPage<VehicleAlertPageListDTO> pageList = vehicleAlertMapper.getHistoryPageList(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        for (VehicleAlertPageListDTO record : pageList.getRecords()) {
            String dataTypeStr = getDataTypeString(record.getDataType());
            record.setDataTypeStr(dataTypeStr);
        }
        return PageInfo.of(pageList);
    }

    public static String getDataTypeString(Integer dataType) {
        switch (dataType) {
            case 2:
                return DataConstant.nowData;
            case 3:
                return DataConstant.invalidData;
            default:
                return dataType.toString();
        }
    }

    /**
     * 历史报警详情
     *
     * @param vehicleAlertId
     * @return
     */
    public VehicleAlertDTO get(Long vehicleAlertId) {
        VehicleAlert vehicleAlert = vehicleAlertRepository.getById(vehicleAlertId);
        if (vehicleAlert == null) {
            ErrorEnum.TSP_ALERT_EVENT_NULL_ERROR.throwErr();
        }
        VehicleAlertDTO vehicleAlertDTO = new VehicleAlertDTO();
        BeanUtils.copyProperties(vehicleAlert, vehicleAlertDTO);
        // 车牌号
        TspVehicle vehicle = tspVehicleRepository.getByVin(vehicleAlert.getVin());
        if (vehicle != null) {
            TspVehicleLicense vehicleLicense = tspVehicleLicenseRepository.getByTspVehicleId(vehicle.getId());
            if (vehicleLicense != null) {
                vehicleAlertDTO.setPlateCode(vehicleLicense.getPlateCode());
            }
            // 车主真实姓名
            TspUser tspUser = tspUserRepository.getById(vehicle.getTspUserId());
            if (tspUser != null) {
                vehicleAlertDTO.setRealName(tspUser.getRealName());
            }
            List<Map<String, Integer>> alertDTOS = new ArrayList<>();
            Map<String, Integer> map = new HashMap<>();
            map.put("generalAlarmSign", vehicleAlert.getGeneralAlarmSign());
            alertDTOS.add(map);
            vehicleAlertDTO.setGeneralAlarmSigns(alertDTOS);
            String status = vehicleAlert.getDealTime() == null ? "未处理" : "已处理";
            vehicleAlertDTO.setStatus(status);
            vehicleAlertDTO.setVehicleAlertId(vehicleAlertId);
            return vehicleAlertDTO;
        }
        return null;
    }

    /**
     * 处理报警
     *
     * @param vo
     */
    public void deal(VehicleAlertAddVO vo) {
        VehicleAlert vehicleAlert = vehicleAlertRepository.getById(vo.getVehicleAlertId());
        if (vehicleAlert == null) {
            ErrorEnum.TSP_ALERT_EVENT_NULL_ERROR.throwErr();
        }
        BeanUtils.copyProperties(vo, vehicleAlert);
        vehicleAlertMapper.updateById(vehicleAlert);
    }

    public AjaxResult export(VehicleSearchVO vo) {
        //导出该时间段（最多24小时）所有数据
        final int MAX_INTERNAL_HOUR = 24;
        vo.setPageNum(1);
        vo.setPageSize(Integer.MAX_VALUE);
        if (vo.getCollectStartTime() == null || vo.getCollectEndTime() == null) {
            LocalDateTime now = LocalDateUtils.getCurrentTime();
            vo.setCollectEndTime(now);
            vo.setCollectStartTime(now.minusHours(MAX_INTERNAL_HOUR));
        } else {
            Duration duration = Duration.between(vo.getCollectStartTime(), vo.getCollectEndTime());
            if (duration.toHours() > MAX_INTERNAL_HOUR) {
                return AjaxResult.error(HttpStatus.NO_CONTENT, "时间间隔必须小于" + MAX_INTERNAL_HOUR + "小时");
            }
        }

        List<VehicleAlertParsedDTO> list = getPageList(vo).getList();
        if (list == null || list.size() == 0) {
            return AjaxResult.error(HttpStatus.NO_CONTENT,
                    vo.getSearch() + " 此时间段（" + vo.getCollectStartTime().format(LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS)
                            + " - " + vo.getCollectEndTime().format(LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS) + "）无数据！");
        }

        ExcelUtil<VehicleAlertParsedDTO> util = new ExcelUtil<>(VehicleAlertParsedDTO.class);
        return util.exportExcel(list, "_" + vo.getSearch() + "_报警数据_" + vo.getCollectStartTime().format(LocalDateUtils.FORMAT_YYYYMMDDHHMMSS)
                + "-" + vo.getCollectEndTime().format(LocalDateUtils.FORMAT_YYYYMMDDHHMMSS));
    }
}
