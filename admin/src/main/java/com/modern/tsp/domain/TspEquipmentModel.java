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
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/15 11:34
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
@Table(name = "tsp_equipment_model",comment = "摩登 - TSP - 设备型号")
@Alias("TspEquipmentModel")
@TableName(value = "tsp_equipment_model",autoResultMap = true)
public class TspEquipmentModel extends BaseModel {

    /**
     * 设备分类ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "设备分类ID",type = MySqlTypeConstant.BIGINT,length = 22,isNull = false)
    private Long tspEquipmentTypeId;


    /**
     * 设备名称
     */
    @Column(comment = "型号名称",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    private String modelName;
    /**
     * 供应商
     */
    @Column(comment = "供应商",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String suppliers;
    /**
     * 生产批次
     */
    @Column(comment = "生产批次",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String batchNumber;

}
