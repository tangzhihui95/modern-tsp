package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspEquipment;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TspVehicleEquipment;
import com.modern.tsp.enums.TspVehicleStateEnum;
import com.modern.tsp.mapper.TspEquipmentMapper;
import com.modern.tsp.model.dto.TspEquipmentExcelDTO;
import com.modern.tsp.model.vo.TspEquipmentPageListVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 15:04
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspEquipmentRepository extends ServicePlusImpl<TspEquipmentMapper,TspEquipment,TspEquipment> {

    public QueryWrapper<TspEquipment> getPageListEw(TspEquipmentPageListVO vo) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper<>();

        // 未删除 where t.is_delete = 0
        ew.eq(Constants.JOIN_TABLE_PREFIX_T + TspEquipment.IS_DELETE, 0);

        ew.isNotNull(StringUtils.isNotNull(vo.getBindStatus()) && "1".equals(vo.getBindStatus()),
                 "tv.tsp_equipment_id");
        ew.isNull(StringUtils.isNotNull(vo.getBindStatus()) && "0".equals(vo.getBindStatus()),
                "tv.id");
        ew.eq(StringUtils.isNotNull(vo.getTspEquipmentModelId()),
                Constants.JOIN_TABLE_PREFIX_T + TspEquipment.TSP_EQUIPMENT_MODEL_ID,vo.getTspEquipmentModelId());

        //分开实现sn 、sim查询 以备后期修改
//        ew.like(StringUtils.isNotEmpty(vo.getSn()),Constants.JOIN_TABLE_PREFIX_T + TspEquipment.SN,vo.getSn());
//        ew.like(StringUtils.isNotEmpty(vo.getSim()),Constants.JOIN_TABLE_PREFIX_T + TspEquipment.SIM,vo.getSim());

        // 合并实现模糊查询 sn、SIM的功能
        ew.and(StringUtils.isNotEmpty(vo.getSn()),
                        q1 -> q1.like(Constants.JOIN_TABLE_PREFIX_T + TspEquipment.SN, vo.getSn())
                                .or()
                                .like(Constants.JOIN_TABLE_PREFIX_T + TspEquipment.SIM,vo.getSn())
                                .or()
                                .like(Constants.JOIN_TABLE_PREFIX_T + TspEquipment.ICCID,vo.getSn())
        );

        ew.orderByDesc(Constants.JOIN_TABLE_PREFIX_T + TspEquipment.ID);

        return ew;
    }

    public TspEquipment getByName(String sn) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper<>();
        ew.eq(TspEquipment.SN,sn);
        return this.getOne(ew);
    }

    public QueryWrapper<TspEquipment> getNowEquipment(Long tspEquipmentId) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper<>();
        ew.eq("b.id",tspEquipmentId);
        return ew;
    }

    public TspEquipment getBySn(String sn) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper<>();
        ew.eq(!StringUtils.isEmpty(sn),TspEquipment.SN,sn);
        return this.getOne(ew);
    }

    public TspEquipment getByICCID(String iccid) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper<>();
        ew.eq(!StringUtils.isEmpty(iccid),TspEquipment.ICCID,iccid);
        return this.getOne(ew);
    }

    public TspEquipment getByIMEI(String imei) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper<>();
        ew.eq(!StringUtils.isEmpty(imei),TspEquipment.IMEI,imei);
        return this.getOne(ew);
    }

    public TspEquipment getByIMSI(String imsi) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper<>();
        ew.eq(!StringUtils.isEmpty(imsi),TspEquipment.IMSI,imsi);
        return this.getOne(ew);
    }

    public TspEquipment getBySIM(String sim) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper<>();
        ew.eq(!StringUtils.isEmpty(sim),TspEquipment.SIM,sim);
        return this.getOne(ew);
    }

    public TspEquipment getByDTO(TspEquipmentExcelDTO dto) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper<>();
        ew.eq(!StringUtils.isEmpty(dto.getSn()),TspEquipment.SN,dto.getSn());
        ew.eq(!StringUtils.isEmpty(dto.getIccId()),TspEquipment.ICCID,dto.getIccId());
        ew.eq(!StringUtils.isEmpty(dto.getImsi()),TspEquipment.IMSI,dto.getImsi());
        ew.eq(!StringUtils.isEmpty(dto.getSim()),TspEquipment.SIM,dto.getSim());
        ew.eq(!StringUtils.isEmpty(dto.getImei()),TspEquipment.IMEI,dto.getImei());
        return this.getOne(ew);
    }
}
