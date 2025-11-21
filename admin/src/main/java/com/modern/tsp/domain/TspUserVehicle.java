package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/29 11:27
 * @Version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_user_vehicle",comment = "摩登 - TSP - 账号/车辆历史绑定")
@Alias("TspUserVehicle")
@TableName("tsp_user_vehicle")
public class TspUserVehicle extends BaseModel {

    /**
     * 用户主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "用户主键", type = MySqlTypeConstant.BIGINT)
    private Long tspUserId;

    /**
     * 车辆主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆主键", type = MySqlTypeConstant.BIGINT)
    private Long tspVehicleId;
}
