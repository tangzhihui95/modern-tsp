package com.modern.common.utils.code;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/18 11:24
 * @Version 1.0.0
 */
public class ZTSecurityUtil {

    public static String toString(byte[] bytes) {
        return new String(bytes);
    }

    public static byte[] toBytes(String str) {
        return str.getBytes();
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte value : b) {
            String hex = Integer.toHexString(value & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexStringToByte(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return baKeyword;
    }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, StandardCharsets.UTF_8);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 将map格式化成key1=value1&key2=value2……（其中按自然排序）
     */
    public static String naturalSortingKV(JSONObject json) {
        StringBuilder sb = new StringBuilder();
        List<String> list = new LinkedList<>();
        json.forEach((k, v) -> list.add("&" + k + "=" + v));
        Collections.sort(list);// 自然排序
        list.forEach(sb::append);
        return sb.substring(1);
    }
}
