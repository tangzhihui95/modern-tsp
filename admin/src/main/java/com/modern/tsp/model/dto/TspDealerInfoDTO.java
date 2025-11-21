package com.modern.tsp.model.dto;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 11:13
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("经销商管理 - 数据传输对象 - 详情信息")
public class TspDealerInfoDTO  extends BaseDTO {

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
     * 经销商经纬度
     */
    @Column(comment = "经销商经纬度",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private Map<String,String> dealerLngLat;
}
