package com.modern.tsp.abstracts.findData.data.impl;

import com.modern.tsp.abstracts.findData.data.find.TspVehicleBaseFindDataService;
import com.modern.tsp.mapper.TspVehicleMapper;
import com.modern.tsp.model.dto.TspVehicleVolumeDataDTO;
import com.modern.tsp.model.vo.TspVehicleVolumeDataVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/19 12:35
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleFindTwelveMonthService implements TspVehicleBaseFindDataService {
    private final TspVehicleMapper tspVehicleMapper;
    @Override
    public List<TspVehicleVolumeDataDTO> findVolumeData(TspVehicleVolumeDataVO vo) {
        return tspVehicleMapper.findVolumeDataMonth(12);
    }
}
