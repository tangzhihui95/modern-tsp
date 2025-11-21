package com.modern.exInterface.entity.command;

import lombok.*;

import java.time.LocalDateTime;

/**
 * vehicle_command
 *
 * @TableName vehicle_command
 */
//@TableName(value = "vehicle_command")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCommand extends Base {
    /**
     * 命令ID
     */
    protected Integer commandId;

    /**
     * 参数项列表
     */
    protected byte[] command;

    /**
     * 云端命令发送时间
     */
    protected LocalDateTime sendTime;

    /**
     * 车辆命令接收时间
     */
    protected LocalDateTime vehicleReceiveTime;

    /**
     * 车辆命令执行时间
     */
    protected  LocalDateTime vehicleExecuteTime;

    /**
     * 车辆命令执行结果
     */
    protected Integer vehicleExecuteResult;

}