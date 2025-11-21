package com.modern.tsp.abstracts.sendMsg;

import com.modern.common.utils.spring.SpringUtils;
import com.modern.tsp.abstracts.findData.data.find.TspVehicleBaseFindDataService;
import com.modern.tsp.abstracts.findData.data.impl.TspVehicleFindSevenDaysService;
import com.modern.tsp.abstracts.sendMsg.data.base.TspSendMsgBaseService;
import com.modern.tsp.abstracts.sendMsg.data.impl.TspAppSendMsgService;
import com.modern.tsp.abstracts.sendMsg.data.impl.TspDuanXinSendMsgService;
import com.modern.tsp.abstracts.sendMsg.data.impl.TspPlatformSendMsgService;
import com.modern.tsp.enums.TspVehicleFindDataStateEnum;
import org.springframework.stereotype.Component;


/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/19 12:12
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Component
public class TspTemplateSendMsgFactory implements TspSendMsgFactory {
    @Override
    public TspSendMsgBaseService setFindType(Integer state) {
        if (state == null) {
            return null;
        }
        switch (state){
            case 1 :
                return SpringUtils.getBean(TspPlatformSendMsgService.class);
            case 2 :
                return SpringUtils.getBean(TspDuanXinSendMsgService.class);
            case 3 :
                return SpringUtils.getBean(TspAppSendMsgService.class);
            default:
                return null;
        }
    }
}
