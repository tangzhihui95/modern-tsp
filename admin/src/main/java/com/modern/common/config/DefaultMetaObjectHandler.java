package com.modern.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.modern.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动注入数据库公共字段值
 */
@Component
public class DefaultMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        try {
            this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getUsername());
        } catch (Exception e) {
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        try {
            this.strictUpdateFill(metaObject, "updateBy", String.class, SecurityUtils.getUsername());
        } catch (Exception e) {
        }
    }
}
