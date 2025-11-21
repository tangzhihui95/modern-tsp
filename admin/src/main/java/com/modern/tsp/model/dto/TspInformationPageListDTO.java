package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.TspInformationDataEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2022/10/13 20:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("信息发布 - 数据传输对象 - 分页列表")
public class TspInformationPageListDTO extends BaseDTO {

    @ApiModelProperty(value = "信息ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspInformationId;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String informationTitle;

    /**
     * 信息位
     */
    @ApiModelProperty("信息位")
    private Integer informationType;

    /**
     * 转中文
     */
    @ApiModelProperty("信息位")
    private String type;

    /**
     * 信息格式
     */
    @ApiModelProperty("信息格式")
    private TspInformationDataEnum informationModel;

    /**
     * 信息点击量
     */
    @ApiModelProperty("信息点击量")
    private Integer informationCount;

    /**
     * 状态
     */
    @ApiModelProperty("状态、0-待发布，1-发布中、2-已下线")
    private Integer informationStatus;

    /**
     * 转中文
     */
    @ApiModelProperty("信息位")
    private String status;

    /**
     * 有效期
     */
    @ApiModelProperty("有效期")
    private String term;

    @ApiModelProperty("是否立即发布")
    private Boolean whetherPublishNow;

    /**
     * 有效期限
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("有效期限")
    private LocalDateTime unloadTime;

    /**
     * 下线时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("下线时间")
    private LocalDateTime endTime;
}
