package com.modern.tsp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/25 9:39
 */
@Data
@ApiModel("车辆信息 - 请求对象 - 车辆在线数量统计")
public class TspVehicleOnlineDataVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @ApiModelProperty("开始时间")
    private LocalDate startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @ApiModelProperty("结束时间")
    private LocalDate endTime;


    @ApiModelProperty("统计方式1-按地区、2-按单车")
    private Integer dataType;


    @ApiModelProperty("车辆行驶里程查询方式 1-7天、2-半个月、3-一个月")
    private Integer timeState;

    @ApiModelProperty("省份")
    private String provinceValue;

    @ApiModelProperty("市")
    private String cityValue;

    @ApiModelProperty("区")
    private String areaValue;

    @ApiModelProperty("经销商")
    private String dealerName;

    @ApiModelProperty("经销商Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long dealerId;

    @ApiModelProperty("搜索关键字")
    private String search;

    @ApiModelProperty("vin集合")
    private List<String> vinList;
}
