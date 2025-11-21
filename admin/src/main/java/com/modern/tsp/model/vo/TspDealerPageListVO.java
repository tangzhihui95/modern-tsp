package com.modern.tsp.model.vo;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 14:34
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("经销商 - 请求对象 - 分页列表")
public class TspDealerPageListVO extends BaseVO {

    /**
     * 经销商名称
     */
    @Column(comment = "经销商名称",type = MySqlTypeConstant.VARCHAR,length = 50,isNull = false)
    private String dealerName;

    /**
     * 经销商地址
     */
    @Column(comment = "经销商地址",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private String dealerAddress;

}
