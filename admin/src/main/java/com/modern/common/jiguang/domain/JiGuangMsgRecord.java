package com.modern.common.jiguang.domain;

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

import java.util.Map;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/27 10:15
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "ji_guang_msg_record",comment = "极光消息推送记录")
@Alias("JiGuangMsgRecord")
@TableName("ji_guang_msg_record")
@Data
public class JiGuangMsgRecord extends BaseModel {


    @Column(comment = "设备名",type = MySqlTypeConstant.VARCHAR,length = 255)
    private String alias;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "业务ID",type = MySqlTypeConstant.BIGINT,isNull = false)
    private Long businessId;

    @Column(comment = "推送源",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    private String pushSource;

    @Column(comment = "目标源",type = MySqlTypeConstant.VARCHAR,length = 55,isNull = false)
    private String targetSource;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "用户ID",type = MySqlTypeConstant.BIGINT)
    private Long tspUserId;

    @Column(comment = "用户极光ID",type = MySqlTypeConstant.VARCHAR)
    private String registrationId;


    @Column(comment = "是否全部推送1-是、0-否",type = MySqlTypeConstant.TINYINT,isNull = false,defaultValue = "0")
    private Boolean isAll;

    @Column(comment = "是否发送成功",type = MySqlTypeConstant.TINYINT,isNull = false,defaultValue = "0")
    private Boolean isSuccess;


    @Column(comment = "标题",type = MySqlTypeConstant.VARCHAR,length = 255,isNull = false)
    private String title;


    @Column(comment = "内容",type = MySqlTypeConstant.VARCHAR,length = 255,isNull = false)
    private String content;


    @TableField(typeHandler = FastjsonTypeHandler.class, value = "extras")
    @Column(comment = "附加内容",type = MySqlTypeConstant.JSON)
    private Map<String,String> extras;
}
