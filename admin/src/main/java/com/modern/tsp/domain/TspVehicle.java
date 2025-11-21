package com.modern.tsp.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import com.modern.tsp.enums.TspVehicleEnumCertificationState;
import com.modern.tsp.enums.TspVehicleStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 摩登 - TSP - 车辆信息
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Alias("TspVehicle")
@Table(name = "tsp_vehicle",comment = "摩登 - TSP - 车辆信息")
@TableName(value = "tsp_vehicle", autoResultMap = true)
public class TspVehicle extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * sys用户主键id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "sys用户主键id", type = MySqlTypeConstant.BIGINT)
    private Long userId;

    /**
     * sys部门主键id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "sys部门主键id", type = MySqlTypeConstant.BIGINT)
    private Long deptId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Index(value = "idx_tsp_user_id")
    @Column(comment = "用户ID", type = MySqlTypeConstant.BIGINT)
    private Long tspUserId;

    /**
     * 车辆型号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆型号ID", type = MySqlTypeConstant.BIGINT)
    @Index(value = "idx_tsp_vehicle_std_model_id")
    private Long tspVehicleStdModelId;

    /**
     * 设备ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "设备ID", type = MySqlTypeConstant.BIGINT)
    private Long tspEquipmentId;

    /**
     * VIN
     */
    @Index(value = "idx_vin")
    @Column(comment = "VIN", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String vin;


    /**
     * 运营商
     */
    @Column(comment = "运营商", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String operator;


    /**
     * 车辆厂商
     */
    @Column(comment = "车辆厂商", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String providerName;


    /**
     * 配置名称
     */
    @Column(comment = "配置名称", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String configureName;


    /**
     * 颜色
     */
    @Column(comment = "颜色", type = MySqlTypeConstant.VARCHAR, length = 22)
    private String color;


    /**
     * 批次号
     */
    @Column(comment = "批次号", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String batchNo;

    /**
     * 用途
     */
    @Column(comment = "用途", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String purpose;

    /**
     * 发动机号码
     */
    @Column(comment = "发动机号码", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String engineNum;

    /**
     * 电动机序列号
     */
    @Column(comment = "电动机序列号", type = MySqlTypeConstant.VARCHAR, length = 100)
    private String motorNum;

    /**
     * 电池包序列号
     */
    @Column(comment = "电池包序列号", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String essNum;

    /**
     * CDU序列号
     */
    @Column(comment = "CDU序列号", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String cduNum;

    /**
     * 电池包规格
     */
    @Column(comment = "电池包规格", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String essModel;

    /**
     * 电动机品牌
     */
    @Column(comment = "电动机品牌", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String motorBrand;

    /**
     * 出厂日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "出厂日期", type = MySqlTypeConstant.DATE)
    private LocalDate exFactoryDate;

    /**
     * 下线日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "下线日期", type = MySqlTypeConstant.DATE)
    private LocalDate operateDate;

    /**
     * 标签
     */
    @Column(comment = "标签", type = MySqlTypeConstant.VARCHAR, length = 510)
    private String label;

    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @Column(comment = "认证状态1-未认证、2-认证中、3-认证失败、4-已认证", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "1")
    private TspVehicleEnumCertificationState certificationState;

    /**
     * 状态1-已创建、2-已销售、3-已绑定、4-已解绑、5-已报废、6-已注册
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @Column(comment = "状态1-已创建、2-已销售、3-已绑定、4-已解绑、5-已报废、6-已注册", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "1")
    private TspVehicleStateEnum state;


    /**
     * 当前绑定时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "当前绑定时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime currentBindTime;

    /**
     * 信息完成进度1-出厂信息、2-销售信息、3-上牌信息、4-绑定及MNO信息、5-出入库记录
     */
    @Column(comment = "信息完成进度0-基本信息、1-出厂信息、2-销售信息、3-上牌信息、4-绑定及MNO信息、5-出入库记录", type = MySqlTypeConstant.INT, defaultValue = "1")
    private Integer progress;

    /**
     * 是否完成车辆资料信息1-完成、0-未完成
     */
    @Column(comment = "是否完成车辆资料信息1-完成、0-未完成", type = MySqlTypeConstant.TINYINT, length = 1, isNull = false, defaultValue = "0")
    private Boolean isComplete;

    /**
     * 车卡信息是否推送1-已推送、0-未推送
     */
    @Column(comment = "车卡信息是否推送1-已推送、0-未推送", type = MySqlTypeConstant.INT, length = 1, isNull = false, defaultValue = "0")
    private Integer sendStatus;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "推送时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime sendTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "车辆报废执行时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime scrapTime;


    /**
     * 经销商ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "经销商ID", type = MySqlTypeConstant.BIGINT)
    private Long dealerId;


    /**
     * 备注
     */
    @Column(comment = "备注", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String remark;


    public static final String ID = "id";

    public static final String TSP_USER_ID = "tsp_user_id";

    public static final String TSP_VEHICLE_STD_MODEL_ID = "tsp_vehicle_std_model_id";

    public static final String TSP_EQUIPMENT_ID = "tsp_equipment_id";

    public static final String CONFIGURE_NAME = "configure_name";

    public static final String COLOR = "color";

    public static final String BATCH_NO = "batch_no";

    public static final String PURPOSE = "purpose";

    public static final String EX_FACTORY_DATE = "ex_factory_date";

    public static final String OPERATE_DATE = "operate_date";

    public static final String LABEL = "label";

    public static final String STATE = "state";

    public static final String INVOICING_DATE = "Invoicing_date";

    public static final String CURRENT_BIND_TIME = "current_bind_time";

    public static final String PROGRESS = "progress";

    public static final String IS_COMPLETE = "is_complete";

    public static final String REMARK = "remark";

    public static final String CERTIFICATION_STATE = "certification_state";

    public static final String VIN = "vin";
//
//    public static final String SN = "sn";

    public static final String SCRAP_TIME = "scrap_time";

//    public static final String SIM = "sim";
}
