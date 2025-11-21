package com.modern.tsp.model.dto;

import com.modern.common.annotation.Excel;
import com.modern.tsp.enums.TpsVehicleDataKeyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2023/1/9 18:00
 * @Version 1.0.0
 */
@Data
@ApiModel("车辆型号信息 - 数据传输对象 - 导出")
public class TspVehicleStdModelExListDTO {

    @Excel(name = "一级车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 1)
    @ApiModelProperty("一级车型")
    private String vehicleModelName;

    @Excel(name = "二级车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 2)
    @ApiModelProperty("二级车型")
    private String stdModeName;

    @Excel(name = "能源类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 3)
    @ApiModelProperty("能源类型")
    private String dataType;

    private TpsVehicleDataKeyEnum dataKey;

    @Excel(name = "公告批次",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 4)
    @ApiModelProperty("公告批次")
    private String noticeBatch;

    @Excel(name = "公告型号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 5)
    @ApiModelProperty("公告型号")
    private String noticeModel;

    @ApiModelProperty("二级车型id")
    private Long tspStdModelId;

    @Excel(name = "关联车辆数",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 6)
    @ApiModelProperty("关联车辆数")
    private String vehicleCount;
}
