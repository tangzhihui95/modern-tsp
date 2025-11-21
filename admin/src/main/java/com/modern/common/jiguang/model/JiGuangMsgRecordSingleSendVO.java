package com.modern.common.jiguang.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/27 10:52
 */
@Data
@ApiModel("极光消息推送 - 请求对象 - 单个消息推送")
public class JiGuangMsgRecordSingleSendVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("业务ID")
    private Long businessId;

    @ApiModelProperty("推送源")
    private String pushSource;

    @ApiModelProperty("目标源")
    private String targetSource;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("用户ID")
    private Long tspUserId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @TableField(typeHandler = FastjsonTypeHandler.class, value = "extras")
    @ApiModelProperty("附加内容")
    private Map<String,String> extras;
}
