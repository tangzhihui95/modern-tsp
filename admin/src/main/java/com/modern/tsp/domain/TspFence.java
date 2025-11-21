package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import com.modern.tsp.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 16:39
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_fence",comment = "摩登 - TSP - 电子围栏监控")
@Alias("TspFence")
@TableName(value = "tsp_fence", autoResultMap = true)
@Data
public class TspFence extends BaseModel {

    /**
     * 围栏名称
     */
    @Column(comment = "围栏名称",type = MySqlTypeConstant.VARCHAR,length = 50,isNull = false)
    private String fenceName;

    /**
     * 围栏区域
     */
    @Column(comment = "围栏区域",type = MySqlTypeConstant.VARCHAR,length = 50)
    private String fenceArea;

    /**
     * 围栏形状(1：圆型，2：多边形)
     */
    @Column(comment = "围栏形状(1：圆型，2：多边形，3：矩形)",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "1")
    private Integer fenceShape;

    /**
     * 围栏来源
     */
    @Column(comment = "围栏来源",type = MySqlTypeConstant.VARCHAR,length = 20)
    private String fenceSource;

    /**
     * 围栏状态(1：开启，2：关闭)
     */
    @Column(comment = "围栏状态(1：开启，2：关闭)",type = MySqlTypeConstant.INT)
    private TspFenceStatusEnum fenceStatus;

    /**
     * 规则：驶入提醒(1：开启，2：关闭)
     */
    @Column(comment = "规则：驶入提醒(1：开启，2：关闭)",type = MySqlTypeConstant.INT)
    private Integer fenceIn;

    /**
     * 规则：驶出提醒(1：开启，2：关闭)
     */
    @Column(comment = "规则：驶出提醒(1：开启，2：关闭)",type = MySqlTypeConstant.INT)
    private Integer fenceOut;

    /**
     * 监控日提醒次数
     */
    @Column(comment = "监控日提醒次数",type = MySqlTypeConstant.INT)
    private Integer fenceNum;

    /**
     * 监控日(1-星期一,2-星期二,3-星期三,4-星期四,5-星期五,6-星期六,7-星期日)
     */
    @Column(comment = "监控日",type = MySqlTypeConstant.VARCHAR,isNull = false,defaultValue = "1")
    private String fenceDay;

    /**
     * 监控时段
     */
    @Column(comment = "监控时段",type = MySqlTypeConstant.VARCHAR,isNull = false,defaultValue = "1")
    private String fenceTime;

    /**
     * 备注信息
     */
    @Column(comment = "备注信息",type = MySqlTypeConstant.VARCHAR,length = 200)
    private String fenceRemark;

    /**
     * 经度
     */
    @Column(comment = "经度",type = MySqlTypeConstant.VARCHAR,length = 50)
    private String lng;

    /**
     * 纬度
     */
    @Column(comment = "纬度",type = MySqlTypeConstant.VARCHAR,length = 50)
    private String lat;

    /**
     * 多边形经纬度
     */
    @Column(comment = "多边形经纬度",type = MySqlTypeConstant.VARCHAR,length = 500)
    private String lngLatList;

    /**
     * 推送渠道
     */
    @Column(comment = "推送渠道",type = MySqlTypeConstant.VARCHAR,length = 50)
    private String sendChannel;
}
