package com.modern.tsp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 摩登 - TSP - TSP用户
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("标签管理 - 数据传输对象 - 详情信息")
public class TspUserDTO extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 手机号(账号)
     */
    @ApiModelProperty(value = "手机号(账号)")
    private String mobile;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCard;


    @ApiModelProperty(value = "身份证号")
    private Integer sex;


    @ApiModelProperty(value = "身份证号")
    private Integer hasBind;


    @ApiModelProperty(value = "身份证号")
    private Integer type;



    /**
     * 出生日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "出生日期", type = MySqlTypeConstant.DATE)
    private LocalDate birthday;


    @Column(comment = "极光推送ID",type = MySqlTypeConstant.BIGINT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long registrationId;

    /**
     * 注册地址
     */
//    @Column(comment = "注册地址",type = MySqlTypeConstant.VARCHAR,length = 255)
//    private String registerAddress;

    @Column(comment = "注册地址(省)",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String province;


    @Column(comment = "注册地址(市)",type = MySqlTypeConstant.VARCHAR,length = 255)
    private String city;

    @Column(comment = "注册地址(区)",type = MySqlTypeConstant.VARCHAR,length = 255)
    private String area;


    @Column(comment = "实名认证：0未认证，1已认证  （默认为 0）",type = MySqlTypeConstant.TINYINT,defaultValue = "0")
    private Integer realNameAudit;


    @Column(comment = "注册渠道:0平台账号",type = MySqlTypeConstant.TINYINT,defaultValue = "0")
    private Integer registeredChannels;

    /**
     * 详细地址
     */
    @Column(comment = "详细地址", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String address;

    /**
     * 身份证正反面
     */
//    @TableField(value = "user_card_img_urls",typeHandler = FastjsonTypeHandler.class)
//    @Column(comment = "身份证正反面",type = MySqlTypeConstant.TEXT)
//    private List<String> userCardImgUrls;


    @Column(comment = "身份证正面",type = MySqlTypeConstant.VARCHAR)
    private String userCardFrontImg;


    @Column(comment = "身份证反面",type = MySqlTypeConstant.VARCHAR)
    private String userCardBackImg;

    /**
     * 用户标签
     */
    @Column(comment = "用户标签",type = MySqlTypeConstant.VARCHAR,length = 255)
    @JsonSerialize(contentUsing = ToStringSerializer.class)
    private List<Long> label;

    /**
     * 是否启用1-是、0-否
     */
    @Column(comment = "是否启用1-是、0-否",type = MySqlTypeConstant.TINYINT,isNull = false,defaultValue = "1")
    private Boolean isEnable;

    /**
     * 用户来源0-未知、1-商店、2-公众号、3-第三方
     */
    @Column(comment = "用户来源0-未知、1-商店、2-公众号、3-第三方",type = MySqlTypeConstant.INT,isNull = false,defaultValue = "0")
    private Integer source;


    public static final String MOBILE = "mobile";

    public static final String REAL_NAME = "real_name";

    public static final String ID_CARD = "id_card";

    public static final String BIRTHDAY = "birthday";

    public static final String ADDRESS = "address";

    public static final String USER_CARD_FRONT_IMG = "user_card_front_img";

    public static final String USER_CARD_BACK_IMG = "user_card_back_img";

    public static final String IS_ENABLE = "is_enable";

    public static final String SOURCE = "source";


}
