package com.modern.tsp.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseVO;
import com.modern.tsp.enums.TspInformationDataEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2022/10/14 20:44
 */
@Data
@ApiModel("信息发布 - 请求对象 - 添加")
public class TspInformationAddVO extends BaseVO {

    @ApiModelProperty(value = "信息ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspInformationId;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String informationTitle;

    /**
     * 信息位
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @Column(comment = "信息位;0：系统消息；1：告警通知；2：推荐消息；3：问卷调查；4：轮播广告；5：启动页广告；6：保养提醒",type = MySqlTypeConstant.INT,length = 11,isNull = false)
    private int informationType;

    /**
     * 信息格式
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @Column(comment = "信息格式、0-图文、1-链接",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    private TspInformationDataEnum informationModel;

    /**
     * 状态
     */
    @Column(comment = "状态、0-待发布，1-发布中、2-已下线",type = MySqlTypeConstant.INT,isNull = false)
    private Integer informationStatus;

    /**
     * 下线时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "下线时间",type = MySqlTypeConstant.DATETIME)
    private Date unloadTime;

    /**
     * 有效期
     */
    @TableField(value = "term", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("有效期")
    private String term;

    @ApiModelProperty("是否立即发布")
    private Boolean whetherPublishNow;

    /**
     * 信息点击量
     */
    @Column(comment = "信息点击量",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    private Integer informationCount;

}
