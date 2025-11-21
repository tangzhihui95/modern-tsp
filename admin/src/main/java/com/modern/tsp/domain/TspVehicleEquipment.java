package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
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
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/11/14 11:42
 * @Version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Alias("TspVehicleEquipment")
@Table(name = "tsp_vehicle_equipment",comment = "摩登 - TSP - 车辆设备记录表")
@TableName(value = "tsp_vehicle_equipment", autoResultMap = true)
public class TspVehicleEquipment extends BaseModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆id", type = MySqlTypeConstant.BIGINT)
    private Long tspVehicleId;

    /**
     * 设备id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "设备id", type = MySqlTypeConstant.BIGINT)
    private Long tspEquipmentId;

    /**
     * 上传时间
     */
    @ApiModelProperty(value = "上传时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "上传时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime uploadTime;

    /**
     * 解绑时间
     */
    @ApiModelProperty(value = "解绑时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "解绑时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime unBindTime;
}
