package com.modern.tsp.abstracts.alertEvent.data.base;


import java.sql.Time;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/26 15:33
 */
public interface TspEventDataBaseService {

    public List<String> isAlert(Time monitorStartTime, Time monitorEndTime, String operator, String value);
}
