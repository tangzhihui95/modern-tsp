package com.modern.exInterface.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/24 10:36
 * @Version 1.0.0
 */
@Data
public class VehicleAlertAddVO extends BaseVO {

    @ApiModelProperty(value = "报警主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long vehicleAlertId;

    /**
     * 处理详情
     */
    @Column(comment = "处理详情",type = MySqlTypeConstant.VARCHAR,length = 500)
    private String dealContent;

    /**
     * 报警处理时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dealTime;
}
