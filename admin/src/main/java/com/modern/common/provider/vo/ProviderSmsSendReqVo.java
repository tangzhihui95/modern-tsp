package com.modern.common.provider.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2021 脚印互动, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2022/1/14 下午5:00
 */
@ApiModel("请求对象 - 接口提供 - 短信 - 发送短信")
@Data
public class ProviderSmsSendReqVo {

	@ApiModelProperty("验证码, 不填发送随机,需要调接口验证")
	private String code;

	@ApiModelProperty(value = "手机号", required = true)
	@NotEmpty(message = "产品名称不能为空")
	private String mobile;

	@ApiModelProperty(value = "产品名称 产品名称_业务名称 IFA_APPLY", required = true)
	@NotEmpty(message = "产品名称不能为空")
	private String productionName;

}
