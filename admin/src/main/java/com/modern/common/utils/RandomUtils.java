package com.modern.common.utils;

import java.util.Random;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/17 15:31
 * @Version 1.0.0
 */
public class RandomUtils {

    /**
     * 得到随机位数的数字字符串
     * @param length
     * @return
     */
    public static String getRequestId(int length) {
        // 定义字符串范围
        String number = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(10);
            sb.append(number.charAt(index));
        }
        return sb.toString();
    }
}
