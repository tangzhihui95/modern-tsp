package com.modern.exInterface.model.dto;

import cn.hutool.core.util.HexUtil;
import cn.jiguang.common.utils.Nullable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modern.common.annotation.Excel;
import com.modern.exInterface.entity.VehicleAlert;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("(报警数据) - 数据传输对象 - 分页列表")
public class VehicleAlertParsedDTO extends BaseVehicleDataDTO {
    public static final String ALARM_STRING = "报警";
    public static final String NORMAL_STRING = "正常";

    public static final String[] ALARM_SIGN_NAME = {"温度差异报警", "电池高温报警", "车载储能装置类型过压报警", "车载储能装置类型欠压报警", "SOC低报警",
            "单体电池过压报警", "单体电池欠压报警", "SOC过高报警", "SOC跳变报警", "可充电储能系统不匹配报警",
            "电池单体一致性差报警", "绝缘报警", "DC-DC温度报警", "制动系统报警", "DC-DC状态报警",
            "驱动电机控制器温度报警", "高压互锁状态报警", "驱动电机温度报警", "车载储能装置类型过充报警",
            "BMS高压电池错误报警", "防盗报警"};

    /**
     * 最高报警等级;（在当前发生的通用报警中，级别最高的报警所处于的等级。有效值范围：0～3，“0”表示无故障；“1”表示 1 级故障，指代不影响车辆正常行驶的故障；“2”表示 2 级故障，指代影响车辆性能，需驾驶员限制行驶的故障；“3”表示 3 级故障，为最高级别故障，指代驾驶员应立即停车处理或请求救援的故障； “0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "最高报警等级",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = EXCEL_EXPORT_SORT_START + 1)
    private Integer maxAlarmLevel;

    /**
     * 通用报警标志;（通用报警标志位定义见表 18）
     */
    private LinkedHashMap<String, String> generalAlarmSign;

    //Excel导出报警字段__start
    @JsonIgnore
    @Excel(name = "温度差异报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 0 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign0;

    @JsonIgnore
    @Excel(name = "电池高温报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 1 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign1;

    @JsonIgnore
    @Excel(name = "车载储能装置类型过压报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 2 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign2;

    @JsonIgnore
    @Excel(name = "车载储能装置类型欠压报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 3 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign3;

    @JsonIgnore
    @Excel(name = "SOC低报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 4 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign4;

    @JsonIgnore
    @Excel(name = "单体电池过压报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 5 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign5;

    @JsonIgnore
    @Excel(name = "单体电池欠压报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 6 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign6;

    @JsonIgnore
    @Excel(name = "SOC过高报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 7 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign7;

    @JsonIgnore
    @Excel(name = "SOC跳变报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 8 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign8;

    @JsonIgnore
    @Excel(name = "可充电储能系统不匹配报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 9 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign9;

    @JsonIgnore
    @Excel(name = "电池单体一致性差报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 10 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign10;

    @JsonIgnore
    @Excel(name = "绝缘报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 11 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign11;

    @JsonIgnore
    @Excel(name = "DC-DC温度报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 12 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign12;

    @JsonIgnore
    @Excel(name = "制动系统报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 13 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign13;

    @JsonIgnore
    @Excel(name = "DC-DC状态报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 14 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign14;

    @JsonIgnore
    @Excel(name = "驱动电机控制器温度报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 15 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign15;

    @JsonIgnore
    @Excel(name = "高压互锁状态报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 16 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign16;

    @JsonIgnore
    @Excel(name = "驱动电机温度报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 17 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign17;

    @JsonIgnore
    @Excel(name = "车载储能装置类型过充报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 18 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign18;

    @JsonIgnore
    @Excel(name = "BMS高压电池错误报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 19 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign19;

    @JsonIgnore
    @Excel(name = "防盗报警",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,width=8,sort = 20 + EXCEL_EXPORT_SORT_START + 2)
    private String alarmSign20;
    //Excel导出报警字段__end

    /**
     * 可充电储能装置故障总数 N1;（N1 个可充电存储装置故障，有效值范围：0～252，“0xFE” 表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "可充电储能装置故障总数",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 32 + EXCEL_EXPORT_SORT_START + 2)
    private Integer essTotalFault;

    /**
     * 可充电储能装置故障代码列表4×N1;（可充电储能装置故障个数等于可充电储能装置故障总数 N1。见可充电储能装置故障代码列表。）
     */
    @Excel(name = "可充电储能装置故障代码列表",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 33 + EXCEL_EXPORT_SORT_START + 2)
    private String essFaultCodes;

    /**
     * 驱动电机故障总数N2;（N2个驱动电机故障，有效值范围：0～252，“0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "驱动电机故障总数",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 34 + EXCEL_EXPORT_SORT_START + 2)
    private Integer driveMotorTotalFault;

    /**
     * 驱动电机故障代码列表4×N2;（电机故障个数等于电机故障总数N2。 见驱动电机故障代码列表。）
     */
    @Excel(name = "驱动电机故障代码列表",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 35 + EXCEL_EXPORT_SORT_START + 2)
    private String driveMotorFaultCodes;

    /**
     * 发动机故障总数N3;（A2001 固定传 0。 因为汽车是电动汽车，无燃油发动机，所以无此项数据）
     */
    @Excel(name = "发动机故障总数",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 36 + EXCEL_EXPORT_SORT_START + 2)
    private Integer engineTotalFault;

    /**
     * 发动机故障列表;（无数据，因为汽车是电动汽车，无燃油发动机，所以无此项数据）
     */
    @Excel(name = "发动机故障列表",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 37 + EXCEL_EXPORT_SORT_START + 2)
    private String engineFaultCodes;

    /**
     * 其他故障总数N4;（N4个其他故障，有效值范围：0～252，“0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "其他故障总数",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 38 + EXCEL_EXPORT_SORT_START + 2)
    private Integer otherTotalFault;

    /**
     * 其他故障代码列表;（故障个数等于故障总数 N4 。见其他系统故障代码列表。）
     */
    @Excel(name = "其他故障代码列表",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 39 + EXCEL_EXPORT_SORT_START + 2)
    private String otherFaultCodes;

    /**
     * @param unparsedData 待解析的实体类（从数据库直接读取出来的数据）
     * @return 解析后的实体类
     */
    @Nullable
    public static VehicleAlertParsedDTO create(VehicleAlert unparsedData) {
        try {
            VehicleAlertParsedDTO dto = new VehicleAlertParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());

            dto.maxAlarmLevel = unparsedData.getMaxAlarmLevel();
            dto.generalAlarmSign = new LinkedHashMap<>();
            for (int i = 0; i < ALARM_SIGN_NAME.length; i++) {
                String value = ((unparsedData.getGeneralAlarmSign() >>> i) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
                dto.generalAlarmSign.put(ALARM_SIGN_NAME[i], value);
            }
            dto.alarmSign0 = ((unparsedData.getGeneralAlarmSign() >>> 0) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign1 = ((unparsedData.getGeneralAlarmSign() >>> 1) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign2 = ((unparsedData.getGeneralAlarmSign() >>> 2) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign3 = ((unparsedData.getGeneralAlarmSign() >>> 3) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign4 = ((unparsedData.getGeneralAlarmSign() >>> 4) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign5 = ((unparsedData.getGeneralAlarmSign() >>> 5) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign6 = ((unparsedData.getGeneralAlarmSign() >>> 6) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign7 = ((unparsedData.getGeneralAlarmSign() >>> 7) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign8 = ((unparsedData.getGeneralAlarmSign() >>> 8) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign9 = ((unparsedData.getGeneralAlarmSign() >>> 9) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign10 = ((unparsedData.getGeneralAlarmSign() >>> 10) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign11 = ((unparsedData.getGeneralAlarmSign() >>> 11) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign12 = ((unparsedData.getGeneralAlarmSign() >>> 12) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign13 = ((unparsedData.getGeneralAlarmSign() >>> 13) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign14 = ((unparsedData.getGeneralAlarmSign() >>> 14) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign15 = ((unparsedData.getGeneralAlarmSign() >>> 15) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign16 = ((unparsedData.getGeneralAlarmSign() >>> 16) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign17 = ((unparsedData.getGeneralAlarmSign() >>> 17) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign18 = ((unparsedData.getGeneralAlarmSign() >>> 18) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign19 = ((unparsedData.getGeneralAlarmSign() >>> 19) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;
            dto.alarmSign20 = ((unparsedData.getGeneralAlarmSign() >>> 20) & 0x01) == 1 ? ALARM_STRING : NORMAL_STRING;

            dto.essTotalFault = unparsedData.getEssTotalFault();
            dto.essFaultCodes = unparsedData.getEssFaultCodes() != null ? HexUtil.encodeHexStr(unparsedData.getEssFaultCodes(), false) : "";
            dto.driveMotorTotalFault = unparsedData.getDriveMotorTotalFault();
            dto.driveMotorFaultCodes = unparsedData.getDriveMotorFaultCodes() != null ? HexUtil.encodeHexStr(unparsedData.getDriveMotorFaultCodes(), false) : "";
            dto.engineTotalFault = unparsedData.getEngineTotalFault();
            dto.engineFaultCodes = unparsedData.getEngineFaultCodes() != null ? HexUtil.encodeHexStr(unparsedData.getEngineFaultCodes(), false) : "";
            dto.otherTotalFault = unparsedData.getOtherTotalFault();
            dto.otherFaultCodes = unparsedData.getOtherFaultCodes() != null ? HexUtil.encodeHexStr(unparsedData.getOtherFaultCodes(), false) : "";

            return dto;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
