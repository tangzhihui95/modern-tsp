package com.modern.exInterface.model.dto;

import cn.jiguang.common.utils.Nullable;
import com.google.common.collect.ImmutableMap;
import com.modern.common.annotation.Excel;
import com.modern.exInterface.entity.VehicleDriveMotor;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/2 10:08
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("(驱动电机数据) - 数据传输对象 - 分页列表")
public class VehicleDriveMotorParsedDTO extends BaseVehicleDataDTO {
    public static final Map<Integer, String> STATE_MAP = ImmutableMap.<Integer, String>builder()
            .put(0x01, "耗电").put(0x02, "发电").put(0x03, "关闭").put(0x04, "准备")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    /**
     * 驱动电机个数;（有效值 1~253）
     */
    @Excel(name = "驱动电机个数",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 1 + EXCEL_EXPORT_SORT_START)
    private Integer totalNumber;

    /**
     * 驱动电机序号;（有效值范围 1~253）
     */
    @Excel(name = "驱动电机序号",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 2 + EXCEL_EXPORT_SORT_START)
    private Integer sequenceNumber;

    /**
     * 驱动电机状态;（0x01：耗电；0x02：发电；0x03：关闭状态，0x04：准备状态, “0xFE” 表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "驱动电机状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 3 + EXCEL_EXPORT_SORT_START)
    private String state;

    /**
     * 驱动电机控制器温度;（有效值范围：0～250 （数值偏移量 40℃，表示40℃～+210 ℃），最小计量单元：1℃，“0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "驱动电机控制器温度",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 4 + EXCEL_EXPORT_SORT_START)
    private Integer controllerTemperature;

    /**
     * 驱动电机转速;（有效值范围：0～65531（数值偏移量 20000 表示20000 r/min ～ 45531r/min ）， 最小计量单元 ： 1r/min，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "驱动电机转速",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 5 + EXCEL_EXPORT_SORT_START)
    private Integer rotateSpeed;

    /**
     * 驱动电机转矩;（有效值范围：0～65531（数值偏移量 20000 表示2000 N*m～4553.1N*m），最小计量单元：0.1N*m，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "驱动电机转矩",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 6 + EXCEL_EXPORT_SORT_START)
    private String torque;

    /**
     * 驱动电机温度;（有效值范围：0～250 （数值偏移量 40℃，表示40℃～+210 ℃），最小计量单元：1℃，“0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "驱动电机温度",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 7 + EXCEL_EXPORT_SORT_START)
    private Integer temperature;

    /**
     * 电机控制器输入电压;（有效值范围：0～60000（表示 0V～6000V），最小计量单元：0.1V，“0xFF,0xFE”表示异常，“0xFF,0xFF” 表示无效。）
     */
    @Excel(name = "电机控制器输入电压",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 8 + EXCEL_EXPORT_SORT_START)
    private String controllerInputVoltage;

    /**
     * 电机控制器直流母线电流;（有效值范围： 0～20000（数值偏移量 1000A，表示1000A ～ +1000A ）， 最小计量单 元 ： 0.1A ，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "电机控制器直流母线电流",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 9 + EXCEL_EXPORT_SORT_START)
    private String controllerDcBusCurrent;

    /**
     * @param unparsedData 待解析的实体类（从数据库直接读取出来的数据）
     * @return 解析后的实体类
     */
    @Nullable
    public static VehicleDriveMotorParsedDTO create(VehicleDriveMotor unparsedData) {
        try {
            VehicleDriveMotorParsedDTO dto = new VehicleDriveMotorParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());

            // 驱动电机个数
            dto.totalNumber = unparsedData.getTotalNumber();

            // 驱动电机序号
            dto.sequenceNumber = unparsedData.getSequenceNumber();

            // 驱动电机状态
            dto.state = STATE_MAP.get(unparsedData.getState()) != null ? STATE_MAP.get(unparsedData.getState()) : unparsedData.getState().toString();

            // 驱动电机控制器温度
            dto.controllerTemperature = unparsedData.getControllerTemperature() - 40;

            //驱动电机转速
            dto.rotateSpeed = unparsedData.getRotateSpeed() - 20000;

            // 驱动电机转矩
            dto.torque = df1.format(((double) (unparsedData.getTorque() - 20000)) / 10);

            // 驱动电机温度
            dto.temperature = unparsedData.getTemperature() - 40;

            // 电机控制器输人电压
            dto.controllerInputVoltage = df1.format(((double) (unparsedData.getControllerInputVoltage())) / 10);

            // 电机控制器直流母线电流
            dto.controllerDcBusCurrent = df1.format(((double) unparsedData.getControllerDcBusCurrent()) / 10 - 1000);

            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
