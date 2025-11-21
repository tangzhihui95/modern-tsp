package com.modern.tsp.move.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class MoveAlertInfoForm extends MoveAlertInfo{

    /**
     * 当前页
     */
    @TableField(exist = false)
    private Integer page;

    /**
     * 分页大小
     */
    @TableField(exist = false)
    private Integer pageSize;

}
