package com.modern.tsp.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.annotation.Log;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.tsp.domain.TspEquipment;
import com.modern.tsp.domain.TspUser;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.enums.TspVehicleEnumCertificationState;
import com.modern.tsp.enums.TspVehicleSendEnum;
import com.modern.tsp.enums.TspVehicleStateEnum;
import com.modern.tsp.mapper.TspVehicleMapper;
import com.modern.tsp.model.dto.TspVehicleRelationMobileOptionDTO;
import com.modern.tsp.model.vo.*;
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
public class TspVehicleRepository extends ServicePlusImpl<TspVehicleMapper, TspVehicle, TspVehicle> {

    @Autowired
    private TspUserRepository tspUserRepository;

    @Autowired
    private TspVehicleMapper tspVehicleMapper;


    // 流量统计查询
    public QueryWrapper<TspVehicle> getFlowDataVehicleEw(TspVehicleFlowDataVO vo) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();

        ew.like(StringUtils.isNotEmpty(vo.getSearch()),
                Constants.JOIN_TABLE_PREFIX_T + TspVehicle.VIN,vo.getSearch());
        ew.eq(Constants.JOIN_TABLE_PREFIX_T + TspVehicle.IS_DELETE, 0);
        ew.ne(Constants.JOIN_TABLE_PREFIX_T+TspVehicle.STATE,5);
        return ew;

    }
    public List<TspVehicle> findByTspEquipmentId(Long tspEquipmentId) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.eq(TspVehicle.TSP_EQUIPMENT_ID, tspEquipmentId);
        return this.list(ew);
    }



    public QueryWrapper<TspVehicle> getPageListEw(TspVehiclePageListVO vo) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();

        ew.in(CollectionUtils.isNotEmpty(vo.getCarIds()),Constants.JOIN_TABLE_PREFIX_T+TspVehicle.ID,vo.getCarIds());

        ew.isNotNull(StringUtils.isNotNull(vo.getBindStatus()) && "1".equals(vo.getBindStatus()),
                Constants.JOIN_TABLE_PREFIX_T + "tsp_equipment_id");
        ew.isNull(StringUtils.isNotNull(vo.getBindStatus()) && "0".equals(vo.getBindStatus()),
                Constants.JOIN_TABLE_PREFIX_T + "tsp_equipment_id");

        //过滤条件：where t.is_delete = 0 （isDelete 1-删除 0-未删除） 想要删除成功 条件为is_delete=0
        ew.eq(Constants.JOIN_TABLE_PREFIX_T + TspVehicle.IS_DELETE, 0);

        //过滤条件：where t.state = #{state}   （车辆状态不为null 并且 不能是“0（全部）”）
        ew.eq(StringUtils.isNotNull(vo.getState()) && !vo.getState().equals(TspVehicleStateEnum.ALL),
                Constants.JOIN_TABLE_PREFIX_T + TspVehicle.STATE, vo.getState());
        //过滤条件：where t.certification_state = #{certificationState}  （认证状态不为null  并且  认证状态不能 “0（全部）”）
        ew.eq(StringUtils.isNotNull(vo.getCertificationState()) && !vo.getCertificationState().equals(TspVehicleEnumCertificationState.ALL),
                Constants.JOIN_TABLE_PREFIX_T + TspVehicle.CERTIFICATION_STATE, vo.getCertificationState());

//        ew.eq(String.valueOf(StringUtils.isNotNull(vo.getBindStatus())),vo.getBindStatus());

        //多条件模糊查询（不为空的前提）
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q1 -> q1.like(Constants.JOIN_TABLE_PREFIX_A + "std_mode_name", vo.getSearch())
                        .or()
                        .like(Constants.JOIN_TABLE_PREFIX_T + TspVehicle.VIN, vo.getSearch())
                        .or()
                        .like(Constants.JOIN_TABLE_PREFIX_E + "plate_code",vo.getSearch())
                        .or()
                        .like(Constants.JOIN_TABLE_PREFIX_C + "sn",vo.getSearch())
                        .or()
                        .like(Constants.JOIN_TABLE_PREFIX_C + "sim",vo.getSearch()));


        //过滤条件：where t.tsp_vehicle_std_model_id = #{tspVehicleStdModelId}  （车型id不为null）
        ew.eq(StringUtils.isNotNull(vo.getTspVehicleStdModelId()),
                Constants.JOIN_TABLE_PREFIX_T + TspVehicle.TSP_VEHICLE_STD_MODEL_ID, vo.getTspVehicleStdModelId());

        //过滤条件：where b.mobile = #{mobile} （关联账号是空  并且 不是”全部“）
        ew.eq(!StringUtils.isEmpty(vo.getMobile()) && !"全部".equals(vo.getMobile()),
                Constants.JOIN_TABLE_PREFIX_B + TspUser.MOBILE, vo.getMobile());

        //过滤条件：where t.send_status = #{sendStatus}  （推送状态不为空）
        ew.eq(StringUtils.isNotNull(vo.getSendStatus()) && !vo.getSendStatus().equals(TspVehicleSendEnum.ALL),
                Constants.JOIN_TABLE_PREFIX_T + "send_status",vo.getSendStatus());

        ew.orderByDesc(Constants.JOIN_TABLE_PREFIX_T + TspVehicle.CREATE_TIME);

        return ew;
    }

    public void isProgressCheck(TspVehicleAddVO vo) throws ServiceException {
        // 如果是销售信息则验证
        if (vo.getProgress() == 2) {
            if (StringUtils.isEmpty(vo.getPurchaser())) {
                throw new ServiceException("购买方名称不能为空");
            }
            if (StringUtils.isEmpty(vo.getVehicleIdCard())) {
                throw new ServiceException("身份证号不能为空");
            }
            if (vo.getPriceTax() == null) {
                throw new ServiceException("价税合计不能为空");
            }
//            if (StringUtils.isEmpty(vo.getSalesUnitName())) {
//                throw new ServiceException("销货单位名称不能为空");
//            }
            if (vo.getOperateDate() == null) {
                throw new ServiceException("开票日期不能为空");
            }
        }
    }

    public List<TspVehicle> getNotInStateScrapTimeLeCrtTime(Integer scrapped, Integer bound) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.notIn(TspVehicle.STATE, scrapped, bound);
        ew.le(TspVehicle.SCRAP_TIME, LocalDateUtils.getCurrentTime());
        return this.list(ew);
    }

    public void isUserCheck(TspVehicleAddVO vo) throws ServiceException {
        if (vo.getProgress() == 4) {
            if (StringUtils.isEmpty(vo.getMobile())) {
                throw new ServiceException("车主手机号不能为空");
            }
            if (StringUtils.isEmpty(vo.getRealName())) {
                throw new ServiceException("车主姓名不能为空");
            }
            if (StringUtils.isEmpty(vo.getIdCard())) {
                throw new ServiceException("身份证号不能为空");
            }
        }
    }

    public List<TspVehicleRelationMobileOptionDTO> relationMobileOption() {
        List<TspVehicleRelationMobileOptionDTO> dtos = new ArrayList<>();
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.eq(TspVehicle.STATE, TspVehicleStateEnum.BOUND);
        List<Long> longs = this.list(ew).stream().map(TspVehicle::getTspUserId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(longs)) {
            QueryWrapper<TspUser> userEw = new QueryWrapper<>();
            userEw.in(TspUser.ID, longs);
            TspVehicleRelationMobileOptionDTO allDto = new TspVehicleRelationMobileOptionDTO();
            allDto.setMobile("全部");
            dtos.add(allDto);
            for (TspUser tspUser : tspUserRepository.list(userEw)) {
                TspVehicleRelationMobileOptionDTO dto = new TspVehicleRelationMobileOptionDTO();
                BeanUtils.copyProperties(tspUser, dto);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    public TspVehicle getByVin(String vin) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.eq(TspVehicle.VIN, vin);
        return this.getOne(ew);
    }

    public Integer countByTspUserId(Long id) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.eq(TspVehicle.TSP_USER_ID, id);
        return this.count(ew);
    }

    public Integer countByTspVehicleStdModelId(Long tspVehicleStdModelId) {
        LambdaQueryWrapper<TspVehicle> ew = new LambdaQueryWrapper<>();
        ew.eq(TspVehicle::getTspVehicleStdModelId,tspVehicleStdModelId);
        return count(ew);
    }

    /**
     * 在线
     *
     * @return
     */
    public Integer countByOnLine() {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.eq(Constants.JOIN_TABLE_PREFIX_A + TspEquipment.IS_ONLINE, 1);
        return tspVehicleMapper.countByIsOnline(ew);
    }

    /**
     * 离线
     */
    public Integer countByOffLine() {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.eq(Constants.JOIN_TABLE_PREFIX_A + TspEquipment.IS_ONLINE, 0);
        return tspVehicleMapper.countByIsOnline(ew);
    }

    /**
     * 全部
     */
    public Integer countByAll() {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        return tspVehicleMapper.countByIsOnline(ew);
    }

    @Log
    public Wrapper<TspVehicle> findRangeDataAreaVehicle(TspVehicleRangeDataVO vo) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.select("DATE_FORMAT( a.create_time, '%Y-%m-%d' ) AS date,IFNULL(SUM( a.mileage ),0) AS mileageTotal,IFNULL(a.mileage,0) AS mileage ");
//        ew.eq(Constants.JOIN_TABLE_PREFIX_T + TspVehicle.IS_DELETE, Constants.DEL_NO);
        ew.ge( "a.mileage", 0);
//        ew.notIn(Constants.JOIN_TABLE_PREFIX_T + TspVehicle.STATE, 4,5);
        if (vo.getTimeState() != null) {
            String str = vo.getTimeState() == 1 ? "7 DAY" : vo.getTimeState() == 2 ? "15 DAY" : vo.getTimeState() == 3 ? "1 MONTH" : "";
            ew.apply("DATE_SUB( CURDATE( ), INTERVAL " + str + " ) <= DATE( a.create_time)");
        }
        ew.and(StringUtils.isNotNull(vo.getStartTime()) && StringUtils.isNotNull(vo.getEndTime()),
                q -> q.ge(Constants.JOIN_TABLE_PREFIX_A + "create_time", vo.getStartTime() + Constants.TIME)
                        .le(Constants.JOIN_TABLE_PREFIX_A + "create_time", vo.getEndTime() + Constants.LAST_TIME));
        ew.groupBy("date");
        return ew;
    }

    public Wrapper<VehicleIntegrate> findRangeDataSingleVehicle(TspVehicleRangeDataVO vo) {
        QueryWrapper<VehicleIntegrate> ew = new QueryWrapper<>();
        ew.select("DATE_FORMAT( a.create_time, '%Y-%m-%d' ) AS date,IFNULL(SUM( a.mileage ),0) AS mileageTotal,IFNULL(a.mileage,0) AS mileage ");
        ew.notIn(Constants.JOIN_TABLE_PREFIX_A + TspVehicle.STATE, 4,5);
        if (vo.getTimeState() != null) {
            String str = vo.getTimeState() == 1 ? "7 DAY" : vo.getTimeState() == 2 ? "15 DAY" : vo.getTimeState() == 3 ? "1 MONTH" : "";
            ew.apply("DATE_SUB( CURDATE( ), INTERVAL " + str + " ) <= DATE( a.create_time)");
        }
        ew.and(StringUtils.isNotEmpty(vo.getSearch()),
                q -> q.like(Constants.JOIN_TABLE_PREFIX_A + TspVehicle.VIN, vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_C + "plate_code", vo.getSearch())
                        .or().like(Constants.JOIN_TABLE_PREFIX_B + TspEquipment.SN, vo.getSearch()));
        ew.groupBy("date,mileage");
        return ew;
    }

    public List<TspVehicle> findByTspUserId(Long userId) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.eq(TspVehicle.TSP_USER_ID,userId);
        return this.list(ew);
    }

    public List<TspVehicle> findInTspEquipmentIds(Long[] tspEquipmentIds) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.in(TspVehicle.TSP_EQUIPMENT_ID, Arrays.asList(tspEquipmentIds));
        return this.list(ew);
    }

    public Wrapper<TspVehicle> selectListSnEw(String search) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.and(StringUtils.isNotEmpty(search), q -> q.like(Constants.JOIN_TABLE_PREFIX_C + TspEquipment.SN, search));
        return ew;
    }

    public Wrapper<TspVehicle> selectListVinEw(String search) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.and(StringUtils.isNotEmpty(search), q -> q.like(Constants.JOIN_TABLE_PREFIX_T + TspVehicle.VIN, search));
        return ew;
    }

    public Wrapper<TspVehicle> selectListPlateCodeEw(String search) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper<>();
        ew.and(StringUtils.isNotEmpty(search), q -> q.like(Constants.JOIN_TABLE_PREFIX_D + "plate_code", search));
        return ew;
    }
}
