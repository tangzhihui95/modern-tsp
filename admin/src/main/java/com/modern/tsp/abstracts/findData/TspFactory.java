package com.modern.tsp.abstracts.findData;

import com.modern.tsp.abstracts.findData.data.find.TspVehicleBaseFindDataService;
import com.modern.tsp.enums.TspVehicleFindDataStateEnum;
import org.springframework.stereotype.Component;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/19 11:37
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Component
public interface TspFactory {

    TspVehicleBaseFindDataService setFindType(TspVehicleFindDataStateEnum state);
}
