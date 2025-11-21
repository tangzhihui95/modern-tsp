package com.modern.web.domain.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("用户管理 - 请求对象 - 绑定车辆")
public class SysUserBindVehicleVO {


    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @NotNull(message = "所选车辆不能为空")
    @Length(min = 1,message = "所选车辆不能为空")
    @ApiModelProperty("车辆ID集合")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long[] vehicleIds;
}
