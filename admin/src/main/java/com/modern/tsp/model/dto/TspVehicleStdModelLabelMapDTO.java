package com.modern.tsp.model.dto;

import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/20 20:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("车型 - 数据传输对象 - 车辆型号车型标签")
public class TspVehicleStdModelLabelMapDTO extends BaseDTO {

    private String date;
    private String count;
    private String modeNames;
}
