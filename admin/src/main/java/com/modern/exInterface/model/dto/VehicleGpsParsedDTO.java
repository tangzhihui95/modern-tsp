package com.modern.exInterface.model.dto;

import cn.jiguang.common.utils.Nullable;
import com.modern.exInterface.entity.VehicleGps;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("(车辆位置数据) - 数据传输对象 - 分页列表")
public class VehicleGpsParsedDTO extends BaseVehicleDataDTO {
    /**
     * gps坐标类型;（0-3bit: 0bit: 0,有效定位；1,无效定位（当数据通信正常，而不能获取定位信息时，发送最后一次有效定位信息，并将定位状态置为无效。） 1bit: 0,北纬；1,南纬。 2bit: 0,东经；1,西经。)  (4-7bit: 0x0：wgs84（GPS标准坐标）； 0x1：gcj02（火星坐标，即高德地图、腾讯地图和MapABC等地图使用的坐标；）； 0x2：bd09ll（百度地图采用的经纬度坐标）； 0x3：bd09mc（百度地图采用的墨卡托平面坐标））
     */
    private String gpsType;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * @param unparsedData 待解析的实体类（从数据库直接读取出来的数据）
     * @return 解析后的实体类
     */
    @Nullable
    public static VehicleGpsParsedDTO create(VehicleGps unparsedData) {
        try {
            VehicleGpsParsedDTO dto = new VehicleGpsParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());

            dto.gpsType = "" + ((unparsedData.getGpsType() & 0x01) == 1 ? "无效定位" : "有效定位");
            dto.gpsType += ", " + (((unparsedData.getGpsType() >>> 1) & 0x01) == 1 ? "南纬" : "北纬");
            dto.gpsType += ", " + (((unparsedData.getGpsType() >>> 2) & 0x01) == 1 ? "西经" : "东经");

            dto.longitude = df6.format(((double) unparsedData.getLongitude()) / 1000000);
            dto.latitude = df6.format(((double) unparsedData.getLatitude()) / 1000000);

            return dto;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
