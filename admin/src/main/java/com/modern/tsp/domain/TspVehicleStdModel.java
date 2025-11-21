package com.modern.tsp.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import com.modern.tsp.enums.TpsVehicleDataKeyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 14:46
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_vehicle_std_model",comment = "摩登 - TSP - 车辆型号")
@Alias("TspVehicleStdModel")
@TableName("tsp_vehicle_std_model")
public class TspVehicleStdModel extends BaseModel {


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车型ID",type = MySqlTypeConstant.BIGINT,isNull = false)
    private Long tspVehicleModelId;


    @Column(comment = "型号名称",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String stdModeName;

    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @Column(comment = "能源类型",type = MySqlTypeConstant.TINYINT)
    private TpsVehicleDataKeyEnum dataKey;


    @Column(comment = "公告批次",type = MySqlTypeConstant.VARCHAR)
    private String noticeBatch;


    @Column(comment = "公告型号",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String noticeModel;


    @Column(comment = "车辆厂商",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String firm;

}
