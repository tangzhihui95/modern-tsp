package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.TspVehicleEnumCertificationState;
import com.modern.tsp.enums.TspVehicleStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/18 20:20
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车辆信息 - 传输对象 - 详情信息")
public class TspVehicleInfoDTO extends BaseDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("用户ID")
    private Long tspUserId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车辆ID")
    private Long tspVehicleId;

    /**
     * 车辆型号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型ID")
    private Long tspVehicleStdModelId;

    @ApiModelProperty("型号名称")
    private String stdModeName;

    @ApiModelProperty("车辆名称")
    private String vehicleModelName;

    /**
     * 车辆型号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型ID")
    private Long tspVehicleModelId;

    /**
     * 设备ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备ID")
    private Long tspEquipmentId;


    @ApiModelProperty("车辆审核认证ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleAuditId;

    /**
     * 车辆厂商
     */
    @ApiModelProperty("车辆厂商")
    private String providerName;


    /**
     * 配置名称
     */
    @ApiModelProperty("配置名称")
    private String configureName;


    /**
     * 颜色
     */
    @ApiModelProperty("颜色")
    private String color;

    /**
     * TBOX版本号
     */
    @ApiModelProperty("VIN")
    private String vin;


    /**
     * 批次号
     */
    @ApiModelProperty("批次号")
    private String batchNo;

    /**
     * 用途
     */
    @ApiModelProperty("用途")
    private String purpose;

    /**
     * 出厂日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出厂日期")
    private LocalDate exFactoryDate;


    /**
     * 经销商ID
     */
    @ApiModelProperty("经销商ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long dealerId;

    /**
     * 运营时间
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("运营时间")
    private LocalDate operateDate;

//    /**
//     * 标签
//     */
//    @ApiModelProperty("标签")
//    private String label;

    /**
     * 标签
     */
    @ApiModelProperty("标签")
    @JsonSerialize(contentUsing = ToStringSerializer.class)
    private List<Long> label;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("购买领域1-私人用车、2-单位用车、0-未知")
    private Integer purchaserState;

    @ApiModelProperty("购买方")
    private String purchaser;


    @ApiModelProperty("身份证号")
    private String vehicleIdCard;

    /**
     * 价税合计
     */
    @ApiModelProperty("价税合计")
    private BigDecimal priceTax;

    /**
     * 发票号码
     */
    @ApiModelProperty("发票号码")
    private String invoiceNo;

    /**
     * 开票日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("Invoicing_date")
    @ApiModelProperty("开票日期")
    private LocalDate invoicingDate;

    /**
     * 是否三包1-是、0-否
     */
    @ApiModelProperty("是否三包1-是、0-否")
    private Boolean isSanBao;

    /**
     * 销货单位名称
     */
    @ApiModelProperty("销货单位名称")
    private String salesUnitName;

    /**
     * 销货地址
     */
    @ApiModelProperty("销货地址")
    private String salesUnitAddress;

    @ApiModelProperty("燃料种类")
    private Integer fuelType;

    @ApiModelProperty("发动机号码")
    private String engineNum;

    @ApiModelProperty("电动机序列号")
    private String motorNum;

    @ApiModelProperty("电池包序列号")
    private String essNum;

    @ApiModelProperty("CDU序列号")
    private String cduNum;

    @ApiModelProperty("电池包规格")
    private String essModel;

    @ApiModelProperty("电动机品牌")
    private String motorBrand;

    @ApiModelProperty("车辆状态")
    private Integer vehicleStatus;

    @ApiModelProperty("新车标识（1：新车，2：非新车）")
    private Integer newVehicleFlag;

    @ApiModelProperty("车辆产地")
    private Integer vehicleOrigin;

    @ApiModelProperty("车辆销售渠道名称（4s店，直营店等）")
    private String salesChannel;

    @ApiModelProperty("车辆销售渠道类型（1：实体渠道，2：人员上门，3：电子渠道）")
    private Integer channelType;

    @ApiModelProperty("办理员工姓名")
    private String employeeName;

    /**
     * 发票图片
     */
    @TableField(value = "invoice_img_urls", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("发票图片")
    private List<String> invoiceImgUrls;


    /**
     * 上牌车管所名称
     */
    @ApiModelProperty("上牌车管所名称")
    private String awardPlaceName;

    /**
     * 上牌地址
     */
//    @ApiModelProperty("上牌地址(省市区)")
//    private String awardPlaceArea;

    @ApiModelProperty("上牌省份")
    private String awardProvince;


    @ApiModelProperty("上牌城市")
    private String awardCity;


    @ApiModelProperty("上牌区域")
    private String awardArea;
    /**
     * 上牌详细地址
     */
    @ApiModelProperty("上牌详细地址")
    private String awardPlaceAddress;

    @ApiModelProperty("车牌号地区  例如：京")
    private String plateCodeName;

    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String plateCode;

    /**
     * 车牌颜色
     */
    @ApiModelProperty("车牌颜色")
    private String plateColour;

    /**
     * 车辆照片
     */
    @ApiModelProperty("车辆照片")
    @TableField(value = "plate_img_urls", typeHandler = FastjsonTypeHandler.class)
    private List<String> plateImgUrls;

    /**
     * 当前上牌日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("当前上牌日期")
    private LocalDate currentUpPlaceDate;


    /**
     * 手机号(账号)
     */
    @ApiModelProperty("手机号")
    private String mobile;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idCard;


    /**
     * 身份证正反面
     */
//    @TableField(value = "card_img_urls",typeHandler = FastjsonTypeHandler.class)
//    @ApiModelProperty("身份证正反面")
//    private List<String> cardImgUrls;

    @ApiModelProperty("身份证正面")
    private String cardFrontImg;


    @ApiModelProperty("身份证反面")
    private String cardBackImg;


    /**
     * 状态1-已创建、2-已销售、3-已绑定、4-已解绑、5-已报废、6-已注册
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @ApiModelProperty("状态1-已创建、2-已销售、3-已绑定、4-已解绑、5-已报废、6-已注册")
    private TspVehicleStateEnum state;


    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @ApiModelProperty("认证状态1-未认证、2-认证中、3-认证失败、4-已认证")
    private TspVehicleEnumCertificationState certificationState;


    /**
     * 信息完成进度1-出厂信息、2-销售信息、3-上牌信息、4-绑定及MNO信息、5-出入库记录
     */
    @ApiModelProperty("信息完成进度0-基本信息、1-出厂信息、2-销售信息、3-上牌信息、4-绑定及MNO信息、5-出入库记录")
    private Integer progress;

    /**
     * 是否完成车辆资料信息1-完成、0-未完成
     */
    @ApiModelProperty("是否完成车辆资料信息1-完成、0-未完成")
    private Boolean isComplete;

}
