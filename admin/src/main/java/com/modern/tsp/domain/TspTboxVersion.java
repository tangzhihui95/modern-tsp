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
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/8 10:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_tbox_version",comment = "摩登 - TSP - 设备版本(测试)")
@Alias("TspTboxVersion")
@TableName("tsp_tbox_version")
public class TspTboxVersion extends BaseModel {


    @Column(comment = "vin",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    private String vin;


    @Column(comment = "版本号",type = MySqlTypeConstant.VARCHAR,length = 22,isNull = false,defaultValue = "1.0")
    private String version;
}
