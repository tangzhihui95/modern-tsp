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
 * @date 2022/7/20 11:58
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleFindDataService implements TspVehicleBaseFindDataService {
    private final TspVehicleMapper tspVehicleMapper;
    @Override
    public List<TspVehicleVolumeDataDTO> findVolumeData(TspVehicleVolumeDataVO vo) {
        return tspVehicleMapper.findVolumeData();
    }
}
