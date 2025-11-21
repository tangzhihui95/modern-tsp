package com.modern.common.jiguang.service;

import com.modern.common.constant.ErrorEnum;
import com.modern.common.exception.ServiceException;
import com.modern.common.jiguang.domain.JiGuangMsgRecord;
import com.modern.common.jiguang.model.JiGuangMsgRecordSingleSendVO;
import com.modern.common.jiguang.repository.JiGuangMsgRecordRepository;
import com.modern.common.utils.JiGuangUtil;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 15:40
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JiGuangMsgRecordService {
    private final JiGuangMsgRecordRepository jiGuangMsgRecordRepository;
    @Autowired
    private RedissonClient redissonClient;


    @Async
    @Transactional(rollbackFor = ServiceException.class)
    public void sendJiGuangMsg(JiGuangMsgRecord msg) {
        RLock lock = redissonClient.getFairLock(String.valueOf(msg.getBusinessId()));
        if (!lock.isLocked()) {
            try {
                lock.lock(20, TimeUnit.SECONDS);
                msg.setCreateBy(SecurityUtils.getUsername());
                msg.setUpdateBy(SecurityUtils.getUsername());
                jiGuangMsgRecordRepository.save(msg);
                boolean b = JiGuangUtil.singleSend(msg);
                // TODO 判断消息是否发送成功标识
                if (b) {
                    msg.setIsSuccess(true);
                    msg.setUpdateBy(SecurityUtils.getUsername());
                    jiGuangMsgRecordRepository.updateById(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                lock.forceUnlock();
            }
        }
    }
}
