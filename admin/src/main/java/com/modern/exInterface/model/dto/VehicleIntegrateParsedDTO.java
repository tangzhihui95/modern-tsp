package com.modern.exInterface.model.dto;

import cn.jiguang.common.utils.Nullable;
import com.google.common.collect.ImmutableMap;
import com.modern.common.annotation.Excel;
import com.modern.exInterface.entity.VehicleIntegrate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/1 17:04
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("(整车数据) - 数据传输对象 - 分页列表")
public class VehicleIntegrateParsedDTO extends BaseVehicleDataDTO {
    public static final Map<Integer, String> VEHICLE_STATE_MAP = ImmutableMap.<Integer, String>builder()
            .put(0x01, "启动").put(0x02, "熄火").put(0x03, "其他")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    public static final Map<Integer, String> CHARGE_STATE_MAP = ImmutableMap.<Integer, String>builder()
            .put(0x01, "停车充电").put(0x02, "行使充电").put(0x03, "未充电").put(0x04, "充电完成")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    public static final Map<Integer, String> RUN_MODE_MAP = ImmutableMap.<Integer, String>builder()
            .put(0x01, "纯电").put(0x02, "混动").put(0x03, "燃油")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    public static final Map<Integer, String> DC_DC_STATE_MAP = ImmutableMap.<Integer, String>builder()
            .put(0x01, "工作").put(0x02, "断开")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();
    public static final Map<Integer, String> GEAR_PART_MAP = ImmutableMap.<Integer, String>builder()
            .put(0b0000, "空挡").put(0b1101, "倒挡").put(0b1110, "D挡").put(0b1111, "P挡")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    /**
     * 车辆状态;（0x01：车辆启动状态；0x02：熄火；0x03：其他状态；0xFE：表示异常，0xFF：表示无效。）
     */
    @Excel(name = "车辆状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 1 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("车辆状态")
    private String vehicleState;

    /**
     * 充电状态;（0x01：停车充电；0x02：行使充电；0x03：未充电状态；0x04：充电完成；0xFE：表示异常，0xFF： 表示无效）
     */
    @Excel(name = "充电状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 2 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("充电状态")
    private String chargeState;

    /**
     * 运行模式;（0x01: 纯电；0x02：混动；0x03：燃油；0xFE：表示异常；0xFF：表示无效）
     */
    @Excel(name = "运行模式",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 3 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("运行模式")
    private String runMode;

    /**
     * 车速;（有效值范围：0～2200（表示 0 km/h～220 km/h），最小计量单元：0.1km/h，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "车速",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 4 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("车速")
    private String speed;

    /**
     * 里程;（有效值范围：0～9999999（表示 0km～999999.9km），最小计量单元：0.1km。“0xFF, 0xFF, 0xFF,0xFE” 表示异常，“0xFF,0xFF,0 xFF,0xFF”表示无效。）
     */
    @Excel(name = "里程",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 5 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("里程")
    private String mileage;

    /**
     * 总电压;（有效值范围：0～10000（表示 0 V～1000V），最小计量单元：0.1V，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "总电压",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 6 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("总电压")
    private String totalVoltage;

    /**
     * 总电流;（有效值范围：0～20000（偏移量 1000A，表示1000A～+1000A），最小计量单元：0.1A，“0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。）
     */
    @Excel(name = "总电流",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 7 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("总电流")
    private String totalCurrent;

    /**
     * 剩余电量;（有效值范围：0～100（表示 0%～100%），最小计量单元：1%，“0xFE”表示异常，“0xFF”表示无效。）
     */
    @Excel(name = "剩余电量",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 8 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("剩余电量")
    private Integer soc;

    /**
     * DC-DC 状态;（0x01：工作；0x02：断开，0xFE：表示异常，0xFF： 表示无效。）
     */
    @Excel(name = "DC-DC状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 9 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("DC-DC状态")
    private String dcDcState;

    /**
     * 档位
     */
    @Excel(name = "档位",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 10 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("档位")
    private String gear;

    @Excel(name = "制动力",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 11 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("制动力")
    private String gearBrakeForce;

    @Excel(name = "驱动力",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 12 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("驱动力")
    private String gearDriveForce;

    /**
     * 绝缘电阻;（有效范围 0~60000（表示 0KΩ~60000KΩ），最小计量单元：1KΩ ）
     */
    @Excel(name = "绝缘电阻",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 13 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("绝缘电阻")
    private Integer insulationResistance;

    /**
     * 加速踏板状态;（有效值范围:0~100( 表示 0 %~100%)，最小计量单元:1%，"0xFE"表示异常，"0xFF"表示无效）
     */
    @Excel(name = "加速踏板行程值",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 14 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("加速踏板行程值")
    private Integer acceleratorPedalPosition;

    /**
     * 制动踏板状态;（有效值范围:0-100 表示 0%-100%) ，最小计量单元:1% ,"0"表示制动关的状态;在无具体行程值情况下，用"0x55" 即"101"表示制动有效状态，"0xFE"表示异常， "0xFF"表示无效）
     */
    @Excel(name = "制动踏板状态",cellType = Excel.ColumnType.STRING,type = Excel.Type.EXPORT,sort = 15 + EXCEL_EXPORT_SORT_START)
    @ApiModelProperty("制动踏板状态")
    private Integer brakePedalPosition;

    /**
     * @param unparsedData 待解析的实体类（从数据库直接读取出来的数据）
     * @return 解析后的实体类
     */
    @Nullable
    public static VehicleIntegrateParsedDTO create(VehicleIntegrate unparsedData) {
        try {
            VehicleIntegrateParsedDTO dto = new VehicleIntegrateParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());

            // 车辆状态
            dto.vehicleState = VEHICLE_STATE_MAP.get(unparsedData.getVehicleState()) != null ? VEHICLE_STATE_MAP.get(unparsedData.getVehicleState()) : unparsedData.getVehicleState().toString();

            // 充电状态
            dto.chargeState = CHARGE_STATE_MAP.get(unparsedData.getChargeState()) != null ? CHARGE_STATE_MAP.get(unparsedData.getChargeState()) : unparsedData.getChargeState().toString();

            // 运行模式
            dto.runMode = RUN_MODE_MAP.get(unparsedData.getRunMode()) != null ? RUN_MODE_MAP.get(unparsedData.getRunMode()) : unparsedData.getRunMode().toString();

            // 车速
            dto.speed = df1.format(((double) unparsedData.getSpeed()) / 10);

            // 累计里程
            dto.mileage = df1.format((unparsedData.getMileage()) / 10);

            // 总电压
            dto.totalVoltage = df1.format((unparsedData.getTotalVoltage()) / 10);

            // 总电流
            dto.totalCurrent = df1.format(((double) unparsedData.getTotalCurrent()) / 10 - 1000);

            // SOC
            dto.soc = unparsedData.getSoc();

            // DC-DC状态
            dto.dcDcState = DC_DC_STATE_MAP.get(unparsedData.getDcDcState()) != null ? DC_DC_STATE_MAP.get(unparsedData.getDcDcState()) : unparsedData.getDcDcState().toString();

            // 档位
            dto.gear = GEAR_PART_MAP.get((unparsedData.getGear() & 0x0F)) != null ? GEAR_PART_MAP.get((unparsedData.getGear() & 0x0F)) : ((unparsedData.getGear() & 0x0F) + "挡");

            // 制动力
            dto.gearBrakeForce = ((unparsedData.getGear() >>> 4) & 0x01) == 1 ? HAVE_STRING : NOT_HAVE_STRING;

            // 驱动力
            dto.gearDriveForce = ((unparsedData.getGear() >>> 5) & 0x01) == 1 ? HAVE_STRING : NOT_HAVE_STRING;

            // 绝缘电阻
            dto.insulationResistance = unparsedData.getInsulationResistance();

            // 加速踏板行程值
            dto.acceleratorPedalPosition = unparsedData.getAcceleratorPedalPosition();

            // 制动踏板状态
            dto.brakePedalPosition = unparsedData.getBrakePedalPosition();

            return dto;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}
