package com.modern.exInterface.model.dto;

import cn.jiguang.common.utils.Nullable;
import com.modern.common.annotation.Excel;
import com.modern.exInterface.entity.VehicleExtreme;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/2 10:23
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("(极值数据) - 数据传输对象 - 分页列表")
public class VehicleExtremeParsedDTO extends BaseVehicleDataDTO {

    /**
     * 最高电压电池子系统号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    @Excel(name = "最高电压电池子系统号",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 1 + EXCEL_EXPORT_SORT_START)
    private Integer maxVoltageBatterySubsystem;

    /**
     * 最高电压电池单体代号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    @Excel(name = "最高电压电池单体代号",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 2 + EXCEL_EXPORT_SORT_START)
    private Integer maxVoltageBatteryCell;

    /**
     * 电池单体电压最高值;（有效值范围：0～15000（表示 0V～15V），最小计量单元： 0.001V ，“ 0xFF,0xFE ” 表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "电池单体电压最高值",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 3 + EXCEL_EXPORT_SORT_START)
    private String maxVoltageBattery;

    /**
     * 最低电压电池子系统号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    @Excel(name = "最低电压电池子系统号",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 4 + EXCEL_EXPORT_SORT_START)
    private Integer minVoltageBatterySubsystem;

    /**
     * 最低电压电池单体代号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    @Excel(name = "最低电压电池单体代号",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 5 + EXCEL_EXPORT_SORT_START)
    private Integer minVoltageBatteryCell;

    /**
     * 电池单体电压最低值;（有效值范围：0～15000（表示 0V～15V），最小计量单元： 0.001V ，“ 0xFF,0xFE ” 表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "电池单体电压最低值",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 6 + EXCEL_EXPORT_SORT_START)
    private String minVoltageBattery;

    /**
     * 最高温度子系统号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    @Excel(name = "最高温度子系统号",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 7 + EXCEL_EXPORT_SORT_START)
    private Integer maxTemperatureSubsystem;

    /**
     * 最高温度探针序号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    @Excel(name = "最高温度探针序号",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 8 + EXCEL_EXPORT_SORT_START)
    private Integer maxTemperatureProbe;

    /**
     * 最高温度值;（有效值范围：0～250（数值偏移量 40℃，表示40℃～+210 ℃），最小计量单元：1℃，“0xFE”表示异常，“0xFF”表示无效）
     */
    @Excel(name = "最高温度值",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 9 + EXCEL_EXPORT_SORT_START)
    private Integer maxTemperature;

    /**
     * 最低温度子系统号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    @Excel(name = "最低温度子系统号",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 10 + EXCEL_EXPORT_SORT_START)
    private Integer minTemperatureSubsystem;

    /**
     * 最低温度探针序号;（有效值范围：1～250，“0xFE”表示异常，“0xFF” 表示无效。）
     */
    @Excel(name = "最低温度探针序号",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 11 + EXCEL_EXPORT_SORT_START)
    private Integer minTemperatureProbe;

    /**
     * 最低温度值;（有效值范围：0～250（数值偏移量 40℃，表示40℃～+210 ℃），最小计量单元：1℃，“0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "最低温度值",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 12 + EXCEL_EXPORT_SORT_START)
    private Integer minTemperature;

    /**
     * @param unparsedData 待解析的实体类（从数据库直接读取出来的数据）
     * @return 解析后的实体类
     */
    @Nullable
    public static VehicleExtremeParsedDTO create(VehicleExtreme unparsedData) {
        try {
            VehicleExtremeParsedDTO dto = new VehicleExtremeParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());

            // 最高电压电池子系统号
            dto.maxVoltageBatterySubsystem = unparsedData.getMaxVoltageBatterySubsystem();

            // 最高电压电池单体代号
            dto.maxVoltageBatteryCell = unparsedData.getMaxVoltageBatteryCell();

            // 电池单体电压最高值
            dto.maxVoltageBattery = df3.format(((double) unparsedData.getMaxVoltageBattery()) / 1000);

            // 最低电压电池子系统号
            dto.minVoltageBatterySubsystem = unparsedData.getMinVoltageBatterySubsystem();

            // 最低电压电池单体代号
            dto.minVoltageBatteryCell = unparsedData.getMinVoltageBatteryCell();

            // 电池单体电压最低值
            dto.minVoltageBattery = df3.format(((double) unparsedData.getMinVoltageBattery()) / 1000);

            // 最高温度子系统号
            dto.maxTemperatureSubsystem = unparsedData.getMaxTemperatureSubsystem();

            // 最高温度探针序号
            dto.maxTemperatureProbe = unparsedData.getMaxTemperatureProbe();

            // 最高温度值
            dto.maxTemperature = unparsedData.getMaxTemperature() - 40;

            // 最低温度子系统号
            dto.minTemperatureSubsystem = unparsedData.getMinTemperatureSubsystem();

            // 最低温度探针序号
            dto.minTemperatureProbe = unparsedData.getMinTemperatureProbe();

            // 最低温度值
            dto.minTemperature = unparsedData.getMinTemperature() - 40;

            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
