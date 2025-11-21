package com.modern.exInterface.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.core.domain.BaseExModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * vehicle_login;车辆登入
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_login")
public class VehicleLogin extends BaseExModel {

    private static final long serialVersionUID = 1L;

    /**
     * 登入时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    /**
     * 登入流水号;（每次登入，流水号加1。从1开始循环累加，最大值为65531，循环周期为天）
     */
    private Integer sequenceNumber;

    /**
     * SIM卡ICCID
     */
    private String iccid;

    /**
     * 可充电储能子系统n
     */
    private Integer essNumber;

    /**
     * 可充电储能系统编码长度m
     */
    private Integer essCodeLength;

    /**
     * 可充电储能系统编码n*m
     */
    private String essCodes;


    public static final String LOGIN_TIME = "login_time";

    public static final String SEQUENCE_NUMBER = "sequence_number";

    public static final String ICCID = "iccid";

    public static final String ESS_NUMBER = "ess_number";

    public static final String ESS_CODE_LENGTH = "ess_code_length";

    public static final String ESS_CODES = "ess_codes";

    public static final String DATA_TYPE = "data_type";


}
