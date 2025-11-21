package com.modern.common.config;

import com.modern.common.constant.Constants;
import com.modern.common.interceptor.RepeatSubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用配置
 *
 * @author piaomiao
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer
{
    @Autowired
    private RepeatSubmitInterceptor repeatSubmitInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /** 本地文件上传路径 */
        registry.addResourceHandler(ProjectConfig.getProfile() + "/**")
                .addResourceLocations("file:" + ProjectConfig.getProfile() + "/");

        /** swagger配置 */
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        String property = System.getProperty("os.name");
        boolean isLinux = property != null && property.startsWith("Linux");
        if(isLinux){
            registry.addResourceHandler("/image/**").addResourceLocations("file:" + Constants.RESOURCE_PREFIX);
        }else{
            registry.addResourceHandler("/image/**").addResourceLocations("file:D://" );
        }
    }

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**").excludePathPatterns(ProjectConfig.getProfile() + "/**");
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {


        //每次调用registry.addMappin可以添加一个跨域配置，需要多个配置可以多次调用registry.addMapping
//        response.setHeader("Access-Control-Allow-Methods","*");
        registry.addMapping("/**")
                .allowedOriginPatterns("*") //放行哪些原始域
                .allowedMethods("*") //放行哪些请求方式
                .allowedHeaders("*") //放行哪些原始请求头部信息
                .exposedHeaders("*") //暴露哪些头部信息
                .allowCredentials(true) //是否发送 Cookie
                .maxAge(3600);
    }
}
