package com.modern.xtsp.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class TspVehicleSysRoleDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long vehicleId;
}
