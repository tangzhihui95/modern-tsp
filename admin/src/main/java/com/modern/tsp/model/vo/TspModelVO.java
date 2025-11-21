package com.modern.tsp.model.vo;

import com.modern.common.core.domain.BaseModel;
import com.modern.common.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 摩登 - TSP - TSP用户
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("标签管理 - 数据传输对象 - 详情信息")
public class TspModelVO extends BaseVO {

    private static final long serialVersionUID = 1L;


    /**
     * 短信模板标题
     */
    @ApiModelProperty(value = "短信模板标题")
    private String modelTitle;

    /**
     * 短信模板内容
     */
    @ApiModelProperty(value = "短信模板内容")
    private String modelContent;


}
