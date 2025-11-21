package com.modern.tsp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspUseVehicleRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.model.dto.TspUseVehicleRecordPageListDTO;
import com.modern.tsp.model.dto.TspVehiclePageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 摩登 - TSP - 车辆绑定记录 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
public interface TspUseVehicleRecordMapper extends BaseMapperPlus<TspUseVehicleRecord> {
//    SELECT mobile,real_name,id_card,update_by,update_time,remark FROM `tsp_use_vehicle_record`
//    WHERE tsp_user_id = 1561300347668443138;
    @Select("SELECT mobile,real_name,id_card,update_by,update_time,remark FROM tsp_use_vehicle_record ${ew.customSqlSegment}")
    IPage<TspUseVehicleRecordPageListDTO> getUseVehicleRecordPageList(Page<TspUseVehicleRecordPageListDTO> page, @Param(Constants.WRAPPER) QueryWrapper<TspUseVehicleRecord> ew);


//    SELECT COUNT(id) FROM tsp_use_vehicle_record
    @Select("SELECT COUNT(id) FROM tsp_use_vehicle_record")
    Integer getCount(@Param(Constants.WRAPPER) QueryWrapper<TspUseVehicleRecord> ew);
}
