package com.modern.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 区县行政编码字典表
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("provinces")
public class Provinces extends Model<Provinces> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 城市缩写名称
     */
    private String shortName;

    /**
     * 城市层级
     */
    private Integer depth;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 城市邮编
     */
    private String zipCode;

    /**
     * 城市组合名称
     */
    private String mergerName;

    /**
     * 精度
     */
    private String longitude;

    /**
     * 维度
     */
    private String latitude;

    /**
     * 城市拼音
     */
    private String pinyin;

    private Integer isUse;


    public static final String ID = "id";

    public static final String CITY_NAME = "city_name";

    public static final String PARENT_ID = "parent_id";

    public static final String SHORT_NAME = "short_name";

    public static final String DEPTH = "depth";

    public static final String CITY_CODE = "city_code";

    public static final String ZIP_CODE = "zip_code";

    public static final String MERGER_NAME = "merger_name";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String PINYIN = "pinyin";

    public static final String IS_USE = "is_use";


}
