package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import com.modern.tsp.enums.TspInformationDataEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_information",comment = "摩登 - TSP - 信息发布")
@Alias("TspInformation")
@TableName(value = "tsp_information", autoResultMap = true)
@Data
public class TspInformation extends BaseModel {

    /**
     * 标题
     */
    @Column(comment = "标题",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    private String informationTitle;

    /**
     * 信息位
     */
    @Column(comment = "信息位;0：系统消息；1：告警通知；2：推荐消息；3：问卷调查；4：轮播广告；5：启动页广告；6：保养提醒",type = MySqlTypeConstant.INT,length = 11,isNull = false)
    private int informationType;

    /**
     * 信息格式
     */
    @Column(comment = "信息格式、0-图文、1-链接",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    private TspInformationDataEnum informationModel;

    /**
     * 下线时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "下线时间",type = MySqlTypeConstant.DATETIME)
    private LocalDateTime unloadTime;

    /**
     * 有效期
     */
    @TableField(value = "term", typeHandler = FastjsonTypeHandler.class)
    @Column(comment = "有效期",type = MySqlTypeConstant.VARCHAR,length = 500)
    private String term;

    @Column(comment = "是否立即发布", type = MySqlTypeConstant.TINYINT, defaultValue = "0")
    private Boolean whetherPublishNow;

    /**
     * 状态
     */
    @Column(comment = "状态、0-待发布，1-已发布、2-已下线",type = MySqlTypeConstant.INT,isNull = false)
    private Integer informationStatus;

    /**
     * 信息点击量
     */
    @Column(comment = "信息点击量",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    private Integer informationCount;

    /**
     * 系统用户id
     */
    @Column(comment = "系统用户id",type = MySqlTypeConstant.INT)
    private int userId;

    /**
     * 系统部门id
     */
    @Column(comment = "系统部门id",type = MySqlTypeConstant.INT)
    private int deptId;

}
