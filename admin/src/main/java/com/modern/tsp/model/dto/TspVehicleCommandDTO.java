package com.modern.tsp.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 下发指令的属性
 * vin 车架号
 * time 当前时间
 * id
 * commandId 命令id
 * command 发送的命令
 */
@Data
public class TspVehicleCommandDTO {
    private String vin;
    private LocalDateTime time;
    private Long id;
    private String commandId;
    private String command;
}
