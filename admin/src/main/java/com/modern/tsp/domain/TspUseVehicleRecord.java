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
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;


/**
 * <p>
 * 摩登 - TSP - 车辆绑定记录
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_use_vehicle_record",comment = "摩登 - TSP - 车辆绑定记录") //name表示指明数据库表名
@Alias("TspUseVehicleRecord") //在配置文件中配置 type-aliases-package 告诉spring boot （项目）你改别名的包是哪里
@TableName("tsp_use_vehicle_record")
public class TspUseVehicleRecord extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 用户ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "用户ID",type = MySqlTypeConstant.BIGINT,isNull = false)
    private Long tspUserId;

    /**
     * 车辆ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆ID",type = MySqlTypeConstant.BIGINT,isNull = false)
    private Long tspVehicleId;

    /**
     * 手机号
     */
    @Column(comment = "手机号",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String mobile;

    /**
     * 姓名
     */
    @Column(comment = "姓名",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String realName;

    /**
     * 身份证号
     */
    @Column(comment = "身份证号",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String idCard;

    /**
     * 版本号
     */
    @Column(comment = "版本号",type = MySqlTypeConstant.INT)
    private Integer version;


    /**
     * 操作内容(备注) 例：绑定，解绑
     */
    @Column(comment = "备注",type = MySqlTypeConstant.VARCHAR,length = 255)
    private String remark;


    public static final String TSP_USER_ID = "tsp_user_id";

    public static final String TSP_VEHICLE_ID = "tsp_vehicle_id";

    public static final String MOBILE = "mobile";

    public static final String REAL_NAME = "real_name";

    public static final String ID_CARD = "id_card";

    public static final String REMARK = "remark";

    public static final String VERSION = "version";

}
