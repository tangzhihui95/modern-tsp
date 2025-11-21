package com.modern.xtsp.domain.vo;

import com.modern.exInterface.entity.ZhiYuanVehicleTboxSettings;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class ZhiYuanVehicleTboxSettingsVO extends ZhiYuanVehicleTboxSettings {

    private String password;

}
