package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 17:49
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户 - 数据传输对象 - 分页列表")
public class TspUserPageListDTO extends BaseDTO {

    /**
     * 手机号(账号)
     */
    @Excel(name = "账号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("手机号")
    private String mobile;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("身份证号")
    private String idCard;


    @Excel(name = "状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, readConverterExp = "1=已认证,0=已注册")
    @ApiModelProperty("实名认证：0未认证，1已认证  （默认为 0）")
    private Integer realNameAudit;

    /**
     * 是否启用1-是、0-否
     */
    @ApiModelProperty("是否启用")
    private Boolean isEnable;

    @Excel(name = "车辆数量", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("车辆数量")
    private Integer vehicleCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Excel(name = "注册时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("注册时间")
//    private String regTime;
//    private LocalDateTime createTime;
    private LocalDateTime regTime;


}

