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
@Table(name = "tsp_tag",comment = "摩登 - TSP - 标签管理")
@Alias("TspTag")
@TableName(value = "tsp_tag", autoResultMap = true)
@Data
public class TspTag extends BaseModel {

    /**
     * 标签名称
     */
    @Column(comment = "标签名称",type = MySqlTypeConstant.VARCHAR,length = 36,isNull = false)
    private String tagName;

    /**
     * 标签类型
     */
    @Column(comment = "标签类型",type = MySqlTypeConstant.INT,length = 11,isNull = false)
    private Integer tagType;

    /**
     * 关联数量
     */
    @Column(comment = "关联数量",type = MySqlTypeConstant.INT,length = 11,isNull = false,defaultValue = "0")
    private Integer tagCount;
}
