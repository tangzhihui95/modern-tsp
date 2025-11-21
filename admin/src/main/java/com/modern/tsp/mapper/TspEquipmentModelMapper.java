package com.modern.tsp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspEquipmentModel;
import com.modern.tsp.model.dto.TspEquipmentModelPageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/15 11:51
 */
public interface TspEquipmentModelMapper extends BaseMapperPlus<TspEquipmentModel> {

    @Select({
            "SELECT t.id,t.model_name,t.suppliers,t.batch_number,a.name,a.is_terminal,a.extra_type FROM tsp_equipment_model t " +
                    "LEFT JOIN tsp_equipment_type a ON a.id = t.tsp_equipment_type_id ${ew.customSqlSegment}"
    })
    IPage<TspEquipmentModelPageListDTO> getPageList(Page<Object> of,@Param(Constants.WRAPPER) QueryWrapper<TspEquipmentModel> ew);

    @Select({
            "select * from tsp_equipment_model where id = #{id}"
    })
    TspEquipmentModel getByIdContainsDelete(@Param("id") Long tspEquipmentId);

}
