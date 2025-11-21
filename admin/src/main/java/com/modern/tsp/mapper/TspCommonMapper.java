package com.modern.tsp.mapper;

import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspFence;
import com.modern.tsp.domain.TspVehicle;
import org.apache.ibatis.annotations.Select;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 17:54
 * @Version 1.0.0
 */
public interface TspCommonMapper extends BaseMapperPlus<TspVehicle> {

    @Select({
            "select DISTINCT a.vin from tsp_vehicle a " +
                    "left join tsp_equipment b on a.tsp_equipment_id = b.id " +
                    "left join tsp_vehicle_license c on a.id = c.tsp_vehicle_id " +
                    "where a.vin like #{search} " +
                    "or b.sn like #{search} " +
                    "or c.plate_code like #{search} " +
                    "and a.is_delete = 0 limit 1"
    })
    String pasToVin(String search);
}
