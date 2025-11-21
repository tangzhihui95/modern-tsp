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
 * vehicle_logout;车辆登出
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_logout")
public class VehicleLogout extends BaseExModel {

    private static final long serialVersionUID = 1L;

    /**
     * 登出时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logoutTime;

    /**
     * 登出流水号;（登出流水号与当时登入流水号一致）
     */
    private Integer sequenceNumber;


    public static final String LOGOUT_TIME = "logout_time";

    public static final String SEQUENCE_NUMBER = "sequence_number";

    public static final String DATA_TYPE = "data_type";


}
