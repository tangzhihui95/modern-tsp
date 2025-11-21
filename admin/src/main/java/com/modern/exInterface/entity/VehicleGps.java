package com.modern.exInterface.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.modern.common.core.domain.BaseExModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * vehicle_gps;车辆位置数据
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vehicle_gps")
public class VehicleGps extends BaseExModel {

    private static final long serialVersionUID = 1L;

    /**
     * gps坐标类型;（0-3bit: 0bit: 0,有效定位；1,无效定位（当数据通信正常，而不能获取定位信息时，发送最后一次有效定位信息，并将定位状态置为无效。） 1bit: 0,北纬；1,南纬。 2bit: 0,东经；1,西经。)  (4-7bit: 0x0：wgs84（GPS标准坐标）； 0x1：gcj02（火星坐标，即高德地图、腾讯地图和MapABC等地图使用的坐标；）； 0x2：bd09ll（百度地图采用的经纬度坐标）； 0x3：bd09mc（百度地图采用的墨卡托平面坐标））
     */
    private Integer gpsType;

    /**
     * 经度
     */
    private Integer longitude;

    /**
     * 纬度
     */
    private Integer latitude;

    public static final String GPS_TYPE = "gps_type";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String DATA_TYPE = "data_type";


}
