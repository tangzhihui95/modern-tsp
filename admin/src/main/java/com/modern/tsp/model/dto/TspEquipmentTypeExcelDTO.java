package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 15:34
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@Data
@ApiModel("设备信息 - 数据传输对象 - 导入导出")
public class TspEquipmentTypeExcelDTO {

    @Excel(name = "设备类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 1)
    @ApiModelProperty("设备型号名称")
    private String name;

    /**
     * 是否为终端1-是、0-否
     */
    @Excel(name = "是否为终端",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "true=是,false=否",sort = 2)
    @ApiModelProperty("是否为终端1-是、0-否")
    private Boolean isTerminal;

    @Excel(name = "设备扩展信息类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 3)
    @ApiModelProperty("设备扩展信息类型")
    private String extraType;

    @Excel(name = "设备型号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 4)
    @ApiModelProperty("设备型号")
    private String modelName;

    /**
     * 供应商
     */
    @Excel(name = "供应商",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 5)
    @ApiModelProperty("供应商")
    private String suppliers;

    @Excel(name = "生产批次",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 6)
    @ApiModelProperty("生产批次")
    private String batchNumber;

    @Excel(name = "关联设备",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 7)
    @ApiModelProperty("生产批次")
    private Integer count;
}