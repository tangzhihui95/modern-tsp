package com.modern.tsp.mapper;

import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspEquipmentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 摩登 - TSP - 设备分类 Mapper 接口
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
public interface TspEquipmentTypeMapper extends BaseMapperPlus<TspEquipmentType> {

    @Select({
            "SELECT count(t.id) FROM tsp_equipment t " +
                    "LEFT JOIN tsp_equipment_model a on a.id = t.tsp_equipment_model_id " +
                    "WHERE t.is_delete = 0 " +
                    "AND a.is_delete = 0 " +
                    "AND a.tsp_equipment_type_id = #{tspEquipmentTypeId}"
    })
    int countByTspEquipmentTypeId(Long tspEquipmentTypeId);

    @Select({
            "SELECT count(t.id) FROM tsp_equipment t " +
                    "LEFT JOIN tsp_equipment_model a on a.id = t.tsp_equipment_model_id " +
                    "WHERE t.is_delete = 0 " +
                    "AND a.is_delete = 0 " +
                    "AND a.id = #{tspEquipmentModelId}"
    })
    int countByTspEquipmentModelId(Long tspEquipmentModelId);
}
