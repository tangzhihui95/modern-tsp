package com.modern.tsp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/14 15:34
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

@Data
@ApiModel("设备信息 - 数据传输对象 - 导入导出")
public class TspEquipmentTypeImportDTO {

    /**
     * 设备类型
     */
    @Excel(name = "设备类型",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 1)
    private String name;

    @Excel(name = "是否为终端",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 3)
    private String terminal;

    /**
     * 设备扩展信息
     */
    @Excel(name = "设备扩展信息",cellType = Excel.ColumnType.STRING,type = Excel.Type.ALL,sort = 3)
    private String extraType;
}