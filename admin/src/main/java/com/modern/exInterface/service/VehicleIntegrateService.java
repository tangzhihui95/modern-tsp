package com.modern.exInterface.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.HttpStatus;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.exInterface.entity.VehicleIntegrate;
import com.modern.exInterface.mapper.VehicleIntegrateMapper;
import com.modern.exInterface.model.dto.VehicleIntegrateParsedDTO;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.exInterface.repository.VehicleIntegrateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * vehicle_integrate;整车数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleIntegrateService extends VehicleIntegrateRepository {
    private final VehicleIntegrateRepository vehicleIntegrateRepository;
    private final VehicleIntegrateMapper vehicleIntegrateMapper;

    public PageInfo<VehicleIntegrateParsedDTO> getPageList(VehicleSearchVO vo) {
//        Wrapper<VehicleIntegrate> ew = vehicleIntegrateRepository.getPageListEw(vo);
//        IPage<VehicleIntegrate> pageList = vehicleIntegrateMapper.getPageList(Page.of(vo.getPageNum(), vo.getPageSize()),ew);
//        List<VehicleIntegrateParsedDTO> dtoList = new ArrayList<>();
//        for (VehicleIntegrate record : pageList.getRecords()) {
//            VehicleIntegrateParsedDTO dto = VehicleIntegrateParsedDTO.create(record);
//            //TODO: dto == null
//            dtoList.add(dto);
//        }
//        return PageInfo.of(dtoList, pageList.getCurrent(), pageList.getSize(), pageList.getTotal());
//    }

        Wrapper<VehicleIntegrate> ew = vehicleIntegrateRepository.getPageListEw(vo);
        IPage<VehicleIntegrate> pageList = vehicleIntegrateMapper.getPageList(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        HashMap<List<Long>, ArrayList<VehicleIntegrateParsedDTO>> hashMap = new HashMap<>();
        ArrayList<VehicleIntegrateParsedDTO> dtoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(pageList.getRecords())) {
            pageList.getRecords().forEach(item -> {
                VehicleIntegrateParsedDTO dto = VehicleIntegrateParsedDTO.create(item);
                //TODO: dto == null
                dtoList.add(dto);
            });
        }
        List<Long> pageId = dtoList.stream().map(VehicleIntegrateParsedDTO::getId).collect(Collectors.toList());
        hashMap.put(pageId, dtoList);
        return PageInfo.of(hashMap.get(pageId), pageList.getCurrent(), pageList.getSize(), pageList.getTotal());
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

        List<VehicleIntegrateParsedDTO> list = getPageList(vo).getList();
        if (list == null || list.size() == 0) {
            return AjaxResult.error(HttpStatus.NO_CONTENT,
                    vo.getSearch() + " 此时间段（" + vo.getCollectStartTime().format(LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS)
                            + " - " + vo.getCollectEndTime().format(LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS) + "）无数据！");
        }

        ExcelUtil<VehicleIntegrateParsedDTO> util = new ExcelUtil<>(VehicleIntegrateParsedDTO.class);
        return util.exportExcel(list, "_" + vo.getSearch() + "_整车数据_" + vo.getCollectStartTime().format(LocalDateUtils.FORMAT_YYYYMMDDHHMMSS)
                + "-" + vo.getCollectEndTime().format(LocalDateUtils.FORMAT_YYYYMMDDHHMMSS));
    }

    public List<VehicleIntegrate> findByDateAndVinList(String date, List<String> vinList){
        return vehicleIntegrateMapper.findByDateAndVinList(date,vinList);
    }

    public VehicleIntegrate findByBeforeDateAndEqVin(String date, String vin){
        return vehicleIntegrateMapper.findByBeforeDateAndEqVin(date,vin);
    }
}
