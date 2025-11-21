package com.modern.exInterface.model.dto;

import cn.jiguang.common.utils.Nullable;
import com.modern.common.annotation.Excel;
import com.modern.exInterface.entity.VehicleEss;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/2 10:50
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("(可充电储能装置电压数据) - 数据传输对象 - 分页列表")
public class VehicleEssParsedDTO extends BaseVehicleDataDTO {


    /**
     * 可充电储能子系统个数;（N 个可充电储能子系统，有效值范围：1～250，“0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "可充电储能子系统个数", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 1 + EXCEL_EXPORT_SORT_START)
    private Integer totalNumber;

    /**
     * 可充电储能子系统号;（有效值范围：1～250）
     */
    @Excel(name = "可充电储能子系统号", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 2 + EXCEL_EXPORT_SORT_START)
    private Integer sequenceNumber;

    /**
     * 可充电储能装置电压;（有效值范围：0～10000（表示 0V～1000V），最小计量单元：0.1V，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。 ）
     */
    @Excel(name = "可充电储能装置电压", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 3 + EXCEL_EXPORT_SORT_START)
    private String voltage;

    /**
     * 可充电储能装置电流;（有 效 值 范 围 ： 0 ～ 20000 （ 数 值 偏 移 量1000A，表示-1000A～+1000A），最小计量单元：0.1 A，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "可充电储能装置电流", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 4 + EXCEL_EXPORT_SORT_START)
    private String current;

    /**
     * 单体电池总数;（N 个电池单体，有效值范围：1～65531，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "单体电池总数", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 5 + EXCEL_EXPORT_SORT_START)
    private Integer batteryNumber;

    /**
     * 本帧起始电池序号;（当本帧单体个数超过 200 时，应拆分成多帧数据进行传输，有效值范围：1～65531）
     */
    @Excel(name = "本帧起始电池序号", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 6 + EXCEL_EXPORT_SORT_START)
    private Integer thisBatteryStartIndex;

    /**
     * 本帧单体电池总数 M;（本帧单体总数 m;有效值范围：1～200）
     */
    @Excel(name = "本帧单体电池总数", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 7 + EXCEL_EXPORT_SORT_START)
    private Integer thisBatteryNumber;

    /**
     * 单体电池电压 2*M;（有效值范围：0～60000（表示 0V～60.000V），最小计量单元：0.001V，单体电池电压个数等于本帧单体电池总数 m，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 ）
     */
    @Excel(name = "单体电池电压", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, width = 100, sort = 8 + EXCEL_EXPORT_SORT_START)
    private List<String> thisBatteryVoltages;

    /**
     * @param unparsedData 待解析的实体类（从数据库直接读取出来的数据）
     * @return 解析后的实体类
     */
    @Nullable
    public static VehicleEssParsedDTO create(VehicleEss unparsedData) {
        try {
            VehicleEssParsedDTO dto = new VehicleEssParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());


            /**
             * 可充电储能子系统个数;（N 个可充电储能子系统，有效值范围：1～250，“0xFE”表示异常，“0xFF”表示无效。）
             */
            dto.totalNumber = unparsedData.getTotalNumber();

            /**
             * 可充电储能子系统号;（有效值范围：1～250）
             */
            dto.sequenceNumber = unparsedData.getSequenceNumber();

            /**
             * 可充电储能子系统装置电压;（有效值范围：0～10000（表示 0V～1000V），最小计量单元：0.1V，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。 ）
             */
            dto.voltage = df1.format(((double) unparsedData.getVoltage()) / 10);

            /**
             * 可充电储能子系统装置电流;（有 效 值 范 围 ： 0 ～ 20000 （ 数 值 偏 移 量1000A，表示-1000A～+1000A），最小计量单元：0.1 A，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
             */
            dto.current = df1.format(((double) unparsedData.getCurrent()) / 10 - 1000);

            /**
             * 单体电池总数;（N 个电池单体，有效值范围：1～65531，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
             */
            dto.batteryNumber = unparsedData.getBatteryNumber();

            /**
             * 本帧起始电池序号;（当本帧单体个数超过 200 时，应拆分成多帧数据进行传输，有效值范围：1～65531）
             */
            dto.thisBatteryStartIndex = unparsedData.getThisBatteryStartIndex();

            /**
             * 本帧单体电池总数M;（本帧单体总数 m;有效值范围：1～200）
             */
            dto.thisBatteryNumber = unparsedData.getThisBatteryNumber();

            /**
             * 单体电池电压值2*M;（有效值范围：0～60000（表示 0V～60.000V），最小计量单元：0.001V，单体电池电压个数等于本帧单体电池总数 m，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 ）
             */
            dto.thisBatteryVoltages = new ArrayList<>();
            for (int i = 0; i < (unparsedData.getThisBatteryVoltages().length / 2); i++) {
                dto.thisBatteryVoltages.add(df3.format(((double) (((unparsedData.getThisBatteryVoltages()[2 * i] & 0xFF) << 8)
                        | (unparsedData.getThisBatteryVoltages()[2 * i + 1] & 0xFF))) / 1000));
            }

            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
