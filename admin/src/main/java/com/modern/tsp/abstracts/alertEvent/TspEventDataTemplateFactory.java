package com.modern.tsp.abstracts.alertEvent;

import com.modern.common.utils.StringUtils;
import com.modern.common.utils.spring.SpringUtils;
import com.modern.tsp.abstracts.alertEvent.data.base.TspEventDataBaseService;
import com.modern.tsp.abstracts.alertEvent.data.impl.TspEventDataEssTemperatureService;
import com.modern.tsp.abstracts.alertEvent.data.impl.TspEventDataExtremeService;
import org.springframework.stereotype.Component;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/26 15:30
 */
@Component
public class TspEventDataTemplateFactory implements TspEventDataFactory {
    @Override
    public TspEventDataBaseService setFindType(String str) {
        if (StringUtils.isEmpty(str)){
            return null;
        }
        switch (str){
            case "温度差异报警":
                return SpringUtils.getBean(TspEventDataEssTemperatureService.class);
            case "电池高温报警":
                return SpringUtils.getBean(TspEventDataExtremeService.class);
        }
        return null;
    }
}
