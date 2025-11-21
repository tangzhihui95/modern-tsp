package com.modern.tsp.abstracts.findData;

import com.modern.common.utils.spring.SpringUtils;
import com.modern.tsp.abstracts.findData.data.find.TspVehicleBaseFindDataService;
import com.modern.tsp.abstracts.findData.data.impl.*;
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
public class TspTemplateFactory implements TspFactory {
    @Override
    public TspVehicleBaseFindDataService setFindType(TspVehicleFindDataStateEnum state) {
        if (state == null) {
            return null;
        }
        switch (state){
            case SEVEN_DAYS :
                return SpringUtils.getBean(TspVehicleFindSevenDaysService.class);
            case FOURTEEN_DAYS:
                return SpringUtils.getBean(TspVehicleFindFourTeenDayService.class);
            case THIRTY_DAYS:
                return SpringUtils.getBean(TspVehicleFindThirtyDaysService.class);
            case THREE_MONTH:
                return SpringUtils.getBean(TspVehicleFindThreeMonthService.class);
            case SIX_MONTH:
                return SpringUtils.getBean(TspVehicleFindSixMonthService.class);
            case TWELVE_MONTH:
                return SpringUtils.getBean(TspVehicleFindTwelveMonthService.class);
            case THREE_YEAR:
                return SpringUtils.getBean(TspVehicleFindThreeYearService.class);
            case FIVE_YEAR:
                return SpringUtils.getBean(TspVehicleFindFiveYearService.class);
            default:
                return null;
        }
    }
}
