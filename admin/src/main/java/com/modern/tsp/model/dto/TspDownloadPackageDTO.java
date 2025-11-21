package com.modern.tsp.model.dto;

import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/8/16 11:57
 */
@Data
public class TspDownloadPackageDTO {

    private String topic;
    private int qos;
    private String message;
    private String imei;
}
