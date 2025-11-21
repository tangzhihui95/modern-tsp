package com.modern.exInterface.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.HttpStatus;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.exInterface.entity.VehicleExtreme;
import com.modern.exInterface.mapper.VehicleExtremeMapper;
import com.modern.exInterface.model.dto.VehicleExtremeParsedDTO;
import com.modern.exInterface.model.vo.VehicleSearchVO;
import com.modern.exInterface.repository.VehicleExtremeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * vehicle_extreme;极值数据 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleExtremeService extends VehicleExtremeRepository {
    private final VehicleExtremeRepository vehicleExtremeRepository;
    private final VehicleExtremeMapper vehicleExtremeMapper;

    public PageInfo<VehicleExtremeParsedDTO> getPageList(VehicleSearchVO vo) {
        Wrapper<VehicleExtreme> ew = vehicleExtremeRepository.getPageListEw(vo);
        IPage<VehicleExtreme> pageList = vehicleExtremeMapper.getPageList(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        List<VehicleExtremeParsedDTO> dtoList = new ArrayList<>();
        for (VehicleExtreme record : pageList.getRecords()) {
            VehicleExtremeParsedDTO dto = VehicleExtremeParsedDTO.create(record);
            //TODO: dto == null
            dtoList.add(dto);
        }
        return PageInfo.of(dtoList, pageList.getCurrent(), pageList.getSize(), pageList.getTotal());
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

        List<VehicleExtremeParsedDTO> list = getPageList(vo).getList();
        if (list == null || list.size() == 0) {
            return AjaxResult.error(HttpStatus.NO_CONTENT,
                    vo.getSearch() + " 此时间段（" + vo.getCollectStartTime().format(LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS)
                            + " - " + vo.getCollectEndTime().format(LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS) + "）无数据！");
        }

        ExcelUtil<VehicleExtremeParsedDTO> util = new ExcelUtil<>(VehicleExtremeParsedDTO.class);
        return util.exportExcel(list, "_" + vo.getSearch() + "_极值数据_" + vo.getCollectStartTime().format(LocalDateUtils.FORMAT_YYYYMMDDHHMMSS)
                + "-" + vo.getCollectEndTime().format(LocalDateUtils.FORMAT_YYYYMMDDHHMMSS));
    }

}
