package com.modern.tsp.service;

import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.tsp.domain.TspVehicleStdModelExtra;
import com.modern.tsp.mapper.TspVehicleMapper;
import com.modern.tsp.mapper.TspVehicleStdModelExtraMapper;
import com.modern.tsp.model.vo.TspVehicleStdModelExtraAddVO;
import com.modern.tsp.repository.TspVehicleStdModelExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/1 11:25
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public class TspVehicleStdModelExtraService extends TspBaseService {
    @Autowired
    private TspVehicleStdModelExtraRepository tspVehicleStdModelExtraRepository;

    public void add(TspVehicleStdModelExtraAddVO extraAddVO) {
        TspVehicleStdModelExtra extra = new TspVehicleStdModelExtra();
        BeanUtils.copyProperties(extraAddVO,extra);
        extra.setCreateBy(SecurityUtils.getUsername());
        extra.setUpdateBy(SecurityUtils.getUsername());
        tspVehicleStdModelExtraRepository.save(extra);
    }


    public void edit(TspVehicleStdModelExtraAddVO extraAddVO) {
        TspVehicleStdModelExtra extra = tspVehicleStdModelExtraRepository.getByTspStdModelId(extraAddVO.getTspVehicleStdModelId());
        BeanUtils.copyProperties(extraAddVO,extra);
        extra.setUpdateBy(SecurityUtils.getUsername());
        tspVehicleStdModelExtraRepository.updateById(extra);
    }
}
