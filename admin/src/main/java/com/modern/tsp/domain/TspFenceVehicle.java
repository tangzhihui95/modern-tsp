package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import com.modern.tsp.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/19 16:39
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_fence_vehicle",comment = "摩登 - TSP - 电子围栏监控_车辆绑定")
@Alias("TspFenceVehicle")
@TableName(value = "tsp_fence_vehicle", autoResultMap = true)
@Data
public class TspFenceVehicle extends BaseModel {

    /**
     * 电子围栏主键
     */
    @Column(comment = "电子围栏主键",type = MySqlTypeConstant.BIGINT)
    private Long tspFenceId;

    /**
     * 车辆主键
     */
    @Column(comment = "车辆主键",type = MySqlTypeConstant.BIGINT)
    private Long tspVehicleId;
}
