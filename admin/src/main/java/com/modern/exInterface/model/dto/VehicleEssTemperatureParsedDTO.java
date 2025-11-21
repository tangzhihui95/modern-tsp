package com.modern.exInterface.model.dto;

import cn.jiguang.common.utils.Nullable;
import com.modern.common.annotation.Excel;
import com.modern.exInterface.entity.VehicleEssTemperature;
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
 * @date 2022/7/2 11:27
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("(可充电储能装置温度数据) - 数据传输对象 - 分页列表")
public class VehicleEssTemperatureParsedDTO extends BaseVehicleDataDTO {

    /**
     * 可充电储能子系统个数;（N 个可充电储能装置，有效值范围：1～250，“0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "可充电储能子系统个数", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 1 + EXCEL_EXPORT_SORT_START)
    private Integer totalNumber;

    /**
     * 可充电储能子系统号;（有效值范围：1～250）
     */
    @Excel(name = "可充电储能子系统号", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 2 + EXCEL_EXPORT_SORT_START)
    private Integer sequenceNumber;

    /**
     * 可充电储能温度探针个数N;（N 个温度探针，有效值范围：1～32766，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "可充电储能温度探针个数", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 3 + EXCEL_EXPORT_SORT_START)
    private Integer probeNumber;

    /**
     * 可充电储能子系统各温度探针检测到的温度值1*N;（有效值范围：0～250 （数值偏移量 40℃，表示-40℃～+210℃），最小计量单元：1℃， “0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "可充电储能子系统各温度探针检测到的温度值", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, width = 80, sort = 4 + EXCEL_EXPORT_SORT_START)
    private List<Integer> temperatures;

    /**
     * @param unparsedData 待解析的实体类（从数据库直接读取出来的数据）
     * @return 解析后的实体类
     */
    @Nullable
    public static VehicleEssTemperatureParsedDTO create(VehicleEssTemperature unparsedData) {
        try {
            VehicleEssTemperatureParsedDTO dto = new VehicleEssTemperatureParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());

            /**
             * 可充电储能子系统个数;（N 个可充电储能装置，有效值范围：1～250，“0xFE”表示异常，“0xFF”表示无效。）
             */
            dto.totalNumber = unparsedData.getTotalNumber();

            /**
             * 可充电储能子系统号;（有效值范围：1～250）
             */
            dto.sequenceNumber = unparsedData.getSequenceNumber();

            /**
             * 可充电储能温度探针个数N;（N 个温度探针，有效值范围：1～32766，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
             */
            dto.probeNumber = unparsedData.getProbeNumber();

            /**
             * 可充电储能子系统各温度探针监测到的温度值1*N;（有效值范围：0～250 （数值偏移量 40℃，表示-40℃～+210℃），最小计量单元：1℃， “0xFE”表示异常，“0xFF”表示无效。）
             */
            dto.temperatures = new ArrayList<>();
            for (int i = 0; i < unparsedData.getTemperatures().length; i++) {
                dto.temperatures.add((unparsedData.getTemperatures()[i] & 0xFF) - 40);
            }

            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
