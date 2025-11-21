package com.modern.common.exception;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2019 tianjiugongxiang, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author wangzhiliang
 * @since 2019-10-08 16:08
 */
public class NeedRetryException extends RuntimeException {
	public NeedRetryException(String message) {
		super(message);
	}
}
