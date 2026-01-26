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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 摩登 - TSP - 车辆上牌操作记录
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Alias("TspVehicleLicenseRecord")
@Table(value = "tsp_vehicle_license_record",comment = "摩登 - TSP - 车牌操作记录")
@TableName("tsp_vehicle_license_record")
public class TspVehicleLicenseRecord extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 车辆ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspVehicleId;


//    @Column(comment = "上牌用户ID", type = MySqlTypeConstant.BIGINT, isNull = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private Long byTspUserId;

    /**
     * 车管所名称
     */
    @Column(comment = "车管所名称", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardPlaceName;

    /**
     * 上牌地址
     */
//    @Column(comment = "上牌地址",type = MySqlTypeConstant.VARCHAR,length = 255)
//    private String awardPlaceArea;
    @Column(comment = "上牌省份", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardProvince;


    @Column(comment = "上牌城市", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardCity;


    @Column(comment = "上牌区域", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardArea;

    /**
     * 详细地址
     */
    @Column(comment = "详细地址", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String awardPlaceAddress;


//    @Column(comment = "车牌号地区  例如：京",type = MySqlTypeConstant.VARCHAR,length = 10)
//    private String plateCodeName;

    @Column(comment = "版本号", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    private Integer version;


    /**
     * 车牌号
     */
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

    /**
     * 上牌日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "上牌日期", type = MySqlTypeConstant.DATE)
    private LocalDate upPlaceDate;


    public static final String TSP_VEHICLE_ID = "tsp_vehicle_id";

    public static final String AWARD_PLACE_NAME = "award_place_name";

    public static final String PLACE_AREA = "award_place_area";

    public static final String ADDRESS = "award_place_address";

    public static final String PLATE_CODE = "plate_code";

    public static final String PLATE_COLOUR = "plate_colour";

    public static final String PLATE_IMG_URLS = "plate_img_urls";

    public static final String UP_PLACE_DATE = "up_place_date";

    public static final String VERSION = "version";

}
