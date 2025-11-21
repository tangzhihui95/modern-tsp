package com.modern.tsp.model.dto;

import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/20 16:54
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 数据传输对象 - 导出列表信息")
public class TspVehicleExportListDTO {

    @Excel(name = "VIN", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT)
    @ApiModelProperty("VIN")
    private String vin;

    /**
     * 车牌号
     */
    @Excel(name = "车牌号", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT)
    @ApiModelProperty("车牌号")
    private String plateCode;

    /**
     * 一级车型
     */
    @Excel(name = "一级车型", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT)
    @ApiModelProperty("一级车型")
    private String vehicleModelName;

    /**
     * 二级车型
     */
    @Excel(name = "二级车型", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT)
    @ApiModelProperty("二级车型")
    private String stdModeName;

    /**
     * 手机号(账号)
     */
    @Excel(name = "关联账号", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT)
    @ApiModelProperty("关联账号")
    private String mobile;

    @Excel(name = "认证状态",
            cellType = Excel.ColumnType.STRING,
            type = Excel.Type.EXPORT,
            readConverterExp = "1=未认证,2=认证中,3=认证失败,4=已认证"
    )
    @ApiModelProperty("认证状态")
    private Integer certificationState;

    @Excel(name = "车辆状态",
            cellType = Excel.ColumnType.STRING,
            type = Excel.Type.EXPORT,
            readConverterExp = "1=已创建,2=已销售,3=已绑定,4=已解绑,5=已报废,6=已注册"
    )
    @ApiModelProperty("车辆状态")
    private Integer state;

    @Excel(name = "推送状态",
            cellType = Excel.ColumnType.STRING,
            type = Excel.Type.EXPORT,
            readConverterExp = "0=未推送,1=已推送"
    )
    @ApiModelProperty("推送状态")
    private Integer sendStatus;

    @Excel(name = "创建时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
//    private String createTime;
    private LocalDateTime createTime;

}
