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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/20 11:04
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

/**
 * 车辆认证审核
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Alias("TspVehicleAudit")
@Table(name = "tsp_vehicle_audit",comment = "摩登 - TSP - 车辆认证审核")
@TableName(value = "tsp_vehicle_audit",autoResultMap = true)
public class TspVehicleAudit extends BaseModel {


    @Column(comment = "用户ID",type = MySqlTypeConstant.BIGINT,isNull = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspUserId;

    /**
     * 车辆ID
     */
    @Column(comment = "车辆ID",type = MySqlTypeConstant.BIGINT,isNull = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleId;

    /**
     * 身份证正反面
     */
//    @TableField(value = "card_img_urls",typeHandler = FastjsonTypeHandler.class)
//    @Column(comment = "身份证正反面",type = MySqlTypeConstant.TEXT)
//    private List<String> cardImgUrls = new ArrayList<>();

    @Column(comment = "身份证正面",type = MySqlTypeConstant.VARCHAR)
    private String cardFrontImg;


    @Column(comment = "身份证反面",type = MySqlTypeConstant.VARCHAR)
    private String cardBackImg;



    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "申请时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime applyTime;

    /**
     * 通过时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "通过时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime passTime;

    /**
     * 驳回时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "驳回时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime rejectTime;


    @Column(comment = "认证备注",type = MySqlTypeConstant.VARCHAR)
    private String auditRemark;


    public static final String TSP_USER_ID = "tsp_user_id";
    public static final String TSP_VEHICLE_ID = "tsp_vehicle_id";
    public static final String CARD_IMG_URLS = "card_img_urls";
    public static final String CERTIFICATION_STATE = "certification_state";
    public static final String PASS_TIME = "pass_time";
    public static final String REJECT_TIME = "reject_time";
    public static final String AUDIT_REMARK = "audit_remark";
}
