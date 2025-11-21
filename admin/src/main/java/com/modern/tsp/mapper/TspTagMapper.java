package com.modern.tsp.mapper;

import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspFeedback;
import com.modern.tsp.domain.TspTag;
import com.modern.tsp.model.dto.TspTagLabelDTO;
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
public interface TspTagMapper  extends BaseMapperPlus<TspTag> {

    @Select({
            "SELECT tag_name AS 'label',id AS 'value' FROM tsp_tag WHERE is_delete = 0 AND tag_type = #{tagType}"
    })
    List<TspTagLabelDTO> getLabel(Integer tagType);
}
