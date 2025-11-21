package com.modern.common.core.base.query.domain;

import lombok.Data;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/13 0:19
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
public class Query {
    private Class name;
    private List<Class> followName;
    private String join;
}
