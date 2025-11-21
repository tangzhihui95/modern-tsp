package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseDTO;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/24 17:39
 * @Version 1.0.0
 */
@Data
@ApiModel("设备信息 - 数据传输对象 - 导入导出")
public class TspEquipmentModelPageListDTO  extends BaseDTO {

    @ApiModelProperty("设备类型名称")
    private String name;

    /**
     * 设备分类ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备分类ID")
    private Long tspEquipmentTypeId;

    /**
     * 是否为终端1-是、0-否
     */
    @ApiModelProperty("是否为终端1-是、0-否")
    private Boolean isTerminal;

    @ApiModelProperty("设备扩展信息类型")
    private String extraType;

    @ApiModelProperty("设备型号")
    private String modelName;

    /**
     * 供应商
     */
    @ApiModelProperty("供应商")
    private String suppliers;

    @ApiModelProperty("生产批次")
    private String batchNumber;

    @ApiModelProperty("关联设备数量")
    private Integer count;
}
