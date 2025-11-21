package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseExModel;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * vehicle_identification_receive;实名认证-接收实名认证结果
 * </p>
 *
 * @author nut
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tsp_vehicle_identification_receive")
public class TspVehicleIdentificationReceive extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 请求标识
     */
    @Column(comment = "请求标识", type = MySqlTypeConstant.VARCHAR, length = 50,isNull = false)
    private String requestId;


    /**
     * 道路机动车辆生产企业编码
     */
    @Column(comment = "道路机动车辆生产企业编码", type = MySqlTypeConstant.VARCHAR, length = 50,isNull = false)
    private String code;

    /**
     * 实名状态
     */
    @Column(comment = "实名状态", type = MySqlTypeConstant.VARCHAR, length = 20,isNull = false)
    private String status;

    /**
     * 平台标识
     */
    @Column(comment = "平台标识", type = MySqlTypeConstant.VARCHAR, length = 50,isNull = false)
    private String platformId;


    /**
     * 签名值
     */
    @Column(comment = "签名值", type = MySqlTypeConstant.VARCHAR, length = 255,isNull = false)
    private String signNature;

    /**
     * 车联网卡认证详情
     */
    @Column(comment = "车联网卡认证详情", type = MySqlTypeConstant.VARCHAR, length = 500,isNull = false)
    private String cardAuthInfo;

    /**
     * vin号
     */
    @Column(comment = "vin号", type = MySqlTypeConstant.VARCHAR, length = 55,isNull = false)
    private String vin;

    /**
     * 同步时间
     */
    @Column(comment = "同步时间", type = MySqlTypeConstant.VARCHAR, length = 100,isNull = false)
    private String receiveTime;
}
