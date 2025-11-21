package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 11:00
 * @Version 1.0.0
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_dealer",comment = "摩登 - TSP - 经销商")
@Alias("TspDealer")
@TableName(value = "tsp_dealer", autoResultMap = true)
@Data
public class TspDealer extends BaseModel {

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
    @Column(comment = "经销商所在省",type = MySqlTypeConstant.VARCHAR,length = 63,isNull = false)
    private String dealerProvince;

    /**
     * 经销商所在市
     */
    @Column(comment = "经销商所在市",type = MySqlTypeConstant.VARCHAR,length = 63,isNull = false)
    private String dealerCity;

    /**
     * 经销商所在区
     */
    @Column(comment = "经销商所在区",type = MySqlTypeConstant.VARCHAR,length = 63,isNull = false)
    private String dealerDistrict;

    /**
     * 经销商所在街道地址
     */
    @Column(comment = "经销商所在街道地址",type = MySqlTypeConstant.VARCHAR,length = 510,isNull = false)
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
