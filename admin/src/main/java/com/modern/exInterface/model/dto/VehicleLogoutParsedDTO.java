package com.modern.exInterface.model.dto;

import cn.jiguang.common.utils.Nullable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.exInterface.entity.VehicleLogout;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("(车辆登出) - 数据传输对象 - 分页列表")
public class VehicleLogoutParsedDTO extends BaseVehicleDataDTO {

    /**
     * 登出时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logoutTime;

    /**
     * 登出流水号;（登出流水号与当时登入流水号一致）
     */
    private Integer sequenceNumber;

    /**
     * @param unparsedData 待解析的实体类（从数据库直接读取出来的数据）
     * @return 解析后的实体类
     */
    @Nullable
    public static VehicleLogoutParsedDTO create(VehicleLogout unparsedData) {
        try {
            VehicleLogoutParsedDTO dto = new VehicleLogoutParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());

            dto.logoutTime = unparsedData.getLogoutTime();
            dto.sequenceNumber = unparsedData.getSequenceNumber();

            return dto;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
