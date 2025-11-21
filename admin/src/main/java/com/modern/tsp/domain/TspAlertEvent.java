package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.sql.Time;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/13 16:55
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Alias("TspAlertEvent")
@Table(name = "tsp_alert_event", comment = "摩登 - TSP - 车辆告警事件")
@TableName(value = "tsp_alert_event", autoResultMap = true)
public class TspAlertEvent extends BaseModel {

    @Column(comment = "告警规则名称", type = MySqlTypeConstant.VARCHAR, length = 22, isNull = false)
    private String eventName;

    @Column(comment = "告警方式1-TSP平台、2-短信、3-app消息", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    private Integer eventType;


    @TableField(typeHandler = FastjsonTypeHandler.class, value = "type_json")
    @Column(comment = "规则类型", type = MySqlTypeConstant.JSON)
    private List<List<TypeJson>> typeJson;


    @Column(comment = "持续分钟", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    private Integer continueMinute;


    @Column(comment = "告警级别", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    private Integer eventLevel;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(comment = "监控开始时间", type = MySqlTypeConstant.TIME)
    private Time monitorStartTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(comment = "监控结束时间", type = MySqlTypeConstant.TIME)
    private Time monitorEndTime;

    @Column(comment = "是否开启1-开启、0-停用",type = MySqlTypeConstant.TINYINT,length = 1,isNull = false,defaultValue = "0")
    private Boolean isOpen;


    @Column(comment = "备注", type = MySqlTypeConstant.VARCHAR, length = 255, isNull = false)
    private String remark;
}
