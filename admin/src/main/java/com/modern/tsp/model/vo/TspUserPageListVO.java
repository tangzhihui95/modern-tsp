package com.modern.tsp.model.vo;

import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/15 17:52
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户 - 请求对象 - 分页列表")
public class TspUserPageListVO extends BaseVO {

    /**
     * 手机号(账号)
     */
    @ApiModelProperty("手机号")
    private String mobile;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("需要导出的id集合")
    private List<Long> ids;

}
