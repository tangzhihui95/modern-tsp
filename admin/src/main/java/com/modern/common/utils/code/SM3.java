package com.modern.common.utils.code;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.UnsupportedEncodingException;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/18 11:25
 * @Version 1.0.0
 */
public class SM3 {

    /**
     * sm3算法加密
     *
     * @param text 待加密字符串
     * @return 返回加密后，固定长度=32的16进制字符串
     * @explain
     */
    public static String encode(String text) {
        // 将返回的hash值转换成16进制字符串
        String resultHexString = "";
        try {
            // 将字符串转换成byte数组
            byte[] context= text.getBytes("UTF-8");
            // 调用hash()
            byte[] resultHash = encode(context);
            // 将返回的hash值转换成16进制字符串
            resultHexString = ByteUtils.toHexString(resultHash);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        resultHexString = resultHexString.toUpperCase();
        return resultHexString;
    }


    public static byte[] encode(byte[] context) {
        SM3Digest sm3 = new SM3Digest();
        sm3.update(context, 0, context.length);
        byte[] hash = new byte[sm3.getDigestSize()];
        sm3.doFinal(hash, 0);
        return hash;
    }

    public static void main(String[] args) {
        String text = "123sdfq";
        System.out.println(encode(text));
        // 48AE4D7348BAB584C92DA40D89A141ACDB91E648F88825CC9C2363DC24A36E8C
    }
}
