package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 16:39
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("电子围栏 - 传输对象 - 详情信息")
public class TspFencePageListDTO extends BaseDTO {

    @ApiModelProperty(value = "电子围栏ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspFenceId;

    /**
     * 围栏名称
     */
    @Column(comment = "围栏名称",type = MySqlTypeConstant.VARCHAR,length = 50,isNull = false)
    private String fenceName;

    /**
     * 围栏位置
     */
    @Column(comment = "围栏位置",type = MySqlTypeConstant.VARCHAR,length = 50)
    private String fenceArea;

    /**
     * 围栏来源
     */
    @Column(comment = "围栏来源",type = MySqlTypeConstant.VARCHAR,length = 20)
    private String fenceSource;

    /**
     * 围栏状态(1：开启，2：关闭)
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @Column(comment = "围栏状态(1：开启，2：关闭)",type = MySqlTypeConstant.INT)
    private TspFenceStatusEnum fenceStatus;

}
