package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 11:14
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("经销商管理 - 数据传输对象 - 分页列表")
public class TspDealerPageListDTO extends BaseDTO {

    @ApiModelProperty(value = "经销商主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspDealerId;

    /**
     * 经销商名称
     */
    @Column(comment = "经销商名称",type = MySqlTypeConstant.VARCHAR,length = 50,isNull = false)
    private String dealerName;

    /**
     * 经销商编码
     */
    @Column(comment = "经销商编码",type = MySqlTypeConstant.VARCHAR,length = 50,isNull = false)
    private String dealerCode;

    /**
     * 经销商电话
     */
    @Column(comment = "经销商电话",type = MySqlTypeConstant.VARCHAR,length = 22,isNull = false)
    private String dealerPhone;

    /**
     * 经销商地址
     */
    @Column(comment = "经销商地址",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private String dealerAddress;
}
