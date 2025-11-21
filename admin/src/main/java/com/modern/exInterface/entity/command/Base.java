package com.modern.exInterface.entity.command;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 命令模块BaseDO
 */
@Data
public class Base implements Serializable {
    /**
     * 主键（雪花算法）
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    protected Long id;

    /**
     * VIN，应符合GB16735的规定
     */
    protected String vin;


    /**
     * 数据插入数据库时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 数据更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除;（1 表示删除，0 表示未删除）
     */
    @TableLogic
    private Boolean deleted;
}
