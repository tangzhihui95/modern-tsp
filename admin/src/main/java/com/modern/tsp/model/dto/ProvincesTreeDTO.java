package com.modern.tsp.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/8/26 10:42
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@Data
public class ProvincesTreeDTO {

    private String label;

    private String value;

    private List<ProvincesTreeDTO> children = new ArrayList<>();
}
