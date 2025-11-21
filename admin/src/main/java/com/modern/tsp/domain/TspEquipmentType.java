package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
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

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 摩登 - TSP - 设备分类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */

@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
@Table(name = "tsp_equipment_type",comment = "摩登 - TSP - 设备类型")
@Alias("TspEquipmentType")
@TableName(value = "tsp_equipment_type",autoResultMap = true)
public class TspEquipmentType extends BaseModel{

    private static final long serialVersionUID = 1L;

    /**
     * 设备类型
     */
    @Column(comment = "设备类型",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    @Excel(name = "设备类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 1)
    private String name;
    /**
     * 供应商
     */
    @Column(comment = "供应商",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String suppliers;


    /**
     * 是否为终端1-是、0-否
     */
    @Column(comment = "是否为终端1-是、0-否",type = MySqlTypeConstant.TINYINT,isNull = false,defaultValue = "1")
    private Boolean isTerminal;

    /**
     * 设备扩展信息
     */
    @Excel(name = "设备扩展信息",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 3)
    @Column(comment = "设备扩展信息",type = MySqlTypeConstant.VARCHAR,length = 255)
    private String extraType;


    public static final String NAME = "name";

    public static final String IS_TERMINAL = "is_terminal";

    public static final String EXTRA_TYPE = "extra_type";



}
