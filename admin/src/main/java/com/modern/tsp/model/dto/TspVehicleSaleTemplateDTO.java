package com.modern.tsp.model.dto;

import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/20 15:15
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 数据传输对象 - 销售信息模板")
public class TspVehicleSaleTemplateDTO {

    /**
     * vin
     */
    @Excel(name = "VIN",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("VIN")
    private String vin;

    @ApiModelProperty("购买领域1-私人用车、2-单位用车、0-未知")
    @Excel(name = "购买领域",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String purchaserState;

    /**
     * 用途
     */
    @Excel(name = "用途",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("用途")
    private String purpose;

    @ApiModelProperty("购买方")
    @Excel(name = "购买方",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String purchaser;


    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String vehicleIdCard;

    /**
     * 价税合计
     */
    @Excel(name = "价税合计",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("价税合计")
    private BigDecimal priceTax;

    /**
     * 发票号码
     */
    @Excel(name = "发票号码",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("发票号码")
    private String invoiceNo;

    /**
     * 开票日期
     */
    @Excel(name = "开票日期",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("开票日期")
    private String invoicingDate;

    /**
     * 是否三包1-是、0-否
     */
    @ApiModelProperty("是否三包1-是、0-否")
    @Excel(name = "是否三包",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "1=是,0=否")
    private Integer isSanBao;

    /**
     * 销货单位名称
     */
    @ApiModelProperty("销货单位名称")
    @Excel(name = "销货单位名称",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String salesUnitName;

    /**
     * 销货地址
     */
    @ApiModelProperty("销货地址")
    @Excel(name = "销货地址",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String salesUnitAddress;

    /**
     * 车辆状态
     */
    @ApiModelProperty("车辆状态")
    @Excel(name = "车辆状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,
            readConverterExp = "1=生产期,2=测试期,3=代售,4=已售,5=过户,6=报废,7=其他")
    private Integer vehicleStatus;

    /**
     * 销售渠道名称
     */
    @ApiModelProperty("销售渠道名称")
    @Excel(name = "销售渠道名称",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String salesChannel;

    /**
     * 销售渠道类型
     */
    @ApiModelProperty("销售渠道类型")
    @Excel(name = "销售渠道类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "1=实体渠道,2=人员上门,3=电子渠道")
    private Integer channelType;

    /**
     * 办理员工姓名
     */
    @ApiModelProperty("办理员工姓名")
    @Excel(name = "办理员工姓名",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String employeeName;

    /**
     * 是否是新车
     */
    @ApiModelProperty("是否是新车")
    @Excel(name = "是否是新车",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String newVehicleFlagText;


    public Integer getNewVehicleFlag() {
        return "是".equals(newVehicleFlagText)?1:2;
    }

    /**
     * 是否是新车
     */
    @ApiModelProperty("是否是新车")
    private Integer newVehicleFlag;

}
