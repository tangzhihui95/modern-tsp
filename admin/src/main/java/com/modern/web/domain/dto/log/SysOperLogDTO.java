package com.modern.web.domain.dto.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/8/29 11:45
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@ApiModel("操作日志 - 请求对象 - 系统模块下拉列表")
public class SysOperLogDTO {

    @ApiModelProperty("标题")
    private String title;
}
