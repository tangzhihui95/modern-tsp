package com.modern.tsp.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/16 9:52
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 请求对象 - 导入销售信息")
public class TspVehicleSaleExcelDTO {

    @Excel(name = "车辆配置名称",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    private String configureName;

    @Excel(name = "购买方",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("购买方")
    private String purchaser;


    @Excel(name = "车辆登记身份证号",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("车辆登记身份证号")
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
    @Excel(name = "开票日期",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,dateFormat = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("Invoicing_date")
    @ApiModelProperty("开票日期")
    private LocalDate invoicingDate;

    /**
     * 是否三包1-是、0-否
     */
    @Excel(name = "是否三包",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,readConverterExp = "true=是,false=否")
    @ApiModelProperty("是否三包")
    private Boolean isSanBao;

    /**
     * 销货单位名称
     */
    @Excel(name = "销货单位名称",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("销货单位名称")
    private String salesUnitName;

    /**
     * 销货地址
     */
    @Excel(name = "销货单位地址",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL)
    @ApiModelProperty("销货单位地址")
    private String salesUnitAddress;

    /**
     * 发票图片
     */
    @Excel(name = "发票图片",cellType = Excel.ColumnType.IMAGE,type = Excel.Type.ALL)
    @ApiModelProperty("发票地址")
    @TableField(value = "invoice_img_urls", typeHandler = FastjsonTypeHandler.class)
    private List<String> invoiceImgUrls = new ArrayList<>();
}
