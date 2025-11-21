package com.modern.common.jiguang.repository;

import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.jiguang.domain.JiGuangMsgRecord;
import com.modern.common.jiguang.mapper.JiGuangMsgRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JiGuangMsgRecordRepository extends ServicePlusImpl<JiGuangMsgRecordMapper, JiGuangMsgRecord, JiGuangMsgRecord> {
}
