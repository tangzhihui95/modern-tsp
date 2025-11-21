package com.modern.tsp.move.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.common.core.domain.entity.SysUser;
import com.modern.tsp.domain.TspUser;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TspVehicleLicense;
import com.modern.tsp.move.entity.MoveAlertInfo;
import com.modern.tsp.move.entity.MoveAlertInfoForm;
import com.modern.tsp.move.mapper.MoveAlertInfoMapper;
import com.modern.tsp.repository.TspUserRepository;
import com.modern.tsp.repository.TspVehicleLicenseRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import com.modern.web.service.system.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MoveAlertInfoService extends ServiceImpl<MoveAlertInfoMapper, MoveAlertInfo> {

    private final TspVehicleRepository tspVehicleRepository;

    private final TspVehicleLicenseRepository vehicleLicenseRepository;

    private final TspUserRepository tspUserRepository;

    private final ISysUserService iSysUserService;

    @Override
    public int count() {
        return lambdaQuery().eq(MoveAlertInfo::getDealType,0).count();
    }

    public Page<MoveAlertInfo> queryPage(MoveAlertInfoForm moveAlertInfoForm) {
        String orderSQL = "";
        if(Objects.equals(moveAlertInfoForm.getDealType(),1)){
            orderSQL = "order by create_time,handle_time desc";
        }else{
            orderSQL = "order by create_time desc";
        }
        Page<MoveAlertInfo> page = lambdaQuery()
                .eq(Objects.nonNull(moveAlertInfoForm.getDealType()), MoveAlertInfo::getDealType, moveAlertInfoForm.getDealType())
                .last(orderSQL)
                .page(new Page<>(moveAlertInfoForm.getPage(), moveAlertInfoForm.getPageSize()));
        page.getRecords().forEach(item -> {
            if (!Objects.isNull(item.getHandleUser())) {
                SysUser sysUser = iSysUserService.selectUserById(item.getHandleUser());
                item.setHandlerUserName(sysUser.getUserName());
            }
            item.setDealTypeText(item.getDealType()==1?"已处理":"未处理");
            item.setIsMonitorText(item.getIsMonitor()==1?"监控":"未监控");
        });

        return page;
    }

    /**
     * 保存接口，tbox 发送报警信息时调用该方法，进行保存
     */
    public void baseSave(MoveAlertInfoForm moveAlertInfoForm) {
        moveAlertInfoForm.setDealType(0);
        moveAlertInfoForm.setErrorTime(LocalDateTime.now());
        moveAlertInfoForm.setId(null);
        TspVehicle byVin = tspVehicleRepository.getByVin(moveAlertInfoForm.getVin());
        moveAlertInfoForm.setCarColor(byVin.getColor());
        TspVehicleLicense byTspVehicleId = vehicleLicenseRepository.getByTspVehicleId(byVin.getId());
        moveAlertInfoForm.setCarNumber(byTspVehicleId.getPlateCode());
        TspUser byId = tspUserRepository.getById(byVin.getTspUserId());
        moveAlertInfoForm.setCarMaster(byId.getRealName());
        moveAlertInfoForm.setCarTel(byId.getMobile());
        moveAlertInfoForm.setIsMonitor(0);
        save(moveAlertInfoForm);
    }

    /**
     * 监听实时修改 车辆经纬度信息
     */
    public void baseUpdate(MoveAlertInfoForm moveAlertInfoForm) {
        lambdaUpdate()
                .set(MoveAlertInfo::getLatitude, moveAlertInfoForm.getLatitude())
                .set(MoveAlertInfo::getLongitude, moveAlertInfoForm.getLongitude())
                .eq(MoveAlertInfo::getId, moveAlertInfoForm.getId())
                .update();
    }

}
