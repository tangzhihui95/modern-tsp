package com.modern.common.utils.code;

import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/4 9:11
 * @Version 1.0.0
 */
public class ZTRSAECB {

    private static final Logger logger = LoggerFactory.getLogger(ZTRSAECB.class);
    private static final String ALGORITHM_NAME = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String ALGORITHM_TYPE = "RSA";
    static int MAX_ENCRYPT_BLOCK = 62;
    static int MAX_DECRYPT_BLOCK = 128;

    //接口解密私钥
    public static final byte[] PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIPCwHCiFgk89nxVr/sbfdJaYo/U31xcpMFgLT2s51EXsgvH5SOhQBEplrTaBnZDETXL7KbBwS3CUQf5aw+maa8hKPZW1K4XOawWrHsHf4sdhUlWk2cytZf35j6WhGgeilctVsn4Q3qE+RFI8Zym+s0BLZ/a8KuuBOMWCd7uSmPDAgMBAAECgYAP7Ek1U2E2XJetDu3ler0J3sJqf18+2jNO60CMY4jQ0/xAUDfwxlvntw7vbCm7wP8jcTF4NZIS5ZjFTe8SRRogUwvLsVjig/ob/ttc0/+IaYiXZF/4VmwV0l0e5HYIcV3+gfcZSfSH0r2wKQkbU5dTWiwaAyCnmL5lORD1WILrgQJBANWZ0LyQTAAbjqBVw/x5kfIHQUXig2OQLTpTleW99kGjq3CbGEVkKtc0HUHqByvF2G9CkvLJy4dx421l9ok5Ql0CQQCd6jW6Lw3kiz08seBv/UwkOC7zfcvQCZxwa1Az+kMUB8YdQ1i2C7sD9Xk9jN+LJ0jSEKsvsaXsdRGEwXAa2ByfAkEApgxsA5pdKpxBlQz3TZcoSLExIeXD5CtZrYT6+11gSYi1ptU62f7TevaNZAdNSc8EwQFNASa0bPexKANi0yxLFQJAG341UG70yj97r9+AJb9BzGSDuBzEqTmjJOdqFUKS/x/DXEDnZvpv2uaX7yFhogZ65SfFxg31x08yd5x9+g56HQJAEhV/z+G6wpPVtJM51DthUe8m48lQdW49qmHD8O/OTZrPsefc9ODcxins/gFGxGZmP+l7tRkTEjDZLYLVQvakFg==".getBytes();

    /**
     * 加密 RSA->BASE64
     */
    public static byte[] encrypt(byte[] content, byte[] publicKey) {
        try {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_TYPE);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(x509KeySpec));
            // 标识
            int offSet = 0;
            int contentLength = content.length;
            byte[] resultBytes = {};
            byte[] cache = {};
            while (contentLength - offSet > 0) {
                if (contentLength - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(content, offSet, MAX_ENCRYPT_BLOCK);
                    offSet += MAX_ENCRYPT_BLOCK;
                } else {
                    cache = cipher.doFinal(content, offSet, contentLength - offSet);
                    offSet = contentLength;
                }
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }
            return Base64Util.encode(resultBytes);
        } catch (Exception e) {
            logger.error("content {}", content);
            logger.error("publicKey {}", publicKey);
            logger.error("encrypt 异常", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 解密 BASE64->RSA
     */
    public static byte[] decrypt(byte[] base64Content, byte[] privateKey) {
        try {
            byte[] content = Base64Util.decode(base64Content);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_TYPE);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, keyFactory.generatePrivate(pkcs8KeySpec));

            // 标识
            int offSet = 0;
            int contentLength = content.length;
            byte[] resultBytes = {};
            byte[] cache;
            while (contentLength - offSet > 0) {
                if (contentLength - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK);
                    offSet += MAX_DECRYPT_BLOCK;
                } else {
                    cache = cipher.doFinal(content, offSet, contentLength - offSet);
                    offSet = contentLength;
                }
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }
            return resultBytes;
        } catch (Exception e) {
            logger.error("baseContent {}", base64Content);
            logger.error("privateKey {}", privateKey);
            logger.error("encrypt 异常", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String encode(String context, byte[] publicKey) {
        if (StringUtils.isEmpty(context)) {
            return null;
        }
        return ZTSecurityUtil.toString(ZTRSAECB.encrypt(context.getBytes(), Base64Util.decode(publicKey)));
    }

    public static String decode(String context, byte[] privateKey) {
        return ZTSecurityUtil.toString(ZTRSAECB.decrypt(context.getBytes(), Base64Util.decode(privateKey)));
    }

    public static void main(String[] args) {
        byte[] privateKey = ("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIPCwHCiFgk89nxVr/sbfdJaYo/U31xcpMFgLT2s51EXsgvH5SOhQBEplrTaBnZDETXL7KbBwS3CUQf5aw+maa8hKPZW1K4XOawWrHsHf4sdhUlWk2cytZf35j6WhGgeilctVsn4Q3qE+RFI8Zym+s0BLZ/a8KuuBOMWCd7uSmPDAgMBAAECgYAP7Ek1U2E2XJetDu3ler0J3sJqf18+2jNO60CMY4jQ0/xAUDfwxlvntw7vbCm7wP8jcTF4NZIS5ZjFTe8SRRogUwvLsVjig/ob/ttc0/+IaYiXZF/4VmwV0l0e5HYIcV3+gfcZSfSH0r2wKQkbU5dTWiwaAyCnmL5lORD1WILrgQJBANWZ0LyQTAAbjqBVw/x5kfIHQUXig2OQLTpTleW99kGjq3CbGEVkKtc0HUHqByvF2G9CkvLJy4dx421l9ok5Ql0CQQCd6jW6Lw3kiz08seBv/UwkOC7zfcvQCZxwa1Az+kMUB8YdQ1i2C7sD9Xk9jN+LJ0jSEKsvsaXsdRGEwXAa2ByfAkEApgxsA5pdKpxBlQz3TZcoSLExIeXD5CtZrYT6+11gSYi1ptU62f7TevaNZAdNSc8EwQFNASa0bPexKANi0yxLFQJAG341UG70yj97r9+AJb9BzGSDuBzEqTmjJOdqFUKS/x/DXEDnZvpv2uaX7yFhogZ65SfFxg31x08yd5x9+g56HQJAEhV/z+G6wpPVtJM51DthUe8m48lQdW49qmHD8O/OTZrPsefc9ODcxins/gFGxGZmP+l7tRkTEjDZLYLVQvakFg==").getBytes();
        byte[] publicKey = ("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDwsBwohYJPPZ8Va/7G33SWmKP1N9cXKTBYC09rOdRF7ILx+UjoUARKZa02gZ2QxE1y+ymwcEtwlEH+WsPpmmvISj2VtSuFzmsFqx7B3+LHYVJVpNnMrWX9+Y+loRoHopXLVbJ+EN6hPkRSPGcpvrNAS2f2vCrrgTjFgne7kpjwwIDAQAB").getBytes();
        String text = "{\n" +
                "\t\"VehicleLogginAddress\": \"湖南大学\",\n" +
                "\t\"VehicleSalesTime\": \"20221104143754\",\n" +
                "\t\"VehicleChannelName\": \"实体渠道\",\n" +
                "\t\"VehicleDataType\": \"2\",\n" +
                "\t\"VehicleType\": \"1\",\n" +
                "\t\"CardInfo\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"ICCID\": \"89860321352008700281\",\n" +
                "\t\t\t\"MSISDN\": \"14984988121\",\n" +
                "\t\t\t\"IspType\": \"0\",\n" +
                "\t\t\t\"IspCode\": \"1000\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"VehicleName\": \"纯电动轿车\",\n" +
                "\t\"VehicleStaffName\": \"胡吱吱\",\n" +
                "\t\"NewVehicleFlag\": \"2\",\n" +
                "\t\"VehicleSalesUpdateTime\": \"20221104143909\",\n" +
                "\t\"VehicleOrgName\": \"摩登汽车（盐城）有限公司\",\n" +
                "\t\"PlaceOfOriginOfVehicle\": \"1\",\n" +
                "\t\"FuelType\": \"3\",\n" +
                "\t\"LicensePlateNumber\": \"湘TEST2\",\n" +
                "\t\"VehicleDepartment\": \"摩登汽车有限公司\",\n" +
                "\t\"VehicleModel\": \"MODERN in\",\n" +
                "\t\"VehicleDepartureTime\": \"20221101000000\",\n" +
                "\t\"BodyColor\": \"黑色\",\n" +
                "\t\"VIN\": \"HJ4DAB7J6MN050016\",\n" +
                "\t\"VehicleChannelType\": \"1\",\n" +
                "\t\"VehicleStatus\": \"3\",\n" +
                "\t\"VehicleNum\": \"\"\n" +
                "}";
        // 加密
        String enText = ZTSecurityUtil.toString(encrypt(text.getBytes(), Base64Util.decode(publicKey)));
        System.out.println(enText);
        // 解密
        System.out.println(ZTSecurityUtil.toString(decrypt(enText.getBytes(), Base64Util.decode(privateKey))));
    }
}
