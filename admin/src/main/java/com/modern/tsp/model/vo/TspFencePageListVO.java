package com.modern.tsp.model.vo;

import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 16:39
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("电子围栏 - 请求对象 - 查询")
public class TspFencePageListVO extends BaseVO {

    /**
     * 围栏名称
     */
    @ApiModelProperty("围栏名称")
    private String fenceName;
}
