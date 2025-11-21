package com.modern.tsp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/20 17:41
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 * 通用文件上传实体类  通过读取yml配置信息上传文件或图片，需要定义路径
 */
@Data
@Component
@ConfigurationProperties(prefix = "imgs")
public class TspUploadConfig {
    /**
     * 销售信息
     */
    private String salePath;
    /**
     * 上牌信息
     */
    private String upPlatePath;
    /**
     * 认证信息
     */
    private String auditPath;
    /**
     * 用户身份证正反面
     */
    private String userPath;
}
