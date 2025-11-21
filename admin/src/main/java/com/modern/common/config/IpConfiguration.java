package com.modern.common.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2019 tonglianchengda, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2020/2/12 12:16 上午
 */
@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {

	private int serverPort;

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		this.serverPort = event.getWebServer().getPort();
	}

	public int getPort() {
		return this.serverPort;
	}
}
