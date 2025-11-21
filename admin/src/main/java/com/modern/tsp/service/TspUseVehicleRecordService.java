package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.Constants;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.core.domain.FrontQuery;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspUseVehicleRecord;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.mapper.TspUseVehicleRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.tsp.model.dto.TspUseVehicleRecordPageListDTO;
import com.modern.tsp.model.vo.TspUseVehicleRecordAddVO;
import com.modern.tsp.model.vo.TspUseVehicleRecordPageListVO;
import com.modern.tsp.repository.TspUseVehicleRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 摩登 - TSP - 车辆绑定记录 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspUseVehicleRecordService extends TspBaseService {

    private final TspUseVehicleRecordRepository tspUseVehicleRecordRepository;
    private final TspUseVehicleRecordMapper tspUseVehicleRecordMapper;

    public void add(TspUseVehicleRecordAddVO recordAddVO) {
        List<TspUseVehicleRecord> mobiles = tspUseVehicleRecordRepository.findByMobile(recordAddVO.getMobile());
        if (!CollectionUtils.isEmpty(mobiles)){
            // 修改历史版本号
            mobiles.stream().filter(a -> a.getVersion() == 1).forEach(b -> b.setVersion(0));
            tspUseVehicleRecordRepository.updateBatchById(mobiles);
        }
        TspUseVehicleRecord vehicleRecord = new TspUseVehicleRecord();
        vehicleRecord.setCreateBy(SecurityUtils.getUsername());
        vehicleRecord.setUpdateBy(SecurityUtils.getUsername());
        vehicleRecord.setVersion(1);
        BeanUtils.copyProperties(recordAddVO,vehicleRecord);
        tspUseVehicleRecordRepository.save(vehicleRecord);
    }

    public PageInfo<TspUseVehicleRecordPageListDTO> getPageList(TspUseVehicleRecordPageListVO frontQuery) {
        QueryWrapper<TspUseVehicleRecord> ew = new QueryWrapper<>();
        //如果这里是默认查询当前登录用户的车辆信息的话就是直接拿缓存的UserId
        ew.eq(StringUtils.isNotNull(frontQuery.getTspVehicleId()),TspUseVehicleRecord.TSP_VEHICLE_ID,frontQuery.getTspVehicleId());
        ew.eq(TspUseVehicleRecord.IS_DELETE,0);
        ew.eq("version",1);
        ew.orderByDesc("update_time");

        Integer count = tspUseVehicleRecordMapper.getCount(ew);
        if (count / 10 < frontQuery.getPageNum() - 1){
            frontQuery.setPageNum(1);
        }
        ArrayList<TspUseVehicleRecordPageListDTO> dtos = new ArrayList<>();
        IPage<TspUseVehicleRecordPageListDTO> page = tspUseVehicleRecordMapper.getUseVehicleRecordPageList(Page.of(frontQuery.getPageNum(),frontQuery.getPageSize()),ew);
        for (TspUseVehicleRecordPageListDTO record : page.getRecords()) {
            TspUseVehicleRecordPageListDTO dto = new TspUseVehicleRecordPageListDTO();
            BeanUtils.copyProperties(record,dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos,page.getCurrent(),page.getSize(),page.getTotal());
    }
}
