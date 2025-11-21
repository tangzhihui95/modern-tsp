package com.modern.tsp.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map.Entry;
import java.util.Objects;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/8/16 17:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("统计分析 - 数据传输对象 - 车辆告警统计")
public class TspVehicleAlertDataDTO {

    @ApiModelProperty("报警名称")
    private String alert;

    @ApiModelProperty("报警数量")
    private Integer sale;

    public TspVehicleAlertDataDTO(Entry<String,Long> entry){
        if(Objects.isNull(entry)){
            this.alert="其他";
        }else{
            this.alert = entry.getKey();
            this.sale = entry.getValue()==null?0:entry.getValue().intValue();
        }
    }
}
