package com.modern.common.utils;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2019 tonglianchengda, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2020/3/22 12:06 上午
 */
@Slf4j
public class UrlUtils {

	private static final String RE_TOP_DOMAIN = "(vip|com\\.cn|net\\.cn|gov\\.cn|org\\.nz|org\\.cn|com|net|org|gov|cc|biz|info|cn|co|me|localhost)";

	private static final Pattern PATTEN_IP = Pattern.compile("((http://)|(https://))?((\\d+\\.){3}(\\d+))");

	public static String getDomainLevelName(String url, int level) {
		String domain = getDomain(url, level);
		if (StringUtil.isEmpty(domain)) {
			return null;
		}
		String[] split = domain.split("\\.");
		if (split.length < (level + 1)) {
			return null;
		}
		String name = split[split.length - (level + 1)];

		return name;
	}

	public static String getLoastDomainLevelName(String url, int level) {
		String domain = getDomain(url, level);
		if (StringUtil.isEmpty(domain)) {
			return null;
		}
		String[] split = domain.split("\\.");
		String name = split[0];

		return name;
	}

	public static String getDomain(String url, int level) {
		log.info("getDomain:: url -> {}", url);
		Matcher matcher = PATTEN_IP.matcher(url);
		if (matcher.find()) {
			return matcher.group(4);
		}

		matcher = Pattern.compile("([\\w-]*\\.?){" + level + "}\\." + RE_TOP_DOMAIN).matcher(url);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

}
