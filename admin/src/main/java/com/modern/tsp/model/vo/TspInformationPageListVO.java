package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2022/10/14 20:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("通知推送 - 请求对象 - 分页列表")
public class TspInformationPageListVO extends BaseVO {

    @ApiModelProperty("消息状态")
    private Integer informationStatus;

    @ApiModelProperty("消息类型、1-即时消息、2-固定消息")
    private Integer informationType;

}
