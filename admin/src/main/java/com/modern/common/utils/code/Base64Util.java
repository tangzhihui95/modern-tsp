package com.modern.common.utils.code;

import java.util.Base64;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/4 10:29
 * @Version 1.0.0
 */
public class Base64Util {
    /**
     * BASE64 加密
     */
    public static byte[] encode(byte[] context) {
        return Base64.getEncoder().encode(context);
    }


    /**
     * BASE64 解密
     */
    public static byte[] decode(byte[] context) {
        return Base64.getDecoder().decode(context);
    }

    public static String encode(String context) {
        return ZTSecurityUtil.toString(encode(ZTSecurityUtil.toBytes(context)));
    }

    public static String decode(String context) {
        return ZTSecurityUtil.toString(decode(ZTSecurityUtil.toBytes(context)));
    }

    public static void main(String[] args) {
        System.out.println(decode("Q2x3QEZURkgkNEAyMDE5bg=="));
    }
}
