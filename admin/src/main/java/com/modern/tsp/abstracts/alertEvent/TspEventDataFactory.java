package com.modern.tsp.abstracts.alertEvent;

import com.modern.tsp.abstracts.alertEvent.data.base.TspEventDataBaseService;
import org.springframework.stereotype.Component;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/26 15:30
 */
@Component
public interface TspEventDataFactory {

    TspEventDataBaseService setFindType(String str);
}
