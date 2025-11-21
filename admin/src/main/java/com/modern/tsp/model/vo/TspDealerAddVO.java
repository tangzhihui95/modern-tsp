package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 14:30
 * @Version 1.0.0
 */
@Data
@ApiModel("经销商 - 请求对象 - 添加")
public class TspDealerAddVO extends BaseVO {

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
     * 经销商所在省
     */
    @Column(comment = "经销商所在省",type = MySqlTypeConstant.VARCHAR,length = 64,isNull = false)
    private String dealerProvince;

    /**
     * 经销商所在市
     */
    @Column(comment = "经销商所在市",type = MySqlTypeConstant.VARCHAR,length = 64,isNull = false)
    private String dealerCity;

    /**
     * 经销商所在区
     */
    @Column(comment = "经销商所在区",type = MySqlTypeConstant.VARCHAR,length = 64,isNull = false)
    private String dealerDistrict;

    /**
     * 经销商所在街道地址
     */
    @Column(comment = "经销商所在街道地址",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private String dealerStreetAndStreetNumber;

    /**
     * 经销商地址
     */
    @Column(comment = "经销商地址",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private String dealerAddress;

    /**
     * 经销商纬度
     */
    @Column(comment = "经销商纬度",type = MySqlTypeConstant.VARCHAR,length = 50,isNull = false)
    private String dealerLat;

    /**
     * 经销商经度
     */
    @Column(comment = "经销商经度",type = MySqlTypeConstant.VARCHAR,length = 50,isNull = false)
    private String dealerLng;
}
