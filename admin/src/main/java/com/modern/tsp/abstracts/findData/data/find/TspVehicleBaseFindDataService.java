package com.modern.tsp.abstracts.findData.data.find;

import com.modern.tsp.model.dto.TspVehicleVolumeDataDTO;
import com.modern.tsp.model.vo.TspVehicleVolumeDataVO;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/19 11:58
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
public interface TspVehicleBaseFindDataService {

    public List<TspVehicleVolumeDataDTO> findVolumeData(TspVehicleVolumeDataVO vo);

}
