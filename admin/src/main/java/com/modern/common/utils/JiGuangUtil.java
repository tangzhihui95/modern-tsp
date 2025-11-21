package com.modern.common.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.modern.common.jiguang.domain.JiGuangMsgRecord;
import com.modern.common.jiguang.model.JiGuangMsgRecordSingleSendVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/27 15:35
 */

@Slf4j
@Component
public class JiGuangUtil {

    @Autowired
    @Value("${jiguang.masterSecret}")
    private static String masterSecret;


    @Autowired
    @Value("${jiguang.appKey}")
    private static String appKey;

    /**
     * 单个消息推送
     */
    public static boolean singleSend(JiGuangMsgRecord vo) {
        PushPayload pushPayload = buildPushObject_android_ios_byAlias(vo);
        JPushClient client = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
        try {
            log.info("极光消息开始推送:{}", "业务ID：" + vo.getBusinessId() + "============ 标题：" + vo.getTitle());
            client.sendPush(pushPayload);
            log.info("极光消息推送完成:{}", "业务ID：" + vo.getBusinessId() + "============ 标题：" + vo.getTitle());
            return true;
        } catch (APIConnectionException | APIRequestException e) {
            log.error("极光消息推送异常:{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    public static PushPayload buildPushObject_android_ios_byAlias(JiGuangMsgRecord vo) {
        String content = vo.getContent();
        String title = vo.getTitle();
        Map<String, String> extras = vo.getExtras();
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios()).setAudience(Audience.alias(vo.getRegistrationId()))
                .setNotification(Notification.newBuilder().setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtras(extras).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
    }

    /**
     * 全平台推送
     */
//    public void jPushAll(Map<String, String> parm, List<String> list) {
//        JPushClient client = new JPushClient(masterSecret, appKey);
//        PushPayload newBuilder = PushPayload.newBuilder()
//                // 所有平台得用户
//                .setPlatform(Platform.all())
//                // 指定用户
//                .setAudience(Audience.alias(list))
//                .setNotification(Notification.newBuilder()
//                        //ios推送
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                .setAlert(IosAlert.newBuilder().setTitleAndBody(parm.get("title"), null, parm.get("body")).build())
//                                .setBadge(+1)
//                                .setSound("qxgf")
//                                .addExtras(parm)
//                                .build())
//
//                        //Android推送 也可以分开写
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//                                .setTitle(parm.get("title"))
//                                .addExtras(parm)
//                                .setAlert(parm.get("body"))
//                                .build())
//                        .build())
//                //指定开发环境
//                .setOptions(Options.newBuilder().setApnsProduction(false).build())
//                //自定义信息
//                .setMessage(Message.newBuilder().setMsgContent(parm.get("title")).addExtras(parm).build())
//                .build();
//        try {
//            PushResult pushResult = client.sendPush(newBuilder);
//            System.out.println(pushResult);
//            if (pushResult.getResponseCode() == 200) {
//                System.out.println(pushResult.getResponseCode());
//            }
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//        } catch (APIRequestException e) {
//            e.printStackTrace();
//        }
//    }
}
