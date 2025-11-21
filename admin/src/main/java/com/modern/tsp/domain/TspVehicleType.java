package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;


/**
 * <p>
 * 摩登 - TSP - 车辆分类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Alias("TspVehicleType")
@Table(name = "tsp_vehicle_type",comment = "摩登 - TSP - 车辆分类")
@TableName("tsp_vehicle_type")
public class TspVehicleType extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 车辆分类名称
     */
    @Column(comment = "车辆分类名称",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String typeName;



    public static final String TYPE_NAME = "type_name";



}
