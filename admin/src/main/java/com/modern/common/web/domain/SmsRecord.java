package com.modern.common.web.domain;

import com.modern.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2021 脚印互动, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2021/1/4 下午6:33
 */
@ApiModel("数据实体 - 短信 - 短信记录")
@Data
public class SmsRecord extends BaseEntity {

	@ApiModelProperty("短信记录id")
	private Long id;

	@ApiModelProperty("手机号")
	private String mobile;

	@ApiModelProperty("内容")
	private String content;

	@ApiModelProperty("模板code")
	private String templateCode;

	@ApiModelProperty("产品名")
	private String production;

}

