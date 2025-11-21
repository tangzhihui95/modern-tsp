package com.modern.common.utils.spring;

import com.modern.common.config.IpConfiguration;
import com.modern.common.enums.EnvironmentType;
import com.modern.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.Query;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @author piaomiao
 */
@Slf4j
@Component
public final class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {
	/**
	 * Spring应用上下文环境
	 */
	private static ConfigurableListableBeanFactory beanFactory;

	private static ApplicationContext applicationContext;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringUtils.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}

	private static Integer PORT = null;

	private static EnvironmentType environmentType;

	private static Boolean isDebug = false;

	public static String getHost() {
		return getEnvValue("airoffice.host");
	}

	public static String getWxauthHost() {
		return getEnvValue("airoffice.weixin.auth_host");
	}

	/**
	 * 获取对象
	 *
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws org.springframework.beans.BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) beanFactory.getBean(name);
	}

	/**
	 * 获取类型为requiredType的对象
	 *
	 * @param clz
	 * @return
	 * @throws org.springframework.beans.BeansException
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		T result = (T) beanFactory.getBean(clz);
		return result;
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 *
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 *
	 * @param name
	 * @return boolean
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 *
	 * @param name
	 * @return
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getAliases(name);
	}

	/**
	 * 获取aop代理对象
	 *
	 * @param invoker
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAopProxy(T invoker) {
		return (T) AopContext.currentProxy();
	}

	/**
	 * 获取当前的环境配置，无配置返回null
	 *
	 * @return 当前的环境配置
	 */
	public static String[] getActiveProfiles() {
		return applicationContext.getEnvironment().getActiveProfiles();
	}

	/**
	 * 获取当前的环境配置，当有多个环境配置时，只获取第一个
	 *
	 * @return 当前的环境配置
	 */
	public static String getActiveProfile() {
		final String[] activeProfiles = getActiveProfiles();
		return StringUtils.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
	}

	/**
	 * 获取port端口号
	 *
	 * @return
	 */
	public static int getServerPort() {
		IpConfiguration ip = getBean(IpConfiguration.class);
		int port = ip.getPort();
		return port;
	}

	public static Integer getPORT() {
		if (null != PORT && 0 != PORT && -1 != PORT) {
			return PORT;
		}
		int port = getServerPort();
		if (0 == port) {
			port = getHttpPort();
			if (-1 == port) {
				if (null != PORT) {
					throw new RuntimeException("获取端口错误,端口为0/-1");
				}
				log.warn("获取本地端口错误 - 服务启动中...");

			}
		}
		PORT = port;
		return PORT;
	}

	/**
	 * 获取服务地址
	 *
	 * @return
	 */
	public static String getServerAddress() throws UnknownHostException {
		String serverAddress = null;
		try {
			serverAddress = InetAddress.getLocalHost().getHostAddress() + ":" + getPORT();
		} catch (UnknownHostException e) {
			log.error("获取本地服务地址错误:: {}", e.getMessage());
			throw e;
		}
		log.debug("本机服务地址:: " + serverAddress);
		return serverAddress;
	}

	public static String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "12.0.0.1";
		}
	}

	public static int getHttpPort() {
		try {
			MBeanServer server;
			if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
				server = MBeanServerFactory.findMBeanServer(null).get(0);
			} else {
				log.error("no MBeanServer!");
				return -1;
			}

			Set names = server.queryNames(new ObjectName("Catalina:type=Connector,*"),
					Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

			Iterator iterator = names.iterator();
			if (iterator.hasNext()) {
				ObjectName name = (ObjectName) iterator.next();
				return Integer.parseInt(server.getAttribute(name, "port").toString());
			}
		} catch (Exception e) {
			log.error("getHttpPort", e);
		}
		return -1;
	}

	private static Environment getEnvironment() {
		return getBean(Environment.class);
	}

	public static String getEnvValue(String name) {
		return getEnvironment().getProperty(name);
	}

	public static EnvironmentType getEnvironmentType() {
		if (null == environmentType) {
			environmentType = EnvironmentType.valueOf(getEnvValue("spring.profiles.active").toUpperCase());
		}
		return environmentType;
	}

	public static Boolean getIsDebug() {
		if (EnvironmentType.LOCAL.equals(getEnvironmentType())) {
			return true;
		}
		return isDebug;
	}

	public static void setIsDebug(Boolean isDebug) {
		SpringUtils.isDebug = isDebug;
	}
}
