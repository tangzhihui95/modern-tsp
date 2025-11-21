package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;


/**
 * <p>
 * 摩登 - TSP - 车辆车型
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_vehicle_model",comment = "摩登 - TSP - 车辆车型")
@Alias("TspVehicleModel")
@TableName("tsp_vehicle_model")
public class TspVehicleModel extends BaseModel {

    private static final long serialVersionUID = 1L;


//    /**
//     * 车辆分类ID
//     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    @Column(comment = "车辆分类ID",type = MySqlTypeConstant.BIGINT,isNull = false)
//    private Long tspVehicleTypeId;


    /**
     * 一级车型
     */
    @Column(comment = "一级车型",type = MySqlTypeConstant.VARCHAR,length = 55)
    @Excel(name = "一级车型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 1)
    private String vehicleModelName;




    public static final String TSP_VEHICLE_TYPE_ID = "tsp_vehicle_type_id";

    public static final String VEHICLE_MODEL_NAME = "vehicle_model_name";

    public static final String PARENT_MODEL_ID = "parent_model_id";


}
