package com.modern.tsp.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
import com.modern.common.core.domain.BaseVO;
import com.modern.tsp.enums.*;
import com.modern.tsp.model.dto.TspVehiclePageListDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 16:39
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("电子围栏 - 请求对象 - 添加")
public class TspFenceAddVO extends BaseVO {

    @ApiModelProperty(value = "电子围栏主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspFenceId;

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
     * 围栏来源
     */
    @Column(comment = "围栏来源",type = MySqlTypeConstant.VARCHAR,length = 20)
    private String fenceSource;

    /**
     * 围栏形状(1：圆型，2：多边形)
     */
    @Column(comment = "围栏形状(1：圆型，2：多边形,3：矩形)",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "1")
    private Integer fenceShape;

    /**
     * 围栏状态(1：开启，2：关闭)
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
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
    @Column(comment = "监控日",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "1")
    private List<Integer> fenceDay;

    /**
     * 监控时段
     */
    @Column(comment = "监控时段",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "1")
    private List<Integer> fenceTime;

    /**
     * 备注信息
     */
    @Column(comment = "备注信息",type = MySqlTypeConstant.VARCHAR,length = 200)
    private String fenceRemark;

    /**
     * 车辆信息
     */
    private List<TspVehiclePageListDTO> vehicleList;

    /**
     * 推送渠道
     */
    private List<Integer> sendChannel;

    /**
     * 经纬度信息
     */
    private Object lngLatList;
}
