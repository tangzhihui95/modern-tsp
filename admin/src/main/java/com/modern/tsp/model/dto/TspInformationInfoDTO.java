package com.modern.tsp.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseDTO;
import com.modern.tsp.enums.TspInformationDataEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/7 20:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("信息发布 - 传输对象 - 详情信息")
public class TspInformationInfoDTO extends BaseDTO {

    /**
     * 标题
     */
    @Column(comment = "标题",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
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
     * 下线时间
     */
    @Column(comment = "下线时间",type = MySqlTypeConstant.DATETIME)
    private Date unloadTime;

    /**
     * 结束时间
     */
    @Column(comment = "结束时间",type = MySqlTypeConstant.DATETIME)
    private Date endTime;

    /**
     * 选择时间
     */
    private List<String> termStr;

    private Boolean whetherPublishNow;
}
