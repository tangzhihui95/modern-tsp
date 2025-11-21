package com.modern.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置日志打印
 */
@Configuration
@ConditionalOnWebApplication
public class HttpTraceConfiguration {
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	static class ServletTraceFilterConfiguration {

		@Bean
		public HttpTraceLogFilter httpTraceLogFilter() {
			return new HttpTraceLogFilter();
		}
	}
}
