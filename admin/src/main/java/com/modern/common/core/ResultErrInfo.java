package com.modern.common.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2021 脚印互动, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2022/3/1 上午11:29
 */
@Data
public class ResultErrInfo<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("错误码 0正常 非0错误")
	private Integer errcode = 0;

	@ApiModelProperty("错误信息")
	private String errmsg = "success";

	@ApiModelProperty("是否有返回信息")
	private boolean hasInfo = false;

	@ApiModelProperty("返回信息, 等同于接口正常返回信息")
	private T info;

	public ResultErrInfo() {

	}
}
