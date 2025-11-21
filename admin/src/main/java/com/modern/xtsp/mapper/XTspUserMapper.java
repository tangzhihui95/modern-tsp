package com.modern.xtsp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.tsp.domain.TspUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface XTspUserMapper extends BaseMapper<TspUser> {
}
