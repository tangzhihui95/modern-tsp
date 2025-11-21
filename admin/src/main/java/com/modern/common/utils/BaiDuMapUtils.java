package com.modern.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.modern.common.utils.baiduBean.AddressComponent;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/4 21:42
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

public class BaiDuMapUtils {

    public final static String BAIDU_AK = "zHCDMKmoqGgpZrtpwegV2ufM1kd0YE73";

    public static void main(String[] args) {
        AddressComponent component = getAddressInfoByLngAndLat("117.190182", "39.125596");
        System.out.println(component);
    }
    /**
     * 根据经纬度调用百度API获取 地理位置信息，根据经纬度
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    public static AddressComponent getAddressInfoByLngAndLat(String longitude, String latitude) {
        JSONObject obj = new JSONObject();
        String location = latitude + "," + longitude;
        //百度url  coordtype :bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度）
        String url = "https://api.map.baidu.com/reverse_geocoding/v3/?ak=" + BAIDU_AK + "&output=json&coordtype=wgs84ll&location=" + location;
        try {
            String json = loadJSON(url);
            obj = JSONObject.parseObject(json);
//            System.out.println(obj.toString());
            // status:0 成功
            String success = "0";
            String status = String.valueOf(obj.get("status"));
            if (success.equals(status)) {
                String result = String.valueOf(obj.get("result"));
                JSONObject resultObj = JSONObject.parseObject(result);
                String addressComponent = String.valueOf(resultObj.get("addressComponent"));
                //JSON字符串转换成Java对象
                AddressComponent addressComponentInfo = JSONObject.parseObject(addressComponent, AddressComponent.class);
                System.out.println("addressComponentInfo:" + addressComponent);
                return addressComponentInfo;
            }
        } catch (Exception e) {
            System.out.println("未找到相匹配的经纬度，请检查地址！");
        }
        return null;
    }

    @SneakyThrows
    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), StandardCharsets.UTF_8));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (IOException ignored) {
        }
        return json.toString();
    }
}
