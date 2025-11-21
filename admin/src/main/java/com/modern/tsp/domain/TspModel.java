package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Date 2022/10/15 11:00
 * @Version 1.0.0
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_model",comment = "摩登 - TSP - 短信模板")
@Alias("TspModel")
@TableName(value = "tsp_model", autoResultMap = true)
@Data
public class TspModel extends BaseModel {

    /**
     * 模板标题
     */
    @Column(comment = "模板标题",type = MySqlTypeConstant.VARCHAR,length = 50,isNull = false)
    private String modelTitle;

    /**
     * 模板内容
     */
    @Column(comment = "模板内容",type = MySqlTypeConstant.VARCHAR,length = 500,isNull = false)
    private String modelContent;

}
