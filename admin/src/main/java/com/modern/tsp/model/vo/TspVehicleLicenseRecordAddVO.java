package com.modern.tsp.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 16:20
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("车辆上牌记录 - 请求对象 - 添加")
public class TspVehicleLicenseRecordAddVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("车辆ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleId;

//    @ApiModelProperty("上牌用户ID")
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private Long byTspUserId;
    /**
     * 上牌车管所名称
     */
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
    @TableField(value = "plate_img_urls", typeHandler = FastjsonTypeHandler.class)
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
}
