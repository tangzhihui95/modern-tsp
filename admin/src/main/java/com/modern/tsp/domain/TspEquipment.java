package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;


/**
 * <p>
 * 摩登 - TSP - 设备
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_equipment",comment = "摩登 - TSP - 设备")
@Alias("TspEquipment")
@TableName("tsp_equipment")
@Data
public class TspEquipment extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "分类ID",type = MySqlTypeConstant.BIGINT,isNull = false)
    private Long tspEquipmentModelId;

    /**
     * SN
     */
    @Column(comment = "sn", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String sn;


    @Column(comment = "iccId", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String iccId;


    @Column(comment = "imsi", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String imsi;

    @Column(comment = "sim", type = MySqlTypeConstant.VARCHAR,length = 55, isNull = false)
    private String sim;


    @Column(comment = "imei", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String imei;

    /**
     * version
     */
    @Column(comment = "硬件版本号", type = MySqlTypeConstant.VARCHAR,length = 22)
    private String version;

    /**
     * 是否为终端1-是、0-否
     */
    @Column(comment = "是否未终端1-是、0-否",type = MySqlTypeConstant.TINYINT,length = 1,isNull = false,defaultValue = "0")
    private Boolean isTerminal;

    /**
     * 供应商代码
     */
    @Column(comment = "供应商代码",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    private String supplierCode;

    @Column(comment = "运营商、1-移动、2-联通、3-电信、0-未知",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    private Integer operator;

    /**
     * 批次流水号
     */
    @Column(comment = "批次流水号",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    private String serialNumber;

    /**
     * 是否在线1-在线、0-未在线
     */
    @Column(comment = "是否在线1-在线、0-未在线",type = MySqlTypeConstant.TINYINT,length = 1,isNull = false,defaultValue = "0")
    private Boolean isOnline;

    /**
     * 是否注册1-是、0-否
     */
    @Column(comment = "是否注册1-是、0-否",type = MySqlTypeConstant.TINYINT,length = 1,isNull = false,defaultValue = "0")
    private Boolean isRegister;

    /**
     * 是否报废1-是、0-否
     */
    @Column(comment = "是否报废1-是、0-否",type = MySqlTypeConstant.TINYINT,length = 1)
    private Boolean isScrap;

    @ApiModelProperty("设备报废时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "设备报废时间",type = MySqlTypeConstant.DATETIME)
    private LocalDateTime scrapTime;

    public static final String TSP_EQUIPMENT_MODEL_ID = "tsp_equipment_model_id";

    public static final String NAME = "name";

    public static final String IS_TERMINAL = "is_terminal";

    public static final String SUPPLIER_CODE = "supplier_code";

    public static final String SERIAL_NUMBER = "serial_number";

    public static final String IS_ONLINE = "is_online";

    public static final String IS_REGISTER = "is_register";

    public static final String IS_SCRAP = "is_scrap";

    public static final String VERSION = "version";

    public static final String SN = "sn";

    public static final String ICCID = "icc_id";

    public static final String IMEI = "imei";

    public static final String IMSI = "imsi";

    public static final String SIM = "sim";

}
