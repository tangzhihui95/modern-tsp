package com.modern.exInterface.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.modern.common.core.domain.BaseExModel;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 智源TBox设置数据
 */
@TableName(value = "vehicle_zhiyuan_tbox_settings")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ZhiYuanVehicleTboxSettings extends BaseExModel {
    /** 车载终端本地存储时间周期 0-60000ms */
//    @Pattern(regexp = "^(\\d{1,4}|[1-5]\\d{4}|60000|65534|65535)$")
    @Min(0) @Max(60000)
    private Integer storeCycle ;

    /** 正常时，信息上报时间周期 1-30s */
//    @Pattern(regexp = "^([1-9]|[1-2]\\d|30|65534|65535)$")
    @Min(1) @Max(30)
    private Integer sendInterval ;

    /** 出现报警时，信息上报时间周期 0-60000ms */
//    @Pattern(regexp = "^(\\d{1,4}|[1-5]\\d{4}|60000|65534|65535)$")
    @Min(0) @Max(60000)
    private Integer sendIntervalDuringAlarm ;

    /** 远程服务与管理平台域名长度m 0-255 */
    @Min(0) @Max(255)
    private Integer serverPlatformDomainNameLength ;

    /** 远程服务与管理平台域名 */
    @Size(min=0, max=1024)
    private String serverPlatformDomainName ;

    /** 远程服务与管理平台端口 0-65531*/
    @Min(0) @Max(65531)
    private Integer serverPlatformPort ;

    /** 硬件版本 */
    @Size(min=5, max=5)
    private String hardwareVersion ;

    /** 固件版本 */
    @Size(min=5, max=5)
    private String firmwareVersion ;

    /** 车载终端心跳发送周期 1-240s */
//    @Pattern(regexp = "^([1-9]|[1-9]\\d|[1-2][0-3]\\d|240|65534|65535)$")
    @Min(1) @Max(240)
    private Integer heartbeatInterval ;

    /** 终端应答超时时间 1-600s */
//    @Pattern(regexp = "^([1-9]|[1-9]\\d|[1-5]\\d{2}|600|65534|65535)$")
    @Min(1) @Max(600)
    private Integer responseTimeout ;

    /** 平台应答超时时间 1-600s */
//    @Pattern(regexp = "^([1-9]|[1-9]\\d|[1-5]\\d{2}|600|65534|65535)$")
    @Min(1) @Max(600)
    private Integer serverResponseTimeout ;

    /** 连续三次登入失败后，到下一次登入的间隔时间 1-240s */
//    @Pattern(regexp = "^([1-9]|[1-9]\\d|[1-2][0-3]\\d|240|65534|65535)$")
    @Min(1) @Max(240)
    private Integer loginIntervalAfterFailure ;

    /** 公共平台域名长度n 0-255 */
    @Min(0) @Max(255)
    private Integer nationalPlatformDomainNameLength ;

    /** 公共平台域名 */
    @Size(min=0, max=1024)
    private String nationalPlatformDomainName ;

    /** 公共平台端口 0-65531 */
    @Min(0) @Max(65531)
    private Integer nationalPlatformPort ;

    /** 是否处于抽样监测中 */
//    @Pattern(regexp = "^(1|2|254|255)$")
    @Min(1) @Max(2)
    private Integer whetherSamplingMonitoring ;

    /** http服务器域名或ip长度n */
    private Integer httpServerDomainNameLength ;
    /** http域名或ip */
    private String httpServerDomainName ;
    /** http端口号 */
    private Integer httpServerPort ;
    /** 设备注册接口连接地址长度m */
    private Integer deviceRegisteredAddressLength ;
    /** 设备注册接口连接地址 */
    private String deviceRegisteredAddress ;
    /** 车载储能装置类型 */
    private Integer energyStorageEquipmentType ;
    /** 冷却类型 */
    private Integer coolingType ;
}
