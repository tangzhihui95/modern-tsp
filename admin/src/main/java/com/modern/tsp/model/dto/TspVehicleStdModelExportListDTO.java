package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.annotation.Excel;
import com.modern.tsp.enums.TpsVehicleDataKeyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/24 14:31
 * @Version 1.0.0
 */
@Data
@ApiModel("车辆型号信息 - 数据传输对象 - 导入导出")
public class TspVehicleStdModelExportListDTO {

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

//    @Excel(name = "环保标准",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 6)
//    @ApiModelProperty("环保标准")
//    private String environmentalProtection;
//
//    @Excel(name = "车身尺寸(长*宽*高)M",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 7)
//    @ApiModelProperty("车身尺寸(长*宽*高)M")
//    private String dimensions;
//
//    @Excel(name = "变速箱",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 8)
//    @ApiModelProperty("变速箱")
//    private String transmissionCase;

}
