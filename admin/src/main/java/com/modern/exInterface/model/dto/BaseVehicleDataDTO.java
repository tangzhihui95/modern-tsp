package com.modern.exInterface.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.collect.ImmutableMap;
import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class BaseVehicleDataDTO {
    public static final int EXCEL_EXPORT_SORT_START = 3;
    public static final String EXCEPTION_STRING = "异常";
    public static final String INVALID_STRING = "无效";
    public static final String HAVE_STRING = "有";
    public static final String NOT_HAVE_STRING = "无";
    public static final Map<Integer, String> BYTE_EXCEPTION_INVALID_MAP = ImmutableMap.of(0xFE, EXCEPTION_STRING, 0xFF, INVALID_STRING);
    public static final Map<Integer, String> SHORT_EXCEPTION_INVALID_MAP = ImmutableMap.of(0xFFFE, EXCEPTION_STRING, 0xFFFF, INVALID_STRING);
    public static final Map<Integer, String> DATA_TYPE_MAP = ImmutableMap.of(0x01, "车辆登入", 0x02, "实时信息", 0x03, "补发信息", 0x04, "车辆登出");

    public static final DecimalFormat df1 = new DecimalFormat("0.0");  //RoundingMode.HALF_EVEN
    public static final DecimalFormat df2 = new DecimalFormat("0.00");  //RoundingMode.HALF_EVEN
    public static final DecimalFormat df3 = new DecimalFormat("0.000");  //RoundingMode.HALF_EVEN
    public static final DecimalFormat df6 = new DecimalFormat("0.000000");  //RoundingMode.HALF_EVEN

    @Excel(name = "ID", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, width = 20, sort = Integer.MAX_VALUE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("ID")
    protected Long id;

    /**
     * VIN;（应符合GB16735的规定）
     */
    @Excel(name = "VIN", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 1)
    @ApiModelProperty("VIN")
    protected String vin;

    /**
     * 数据类型;（0x02实时数据，0x03补发数据）
     */
    @Excel(name = "命令标识", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = Integer.MAX_VALUE - 2)
    @ApiModelProperty("命令标识")
    protected String dataType;

    @Excel(name = "采集时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd HH:mm:ss", sort = 2)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("采集时间")
    protected LocalDateTime collectTime;

    @Excel(name = "接收时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd HH:mm:ss", sort = Integer.MAX_VALUE - 1)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("接收时间")
    protected LocalDateTime createTime;

    protected void parseBaseData(Long id, String vin, Integer dataType, LocalDateTime collectTime, LocalDateTime createTime) {
        this.id = id;
        this.vin = vin;
        this.dataType = DATA_TYPE_MAP.get(dataType) != null ? DATA_TYPE_MAP.get(dataType) : dataType.toString();
        this.collectTime = collectTime;
        this.createTime = createTime;
    }
}
