package com.modern.exInterface.model.dto;

import cn.jiguang.common.utils.Nullable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.exInterface.entity.VehicleLogin;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("(车辆登入) - 数据传输对象 - 分页列表")
public class VehicleLoginParsedDTO extends BaseVehicleDataDTO {

    /**
     * 登入时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    /**
     * 登入流水号;（每次登入，流水号加1。从1开始循环累加，最大值为65531，循环周期为天）
     */
    private Integer sequenceNumber;

    /**
     * SIM卡ICCID
     */
    private String iccid;

    /**
     * 可充电储能子系统n
     */
    private Integer essNumber;

    /**
     * 可充电储能系统编码长度m
     */
    private Integer essCodeLength;

    /**
     * 可充电储能系统编码n*m
     */
    private String essCodes;

    /**
     * @param unparsedData 待解析的实体类（从数据库直接读取出来的数据）
     * @return 解析后的实体类
     */
    @Nullable
    public static VehicleLoginParsedDTO create(VehicleLogin unparsedData) {
        try {
            VehicleLoginParsedDTO dto = new VehicleLoginParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());

            dto.loginTime = unparsedData.getLoginTime();
            dto.sequenceNumber = unparsedData.getSequenceNumber();
            dto.iccid = unparsedData.getIccid();
            dto.essNumber = unparsedData.getEssNumber();
            dto.essCodeLength = unparsedData.getEssCodeLength();
            dto.essCodes = unparsedData.getEssCodes();

            return dto;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
