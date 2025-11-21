package com.modern.tsp.mapper;

import com.modern.common.core.base.BaseMapperPlus;
import com.modern.tsp.domain.TspDealer;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 15:30
 * @Version 1.0.0
 */
public interface TspDealerMapper extends BaseMapperPlus<TspDealer> {
    @Select({
            "SELECT id,dealer_name as 'dealerName' FROM tsp_dealer WHERE is_delete = 0"
    })
    List<Map<String,String>> saleNameList();

    @Select({
            "SELECT dealer_address as 'dealerAddress' FROM tsp_dealer WHERE is_delete = 0 AND dealer_name = #{dealerName}"
    })
    Map<String, String> saleNameGetAddress(String dealerName);

    @Select({
            "select dealer_name as 'dealerName',id from tsp_dealer where is_delete = 0 and dealer_address like concat(#{address},'%')"
    })
    List<TspDealer> saleNameListByLikeAddress(String address);

}
