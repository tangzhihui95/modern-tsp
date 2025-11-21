package com.modern.xtsp.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.HexUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class DataUtil {
    private static final ZoneId ZONE_ID = ZoneId.of("GMT+8");

    public static LocalDateTime bytesToDate(byte[] bytes, int start) {
        /*
        bytes时间均应采用 GMT+8 时间
        数据表示内容 长度/字节 数据类型 有效值范围
        年 1 BYTE 0~99
        月 1 BYTE 1~12
        日 1 BYTE 1~31
        时 1 BYTE 0~23
        分 1 BYTE 0~59
        秒 1 BYTE 0~59
        */
        return LocalDateTime.of(bytes[start] + 2000, bytes[start + 1], bytes[start + 2],
                bytes[start + 3], bytes[start + 4], bytes[start + 5]);
    }

    public static byte[] dateToBytes(LocalDateTime localDateTime) {
        /*
        bytes时间均应采用 GMT+8 时间
        数据表示内容 长度/字节 数据类型 有效值范围
        年 1 BYTE 0~99
        月 1 BYTE 1~12
        日 1 BYTE 1~31
        时 1 BYTE 0~23
        分 1 BYTE 0~59
        秒 1 BYTE 0~59
        */
        byte[] dateBytes = new byte[6];
        dateBytes[0] = (byte) (localDateTime.getYear() % 100);
        dateBytes[1] = (byte) localDateTime.getMonthValue();
        dateBytes[2] = (byte) localDateTime.getDayOfMonth();
        dateBytes[3] = (byte) localDateTime.getHour();
        dateBytes[4] = (byte) localDateTime.getMinute();
        dateBytes[5] = (byte) localDateTime.getSecond();
        return dateBytes;
    }

    /**
     * @param localDateTime
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return LocalDateTimeUtil.format(localDateTime, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * @param localDateTime
     * @return yyyy-MM-dd
     */
    public static String formatLocalDate(LocalDateTime localDateTime) {
        return LocalDateTimeUtil.format(localDateTime, DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * @param strDateTime yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String strDateTime) {
        return LocalDateTimeUtil.parse(strDateTime, DatePattern.NORM_DATETIME_PATTERN);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE_ID);
    }

    public static byte[] nowBytes() {
        return dateToBytes(now());
    }

    private static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date1.toLocalDate().equals(date2.toLocalDate());
    }

    public static LocalDateTime epochMilliToLocalDateTime(long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);
        return LocalDateTime.ofInstant(instant, ZONE_ID);
    }

    /**
     * List<Byte>转byte[]
     *
     * @param oBytes
     * @return 转换后byte[]或new byte[0]
     */
    public static byte[] toPrimitive(List<Byte> oBytes) {
        if (oBytes == null || oBytes.size() == 0) {
            return new byte[0];
        }

        byte[] bytes = new byte[oBytes.size()];

        for (int i = 0; i < oBytes.size(); i++) {
            bytes[i] = oBytes.get(i);
        }

        return bytes;
    }
}
