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
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/1 11:15
 * @Version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_vehicle_other",comment = "摩登 - TSP - 车卡信息推送补充数据表")
@Alias("TspVehicleOther")
@TableName("tsp_vehicle_other")
public class TspVehicleOther extends BaseModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspVehicleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆产地", type = MySqlTypeConstant.INT,length = 11)
    private Integer vehicleOrigin;

    /**
     * 车辆销售渠道名称（4s店，直营店等）
     */
    @Column(comment = "车辆销售渠道名称（4s店，直营店等）", type = MySqlTypeConstant.VARCHAR, length = 100)
    private String salesChannel;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆销售渠道类型（1：实体渠道，2：人员上门，3：电子渠道）", type = MySqlTypeConstant.INT,length = 11)
    private Integer channelType;

    /**
     * 办理员工姓名
     */
    @Column(comment = "办理员工姓名", type = MySqlTypeConstant.VARCHAR, length = 36)
    private String employeeName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "燃料种类", type = MySqlTypeConstant.INT,length = 11)
    private Integer fuelType;

    /**
     * 发动机号码
     */
    @Column(comment = "发动机号码", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String engineNum;

    /**
     * 电动机序列号
     */
    @Column(comment = "电动机序列号", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String motorNum;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆状态", type = MySqlTypeConstant.INT,length = 11,isNull = false)
    private Integer vehicleStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "新车标识", type = MySqlTypeConstant.INT,length = 11,isNull = false)
    private Integer newVehicleFlag;
}
