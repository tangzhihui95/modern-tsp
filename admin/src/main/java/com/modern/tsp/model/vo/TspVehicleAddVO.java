package com.modern.tsp.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 10:32
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆信息 - 请求对象 - 添加")
public class TspVehicleAddVO {

    @ApiModelProperty("车辆ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleId;

    /**
     * 用户ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspUserId;

    /**
     * 车辆型号ID
     */
//    @NotNull(message = "车辆型号不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleStdModelId;

    /**
     * 车辆厂商
     */
    @ApiModelProperty("车辆厂商")
    private String providerName;

    /**
     * 用途
     */
//    @NotEmpty(message = "车辆用途不能为空")
    @ApiModelProperty("车辆用途")
    private String purpose;

    /**
     * 配置名称
     */
//    @NotEmpty(message = "配置名称不能为空")
    @ApiModelProperty("配置名称")
    private String configureName;

    /**
     * 颜色
     */
//    @NotEmpty(message = "外观颜色不能为空")
    @ApiModelProperty("外观颜色")
    private String color;


//    @NotEmpty(message = "VIN不能为空")
    @Length(min = 17,max = 17,message = "VIN必须为17位")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "VIN格式必须位17位数字或数字字母组成")
    @ApiModelProperty("vin")
    private String vin;

    /**
     * 批次号
     */
//    @NotEmpty(message = "批次号不能为空")
    @ApiModelProperty("批次号")
    private String batchNo;

    /**
     * 设备ID
     */
    @ApiModelProperty("设备ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentId;

    /**
     * 出厂日期
     */
//    @NotNull(message = "出厂日期不能为空")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出厂日期")
    private LocalDate exFactoryDate;

    /**
     * 运营时间
     */
//    @NotNull(message = "运营时间不能为空")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("运营日期")
    private LocalDate operateDate;

    /**
     * 标签
     */
    @ApiModelProperty("标签")
    private List<Long> label;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 状态1-已创建、2-已销售、3-已绑定、4-已解绑、5-已报废、6-已注册
     */
//    @ApiModelProperty("车辆状态")
//    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
//    private TspVehicleStateEnum state;
//
//    @ApiModelProperty("认证状态")
//    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
//    private TspVehicleEnumCertificationState certificationState;

    @ApiModelProperty("购买方")
    private String purchaser;

    @ApiModelProperty("购买领域1-私人用车、2-单位用车、0-未知")
    private Integer purchaserState;

//    @NotEmpty(message = "身份证号不能为空")
//    @Length(min = 17,max = 18,message = "身份证长度应在17位或18位")
//    @Pattern(
//            regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",
//            message = "身份证长度应在17位或18位,由数值或数值加X组成"
//    )
    @ApiModelProperty("车辆登记身份证号")
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
    @ApiModelProperty("是否三包")
    private Boolean isSanBao;

    /**
     * 销货单位名称
     */
    @ApiModelProperty("销货单位名称")
    private String salesUnitName;

    /**
     * 销货地址
     */
    @ApiModelProperty("销货单位地址")
    private String salesUnitAddress;

    @ApiModelProperty("燃料种类")
    private Integer fuelType;

    /**
     * 发动机号码
     */
    @ApiModelProperty("发动机号码")
    private String engineNum;

    /**
     * 电动机序列号
     */
    @ApiModelProperty("电动机序列号")
    private String motorNum;

    /**
     * 电池包序列号
     */
    @ApiModelProperty("电池包序列号")
    private String essNum;

    /**
     * CDU序列号
     */
    @ApiModelProperty("CDU序列号")
    private String cduNum;

    /**
     * 电池包规格
     */
    @ApiModelProperty("电池包规格")
    private String essModel;

    /**
     * 电动机品牌
     */
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
    @ApiModelProperty("发票地址")
    @TableField(value = "invoice_img_urls", typeHandler = FastjsonTypeHandler.class)
    private List<String> invoiceImgUrls;

    /**
     * 上牌车管所名称
     */
//    @NotEmpty(message = "上牌车管所名称不能为空")
    @ApiModelProperty("上牌车管所名称")
    private String awardPlaceName;

    /**
     * 上牌地址
     */
//    @ApiModelProperty("上牌地址")
//    private String awardPlaceArea;


    @ApiModelProperty("省份")
    private String awardProvince;


    @ApiModelProperty("城市")
    private String awardCity;


    @ApiModelProperty("区域")
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
    @TableField(value = "plate_img_urls",typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("车辆照片")
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
//    @NotEmpty(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "格式错误,手机号应为11为数字组成")
    @ApiModelProperty("手机号(账号)")
    private String mobile;


    @ApiModelProperty("车辆审核认证ID")
    private Long tspVehicleAuditId;

    /**
     * 真实姓名
     */
//    @NotEmpty(message = "真实姓名不能为空")
    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 身份证号
     */
//    @NotEmpty(message = "身份证号不能为空")
    @ApiModelProperty("身份证号")
    @Pattern(
            regexp = "^\\d{15}|^\\d{17}([0-9]|X|x)$",
            message = "身份证长度应为18位,由数值或数值加X组成"
    )
    private String idCard;

//    /**
//     * 身份证正反面
//     */
//    @TableField(value = "card_img_urls",typeHandler = FastjsonTypeHandler.class)
//    @ApiModelProperty("身份证正反面")
//    private List<String> cardImgUrls;

    @ApiModelProperty("身份证正面")
    private String cardFrontImg;


    @ApiModelProperty("身份证反面")
    private String cardBackImg;

    /**
     * 当前绑定时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime currentBindTime;

    /**
     * 信息完成进度1-出厂信息、2-销售信息、3-上牌信息、4-绑定及MNO信息、5-出入库记录
     */
    private Integer progress;

    /**
     * 是否完成车辆资料信息1-完成、0-未完成
     */
    private Boolean isComplete;

    @ApiModelProperty("修改状态")
    private Integer editType;

    @ApiModelProperty("经销商ID")
    private Long dealerId;
}
