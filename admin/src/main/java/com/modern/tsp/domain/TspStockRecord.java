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
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * <p>
 * 摩登 - TSP - 出入库操作记录
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tsp_stock_record",comment = "摩登 - TSP - 出入库操作记录")
@Alias(value = "TspStockRecord")
@TableName("tsp_stock_record")
public class TspStockRecord extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 出入库类型1-入库、2-出库、0-未知
     */
    @Column(comment = "出入库类型1-入库、2-出库、0-未知", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    private Integer type;

    /**
     * 操作时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "操作时间",type = MySqlTypeConstant.DATETIME,isNull = false)
    private LocalDateTime stockTime;

    /**
     * 操作名称
     */
    @Column(comment = "操作名称",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String stockName;

    /**
     * 手机号
     */
    @Column(comment = "手机号",type = MySqlTypeConstant.VARCHAR,length = 55)
    private String mobile;

    /**
     * 版本号
     */
    @Column(comment = "版本号",type = MySqlTypeConstant.INT,defaultValue = "0",isNull = false)
    private Integer version;


    public static final String TYPE = "type";

    public static final String STOCK_TIME = "stock_time";

    public static final String STOCK_NAME = "stock_name";

    public static final String MOBILE = "mobile";

    public static final String VERSION = "version";


}
