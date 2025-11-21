package com.modern.tsp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspVehicleModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.tsp.model.dto.TspVehicleModelPageListDTO;
import com.modern.tsp.model.dto.TspVehiclePageListDTO;
import com.modern.tsp.model.dto.TspVehicleStdModelExListDTO;
import com.modern.tsp.model.dto.TspVehicleStdModelExportListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 摩登 - TSP - 车辆车型 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
public interface TspVehicleModelMapper extends BaseMapperPlus<TspVehicleModel> {

    @Select({
        "SELECT t.std_mode_name,t.data_key,t.notice_batch,t.notice_model,t.id as 'tspStdModelId', a.vehicle_model_name " +
                "FROM tsp_vehicle_std_model t " +
                "LEFT JOIN tsp_vehicle_model a ON a.id = t.tsp_vehicle_model_id ${ew.customSqlSegment}"
    })
    IPage<TspVehicleStdModelExListDTO> getPageList(Page<Object> of,@Param(Constants.WRAPPER) QueryWrapper<TspVehicleModel> ew);
}
