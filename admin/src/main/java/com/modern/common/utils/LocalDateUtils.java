package com.modern.common.utils;

import com.alibaba.fastjson.JSONArray;
import org.springframework.format.datetime.DateFormatter;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2019 通联诚达, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2020/6/22 11:04 上午
 */
public class LocalDateUtils {

	public static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

	/**
	 * 系统默认时区
	 */
	private static final ZoneId ZONE = ZoneId.systemDefault();

	public static final DateTimeFormatter FORMAT_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter FORMAT_YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	public static final DateTimeFormatter FORMAT_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter FORMAT_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
	public static final DateTimeFormatter FORMAT_YYYYMM = DateTimeFormatter.ofPattern("yyyyMM");
	public static final DateTimeFormatter FORMAT_YYYYMMDDHHMM = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	public static final DateTimeFormatter FORMAT_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

//===========================数值定义============================
	/**
	 * 每天小时数
	 */
	public static final int HOURS_PER_DAY = 24;

	/**
	 * 每小时分钟数
	 */
	public static final int MINUTES_PER_HOUR = 60;

	/**
	 * 每分钟秒数
	 */
	public static final int SECONDS_PER_MINUTE = 60;

	/**
	 * 每秒毫秒数
	 */
	public static final int MILLISECOND_PER_SECONDS = 1000;

	/**
	 * 每天秒数
	 */
	public static final int SECONDS_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE;

	/**
	 * 每天毫秒数
	 */
	public static final int MILLISECOND_PER_DAY = SECONDS_PER_DAY * MILLISECOND_PER_SECONDS;

	//===========================异常定义============================
	/**
	 * 解析日期时异常
	 */
	public static final String PARSE_LOCAL_DATE_EXCEPTION = "Unable to obtain";

	//===========================正则定义============================

	/**
	 * 纯数字
	 */
	public static final Pattern NUMERIC_REGEX = Pattern.compile("[0-9]+");

	/**
	 * 字母开头
	 */
	public static final Pattern START_WITH_WORD_REGEX = Pattern.compile("^[A-Za-z].*");

	/**
	 * 中文
	 */
	public static final Pattern CHINESE_REGEX = Pattern.compile("[\u4E00-\u9FFF]");
	//===========================其他定义============================

	/**
	 * MonthDay 默认解析前缀
	 * 解析字符串需要加前缀，如"--12-03"
	 */
	public static final String MONTHDAY_FORMAT_PRE = "--";

	/**
	 * 中文
	 */
	public static final String ZH = "zh";

	/**
	 * 除夕，节日处理使用
	 */
	public static final String CHUXI = "CHUXI";

	/**
	 * 春节，节日处理使用
	 */
	public static final String CHUNJIE = "0101";


	//===========================常用方法============================


	public static Long toEpochSecond() {
		return toEpochSecond(LocalDateTime.now());
	}

	public static Long toEpochSecond(LocalDateTime localDateTime) {
		return localDateTime.toEpochSecond(ZONE_OFFSET);
	}


	/**
	 * 是否为中文语言环境
	 *
	 * @return boolean
	 */
	public static boolean isChineseLanguage() {
		return Locale.getDefault().getLanguage().equals(ZH);
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static LocalDateTime getCurrentTime() {
		return LocalDateTime.now();
	}

	/**
	 * 获取当前标准时间字符串
	 *
	 * @return
	 */
	public static String getCurrentTimeFormat() {
		return getCurrentTimeFormat(FORMAT_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取当前标准年月日字符串
	 * @return
	 */
	public static String getCurrentDateFormat() {
		return getCurrentTimeFormat(FORMAT_YYYY_MM_DD);
	}

	/**
	 * 获取格式化时间字符串
	 *
	 * @param dateTimeFormatter
	 * @return
	 */
	public static String getCurrentTimeFormat(DateTimeFormatter dateTimeFormatter) {
		return getCurrentTimeFormat(getCurrentTime(), dateTimeFormatter);
	}

	/**
	 * 获取当前标准最小时间 XXXX-XX-XX 00:00:00时间
	 * @return
	 */
	public static LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
	}

	/**
	 * 获取格式化时间字符串
	 *
	 * @param localDateTime
	 * @param dateTimeFormatter
	 * @return
	 */
	public static String getCurrentTimeFormat(LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter) {
		return dateTimeFormatter.format(localDateTime);
	}

	/**
	 * 获取指定天数后的最大日期
	 *
	 * @param days
	 * @return
	 */
	public static LocalDateTime getDateAfterMax(int days) {
		return plusDays(LocalDateTime.now(), days);
	}

	/**
	 * 获取指定月数后的最大日期
	 *
	 * @param mounth
	 * @return
	 */
	public static LocalDateTime getMonthAfterMax(int mounth) {
		return getMonthAfterMax(LocalDateTime.now(), mounth);
	}

	/**
	 * 获取指定月数后的最大日期
	 *
	 * @param mounth
	 * @return
	 */
	public static LocalDateTime getMonthAfterMax(LocalDateTime date, int mounth) {
		return plusMonths(date, mounth).toLocalDate().atTime(endTimeOfDay());
	}

	/**
	 * 获取指定月数后的最小日期
	 *
	 * @param mounth
	 * @return
	 */
	public static LocalDateTime getMonthAgoMin(int mounth) {
		return getMonthAgoMin(LocalDateTime.now(), mounth);
	}

	/**
	 * 获取指定月数后的最小日期
	 *
	 * @param mounth
	 * @return
	 */
	public static LocalDateTime getMonthAgoMin(LocalDateTime date, int mounth) {
		return minusMonths(date, mounth).toLocalDate().atTime(startTimeOfDay());
	}


//获取精确起始时间 00:00:00 - 23:59:59

	/**
	 * 一天开始时间 00:00:00
	 *
	 * @return LocalTime
	 */
	public static LocalTime startTimeOfDay() {
		return LocalTime.of(0, 0, 0);
	}

	/**
	 * 一天开始时间 23:59:59
	 *
	 * @return LocalTime
	 */
	public static LocalTime endTimeOfDay() {
		return LocalTime.of(23, 59, 59);
	}

	/**
	 * 昨天起始时间 即：昨天日期+00:00:00
	 *
	 * @return Date
	 */
	public static LocalDateTime startTimeOfDay(int days) {
		return LocalDate.now().plusDays(days).atTime(startTimeOfDay());
	}

	/**
	 * 昨天结束时间即：昨天日期+23:59:59
	 *
	 * @return Date
	 */
	public static LocalDateTime endTimeOfDay(int days) {
		return LocalDate.now().plusDays(days).atTime(endTimeOfDay());
	}

	/**
	 * 昨天起始时间 即：昨天日期+00:00:00
	 *
	 * @return Date
	 */
	public static LocalDateTime startTimeOfYesterday() {
		return LocalDate.now().minusDays(1).atTime(startTimeOfDay());
	}

	/**
	 * 昨天结束时间即：昨天日期+23:59:59
	 *
	 * @return Date
	 */
	public static LocalDateTime endTimeOfYesterday() {
		return LocalDate.now().minusDays(1).atTime(endTimeOfDay());
	}

	/**
	 * 今天起始时间 即：今天日期+00:00:00
	 *
	 * @return Date
	 */
	public static LocalDateTime startTimeOfToday() {
		return LocalDate.now().atTime(startTimeOfDay());
	}

	/**
	 * 今天结束时间即：今天日期+23:59:59
	 *
	 * @return Date
	 */
	public static LocalDateTime endTimeOfToday() {
		return LocalDate.now().atTime(endTimeOfDay());
	}

	/**
	 * 获date起始时间
	 *
	 * @param date
	 * @return Date
	 */
	public static LocalDateTime startTimeOfDate(LocalDateTime date) {
		return date.toLocalDate().atTime(startTimeOfDay());
	}

	/**
	 * 获取date结束时间
	 *
	 * @param date
	 * @return Date
	 */
	public static LocalDateTime endTimeOfDate(LocalDateTime date) {
		return date.toLocalDate().atTime(endTimeOfDay());
	}

	/**
	 * 获取指定年月的第一天起始时间
	 *
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static LocalDateTime startTimeOfSpecialMonth(int year, int month) {
		return LocalDate.of(year, month, 1).atTime(startTimeOfDay());
	}

	/**
	 * 获取指定年月的最后一天结束时间
	 *
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static LocalDateTime endTimeOfSpecialMonth(int year, int month) {
		return LocalDate.of(year, month, 1).atTime(endTimeOfDay());
	}

	/**
	 * 获取指定日期的起始时间
	 *
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @return Date
	 */
	public static LocalDateTime startTimeOfDate(int year, int month, int dayOfMonth) {
		return LocalDate.of(year, month, dayOfMonth).atTime(startTimeOfDay());
	}

	/**
	 * 获取指定日期的结束时间
	 *
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @return Date
	 */
	public static LocalDateTime endTimeOfDate(int year, int month, int dayOfMonth) {
		return LocalDate.of(year, month, dayOfMonth).atTime(endTimeOfDay());
	}

	/**
	 * 日期加操作
	 *
	 * @param temporal
	 * @param unit
	 * @param amountToAdd
	 * @return Temporal
	 */
	public static Temporal plus(Temporal temporal, TemporalUnit unit, long amountToAdd) {
		Objects.requireNonNull(temporal, "temporal");
		return temporal.plus(amountToAdd, unit);
	}

	/**
	 * date日期加操作
	 * @param date
	 * @param unit
	 * @param amountToAdd
	 * @return Date
	 */

	/**
	 * 日期减操作
	 *
	 * @param temporal
	 * @param unit
	 * @param amountToSubtract
	 * @return Temporal
	 */
	public static Temporal minus(Temporal temporal, TemporalUnit unit, long amountToSubtract) {
		Objects.requireNonNull(temporal, "temporal");
		return temporal.minus(amountToSubtract, unit);
	}

	/**
	 * 根据field修改属性
	 *
	 * @param temporal
	 * @param field
	 * @param newValue
	 * @return Temporal
	 */
	public static Temporal with(Temporal temporal, TemporalField field, long newValue) {
		Objects.requireNonNull(temporal, "temporal");
		return temporal.with(field, newValue);
	}

	public static LocalDateTime plusYears(LocalDateTime localDateTime, long amountToAdd) {
		return (LocalDateTime) plus(localDateTime, ChronoUnit.YEARS, amountToAdd);
	}

	public static LocalDate plusYears(LocalDate localDate, long amountToAdd) {
		return (LocalDate) plus(localDate, ChronoUnit.YEARS, amountToAdd);
	}

	public static LocalDateTime plusMonths(LocalDateTime localDateTime, long amountToAdd) {
		return (LocalDateTime) plus(localDateTime, ChronoUnit.MONTHS, amountToAdd);
	}

	public static LocalDate plusMonths(LocalDate localDate, long amountToAdd) {
		return (LocalDate) plus(localDate, ChronoUnit.MONTHS, amountToAdd);
	}

	public static LocalDateTime plusWeeks(LocalDateTime localDateTime, long amountToAdd) {
		return (LocalDateTime) plus(localDateTime, ChronoUnit.WEEKS, amountToAdd);
	}

	public static LocalDate plusWeeks(LocalDate localDate, long amountToAdd) {
		return (LocalDate) plus(localDate, ChronoUnit.WEEKS, amountToAdd);
	}

	public static LocalDateTime plusDays(LocalDateTime localDateTime, long amountToAdd) {
		return (LocalDateTime) plus(localDateTime, ChronoUnit.DAYS, amountToAdd);
	}

	public static LocalDate plusDays(LocalDate localDate, long amountToAdd) {
		return (LocalDate) plus(localDate, ChronoUnit.DAYS, amountToAdd);
	}

	public static LocalDateTime plusHours(LocalDateTime localDateTime, long amountToAdd) {
		return (LocalDateTime) plus(localDateTime, ChronoUnit.HOURS, amountToAdd);
	}

	public static LocalTime plusHours(LocalTime localTime, long amountToAdd) {
		return (LocalTime) plus(localTime, ChronoUnit.HOURS, amountToAdd);
	}

	public static LocalDateTime plusMinutes(LocalDateTime localDateTime, long amountToAdd) {
		return (LocalDateTime) plus(localDateTime, ChronoUnit.MINUTES, amountToAdd);
	}

	public static LocalTime plusMinutes(LocalTime localTime, long amountToAdd) {
		return (LocalTime) plus(localTime, ChronoUnit.MINUTES, amountToAdd);
	}

	public static LocalDateTime plusSeconds(LocalDateTime localDateTime, long amountToAdd) {
		return (LocalDateTime) plus(localDateTime, ChronoUnit.SECONDS, amountToAdd);
	}

	public static LocalTime plusSeconds(LocalTime localTime, long amountToAdd) {
		return (LocalTime) plus(localTime, ChronoUnit.SECONDS, amountToAdd);
	}

	public static LocalDateTime plusMillis(LocalDateTime localDateTime, long amountToAdd) {
		return (LocalDateTime) plus(localDateTime, ChronoUnit.MILLIS, amountToAdd);
	}

	public static LocalTime plusMillis(LocalTime localTime, long amountToAdd) {
		return (LocalTime) plus(localTime, ChronoUnit.MILLIS, amountToAdd);
	}

	public static LocalDateTime minusYears(LocalDateTime localDateTime, long amountToSubtract) {
		return (LocalDateTime) minus(localDateTime, ChronoUnit.YEARS, amountToSubtract);
	}

	public static LocalDate minusYears(LocalDate localDate, long amountToSubtract) {
		return (LocalDate) minus(localDate, ChronoUnit.YEARS, amountToSubtract);
	}

	public static LocalDateTime minusMonths(LocalDateTime localDateTime, long amountToAdd) {
		return (LocalDateTime) minus(localDateTime, ChronoUnit.MONTHS, amountToAdd);
	}

	public static LocalDateTime minusMonths(LocalDateTime localDateTime) {
		return (LocalDateTime) minus(localDateTime, ChronoUnit.MONTHS, 1);
	}

	public static LocalDateTime minusMonths() {
		return (LocalDateTime) minus(getCurrentTime(), ChronoUnit.MONTHS, 1);
	}

	public static LocalDate minusMonths(LocalDate localDate, long amountToSubtract) {
		return (LocalDate) minus(localDate, ChronoUnit.MONTHS, amountToSubtract);
	}

	public static LocalDateTime minusWeeks(LocalDateTime localDateTime, long amountToSubtract) {
		return (LocalDateTime) minus(localDateTime, ChronoUnit.WEEKS, amountToSubtract);
	}

	public static LocalDate minusWeeks(LocalDate localDate, long amountToSubtract) {
		return (LocalDate) minus(localDate, ChronoUnit.WEEKS, amountToSubtract);
	}

	public static LocalDateTime minusDays(LocalDateTime localDateTime, long amountToSubtract) {
		return (LocalDateTime) minus(localDateTime, ChronoUnit.DAYS, amountToSubtract);
	}

	public static LocalDate minusDays(LocalDate localDate, long amountToSubtract) {
		return (LocalDate) minus(localDate, ChronoUnit.DAYS, amountToSubtract);
	}

	public static LocalDateTime minusHours(LocalDateTime localDateTime, long amountToSubtract) {
		return (LocalDateTime) minus(localDateTime, ChronoUnit.HOURS, amountToSubtract);
	}

	public static LocalTime minusHours(LocalTime localTime, long amountToSubtract) {
		return (LocalTime) minus(localTime, ChronoUnit.HOURS, amountToSubtract);
	}

	public static LocalDateTime minusMinutes(LocalDateTime localDateTime, long amountToSubtract) {
		return (LocalDateTime) minus(localDateTime, ChronoUnit.MINUTES, amountToSubtract);
	}

	public static LocalTime minusMinutes(LocalTime localTime, long amountToSubtract) {
		return (LocalTime) minus(localTime, ChronoUnit.MINUTES, amountToSubtract);
	}

	public static LocalDateTime minusSeconds(LocalDateTime localDateTime, long amountToSubtract) {
		return (LocalDateTime) minus(localDateTime, ChronoUnit.SECONDS, amountToSubtract);
	}

	public static LocalTime minusSeconds(LocalTime localTime, long amountToSubtract) {
		return (LocalTime) minus(localTime, ChronoUnit.SECONDS, amountToSubtract);
	}

	public static LocalDateTime minusMillis(LocalDateTime localDateTime, long amountToSubtract) {
		return (LocalDateTime) minus(localDateTime, ChronoUnit.MILLIS, amountToSubtract);
	}

	public static LocalTime minusMillis(LocalTime localTime, long amountToSubtract) {
		return (LocalTime) minus(localTime, ChronoUnit.MILLIS, amountToSubtract);
	}

	/**
	 * Timestamp转LocalDateTime
	 *
	 * @param timestamp
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
		Objects.requireNonNull(timestamp, "timestamp");
		return timestamp.toLocalDateTime();
	}

	/**
	 * LocalDate转LocalDateTime
	 *
	 * @param localDate
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(LocalDate localDate) {
		Objects.requireNonNull(localDate, "localDate");
		return localDate.atStartOfDay();
	}

	/**
	 * LocalTime转LocalDateTime
	 * 以当天的日期+LocalTime组成新的LocalDateTime
	 *
	 * @param localTime
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(LocalTime localTime) {
		Objects.requireNonNull(localTime, "localTime");
		return LocalDate.now().atTime(localTime);
	}

	/**
	 * Instant转LocalDateTime
	 *
	 * @param instant
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(Instant instant) {
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	/**
	 * 时间戳epochMilli毫秒转LocalDateTime
	 *
	 * @param epochMilli
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(long epochMilli) {
		Objects.requireNonNull(epochMilli, "epochMilli");
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault());
	}

	public static LocalDateTime epochSecondtoLocalDateTime(Long epochMilli) {
		Objects.requireNonNull(epochMilli, "epochMilli");
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochMilli), ZoneId.systemDefault());
	}

	public static LocalDateTime parseToLocalDateTime(Object text, DateTimeFormatter formatter) {
		return parseToLocalDateTime((String) text, formatter);
	}

	/**
	 * 根据 formatter解析为 LocalDateTime
	 *
	 * @param text
	 * @param formatter
	 * @return LocalDateTime
	 */
	public static LocalDateTime parseToLocalDateTime(String text, DateTimeFormatter formatter) {
		Objects.requireNonNull(formatter, "formatter");
		LocalDateTime localDateTime = null;
		try {
			localDateTime = toLocalDateTime(formatter.parse(text));
		} catch (DateTimeException e) {
			if (e.getMessage().startsWith(PARSE_LOCAL_DATE_EXCEPTION)) {
				localDateTime = toLocalDateTime(LocalDate.parse(text, formatter));
			} else {
				throw e;
			}
		}
		return localDateTime;
	}

	/**
	 * 根据 dateFormatPattern解析为 LocalDateTime
	 *
	 * @param text
	 * @param dateFormatPattern
	 * @return LocalDateTime
	 */
	public static LocalDateTime parseToLocalDateTime(String text, String dateFormatPattern) {
		Objects.requireNonNull(dateFormatPattern, "dateFormatPattern");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern).withZone(ZONE);
		return parseToLocalDateTime(text, formatter);
	}

	/**
	 * Date转LocalDate
	 *
	 * @param date
	 * @return LocalDate
	 */
	public static LocalDate toLocalDate(Date date) {
		return toLocalDateTime(date).toLocalDate();
	}

	/**
	 * LocalDateTime转LocalDate
	 *
	 * @param localDateTime
	 * @return LocalDate
	 */
	public static LocalDate toLocalDate(LocalDateTime localDateTime) {
		Objects.requireNonNull(localDateTime, "localDateTime");
		return localDateTime.toLocalDate();
	}

	/**
	 * Instant转LocalDate
	 *
	 * @param instant
	 * @return LocalDate
	 */
	public static LocalDate toLocalDate(Instant instant) {
		return toLocalDateTime(instant).toLocalDate();
	}

	/**
	 * 时间戳epochMilli毫秒转LocalDate
	 *
	 * @param epochMilli
	 * @return LocalDate
	 */
	public static LocalDate toLocalDate(long epochMilli) {
		Objects.requireNonNull(epochMilli, "epochMilli");
		return toLocalDateTime(epochMilli).toLocalDate();
	}

	/**
	 * Date转LocalDateTime
	 *
	 * @param date
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		Objects.requireNonNull(date, "date");
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * 时间戳epochMilli毫秒转LocalDateTime
	 *
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(TemporalAccessor temporal) {
		return LocalDateTime.from(temporal);
	}

	/**
	 * LocalDateTime转LocalTime
	 *
	 * @param localDateTime
	 * @return LocalTime
	 */
	public static LocalTime toLocalTime(LocalDateTime localDateTime) {
		Objects.requireNonNull(localDateTime, "localDateTime");
		return localDateTime.toLocalTime();
	}

	/**
	 * 获取2个日期的相差总秒数
	 * 第二个值减去第一个值
	 *
	 * @param startInclusive
	 * @param endExclusive
	 * @return long
	 */
	public static long betweenTotalSeconds(LocalDateTime startInclusive, LocalDateTime endExclusive) {
		return Duration.between(startInclusive, endExclusive).getSeconds();
	}

	/**
	 * 获取2个日期的相差总秒数
	 *
	 * @param startInclusive
	 * @param endExclusive
	 * @return long
	 */
	public static Long betweenTotalSeconds(LocalTime startInclusive, LocalTime endExclusive) {
		return Duration.between(startInclusive, endExclusive).getSeconds();
	}

	/**
	 * 获取2个日期的相差天数
	 *
	 * @param startInclusive
	 * @param endExclusive
	 * @return
	 */
	public static Long betweenTotalDays(LocalDateTime startInclusive, LocalDateTime endExclusive) {
		return Duration.between(startInclusive, endExclusive).toDays();
	}

	/**
	 * 计算两个时间相差的月份
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer getDifMonth(Date startDate, Date endDate){
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(startDate);
		end.setTime(endDate);
		int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
		int month = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
		return Math.abs(month + result);
	}

	/**
	 * 今天剩余时间
	 *
	 * @return
	 */
	public static Long timeRemainingForToday() {
		return LocalDateUtils.betweenTotalSeconds(LocalDateUtils.getCurrentTime(), LocalDateUtils.endTimeOfToday());
	}
}
