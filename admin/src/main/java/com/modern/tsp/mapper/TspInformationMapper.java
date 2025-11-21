package com.modern.tsp.mapper;

import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspInformation;
import com.modern.tsp.model.dto.TspInformationPageListDTO;
import com.modern.tsp.model.vo.TspInformationPageListVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TspInformationMapper extends BaseMapperPlus<TspInformation> {

    @Select({
            "SELECT * FROM tsp_information WHERE is_delete = 0 AND information_status != 2"
    })
    List<TspInformation> getAllInformation();

    List<TspInformationPageListDTO> selectUserList(TspInformationPageListVO vo);
}
