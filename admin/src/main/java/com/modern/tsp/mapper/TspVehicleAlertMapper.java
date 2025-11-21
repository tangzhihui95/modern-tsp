package com.modern.tsp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspVehicleAlert;
import com.modern.tsp.model.dto.TspVehicleAlertPageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 11:41
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
public interface TspVehicleAlertMapper extends BaseMapperPlus<TspVehicleAlert> {
    @Select(value = {
            "SELECT t.*,c.plate_code FROM tsp_vehicle_alert t " +
                    "LEFT JOIN tsp_vehicle a ON t.vin = a.vin " +
                    "LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id " +
                    "LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id  ${ew.customSqlSegment} "
    })
    IPage<TspVehicleAlertPageListDTO> getPageList(Page<TspVehicleAlertPageListDTO> page, @Param(Constants.WRAPPER) Wrapper<TspVehicleAlert> ew);
}
