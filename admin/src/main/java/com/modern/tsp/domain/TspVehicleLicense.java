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
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 9:19
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Alias("TspVehicleLicense")
@Table(name = "tsp_vehicle_license", comment = "摩登 - TSP - 车牌信息")
@TableName(value = "tsp_vehicle_license", autoResultMap = true)
public class TspVehicleLicense extends BaseModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspVehicleId;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "vin", type = MySqlTypeConstant.VARCHAR,length = 255,isNull = false)
    private String vin;

    /**
     * 上牌车管所名称
     */
    @Column(comment = "上牌车管所名称", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardPlaceName;


    @Column(comment = "上牌省份",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String awardProvince;


    @Column(comment = "上牌城市",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String awardCity;


    @Column(comment = "上牌区域",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String awardArea;

//    /**
//     * 上牌地址
//     */
//    @Column(comment = "上牌地址", type = MySqlTypeConstant.VARCHAR, length = 255)
//    private String awardPlaceArea;

    /**
     * 上牌详细地址
     */
    @Column(comment = "上牌详细地址", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String awardPlaceAddress;


    /**
     * 当前上牌日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "当前上牌日期",type = MySqlTypeConstant.DATE)
    private LocalDate currentUpPlaceDate;

    /**
     * 车牌号
     */
    @Index(value = "idx_plate_code")
    @Column(comment = "车牌号", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String plateCode;

    /**
     * 车牌颜色
     */
    @Column(comment = "车牌颜色", type = MySqlTypeConstant.VARCHAR, length = 22)
    private String plateColour;

    /**
     * 车辆照片
     */
    @TableField(value = "plate_img_urls", typeHandler = FastjsonTypeHandler.class)
    @Column(comment = "车辆照片", type = MySqlTypeConstant.TEXT)
    private List<String> plateImgUrls = new ArrayList<>();

}
