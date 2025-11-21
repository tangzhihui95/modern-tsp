package com.modern.tsp.mapper;

import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspModel;
import com.modern.tsp.domain.TspTag;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/17 17:47
 * @Version 1.0.0
 */
public interface TspModelMapper extends BaseMapperPlus<TspModel> {

    @Select({
            "SELECT tag_name AS 'label',tag_name AS 'value' FROM tsp_tag WHERE tag_type = #{tagType}"
    })
    List<Map<String, Object>> getLabel(Integer tagType);

    @Select({
            "SELECT * FROM tsp_model WHERE is_delete = 0 ORDER BY create_time DESC limit 5"
    })
    List<TspModel> getList();
}
