package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/16 9:33
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 数据传输对象 - 导入导出")
public class TspVehicleModelExcelDTO {

    @Excel(name = "车辆分类",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车辆分类")
    private String typeName;

    /**
     * 车辆名称
     */
    @Excel(name = "车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车型")
    private String vehicleModelName;

    /**
     * 数量
     */
    @Excel(name = "型号数量",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("型号数量")
    private Long quantity;


    @Excel(name = "关联车辆",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("关联车辆")
    private Integer vehicleCount;
}
