package com.modern.tsp.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 14:45
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("设备分类 - 请求对象 - 添加")
public class TspEquipmentTypeAddVO {

    @ApiModelProperty("设备分类ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long equipmentTypeId;

//    @Length(max = 12,message = "输入不能超过12位")
    @NotEmpty(message = "设备类型不能为空")
    @ApiModelProperty("设备类型")
    private String name;

//    @Length(max = 12,message = "输入不能超过12位")
//    @NotEmpty(message = "设备类型不能为空")
//    @ApiModelProperty("设备型号")
//    private String model;

    @ApiModelProperty("供应商")
    private String suppliers;

    @NotNull(message = "设备扩展信息不能为空")
    @ApiModelProperty("设备扩展信息")
    private String extraType;

    @ApiModelProperty("是否为终端设备")
    private Boolean isTerminal;
}
