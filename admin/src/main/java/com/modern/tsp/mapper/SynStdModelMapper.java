package com.modern.tsp.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.tsp.synchronize.SynStdModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * TSP二级级车型开放同步 Mapper
 * @author WS
 * @since 2023-05-15
 */
@Mapper
public interface SynStdModelMapper extends BaseMapper<SynStdModel> {
}
