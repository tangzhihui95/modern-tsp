package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 9:16
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Alias("TspVehicleMarket")
@Table(name = "tsp_vehicle_market",comment = "摩登 - TSP - 车辆销售信息")
@TableName(value = "tsp_vehicle_market",autoResultMap = true)
public class TspVehicleMarket extends BaseModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆ID",type = MySqlTypeConstant.BIGINT,isNull = false)
    private Long tspVehicleId;


    @Column(comment = "购买领域1-私人用车、2-单位用车、0-未知",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    private Integer purchaserState;


    @Column(comment = "购买方", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String purchaser;


    @Column(comment = "身份证号", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String vehicleIdCard;

    /**
     * 价税合计
     */
    @Column(comment = "价税合计", type = MySqlTypeConstant.DECIMAL, isNull = false, defaultValue = "0")
    private BigDecimal priceTax;

    /**
     * 发票号码
     */
    @Column(comment = "发票号码", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String invoiceNo;

    /**
     * 开票日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("Invoicing_date")
    @Column(comment = "开票日期", type = MySqlTypeConstant.DATE)
    private LocalDate invoicingDate;

    /**
     * 是否三包1-是、0-否
     */
    @Column(comment = "是否三包1-是、0-否", type = MySqlTypeConstant.TINYINT)
    private Boolean isSanBao;

    /**
     * 销货单位名称
     */
    @Column(comment = "销货单位名称", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String salesUnitName;

    /**
     * 销货地址
     */
    @Column(comment = "销货地址", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String salesUnitAddress;

    /**
     * 发票图片
     */
    @TableField(value = "invoice_img_urls", typeHandler = FastjsonTypeHandler.class)
    @Column(comment = "发票图片", type = MySqlTypeConstant.TEXT)
    private List<String> invoiceImgUrls = new ArrayList<>();


}
