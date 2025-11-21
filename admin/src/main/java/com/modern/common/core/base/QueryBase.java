package com.modern.common.core.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2019 tianjiugongxiang, Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author piaomiao
 * @since 2019-10-12 11:38
 */
@Data
public class QueryBase {

	@ApiModelProperty(name = "queryKey", value = "通用查询关键字", required = false, hidden = true)
	private Long queryKey;

	@ApiModelProperty(name = "orderByColumn", value = "排序字段", required = false, hidden = true)
	private String orderByColumn;

	@ApiModelProperty(name = "isAsc", value = "升降序, ascending/descending", required = false, hidden = true)
	private String isAsc;

	@ApiModelProperty(value = "下一个序列值, 默认传0/或者不传,防止分页数据被插入新数据时错乱", required = false)
	private Long nextSeq = 0L;

	@ApiModelProperty(name = "pageNumber", value = "页码", required = true)
	@Min(value = 1, message = "pageNumber 不能小于1")
	private Integer pageNumber = 1;

	@ApiModelProperty(name = "pageSize", value = "每页数量", required = true)
	@Min(value = 1, message = "pageSize 不能小于1")
	private Integer pageSize = 10;

	@ApiModelProperty(value = "创建时间", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdTime = LocalDateTime.now();

	@ApiModelProperty(value = "更新时间", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@LastModifiedDate
	private LocalDateTime updatedTime = LocalDateTime.now();

	@ApiModelProperty(value = "创建者", hidden = true)
	private long createBy;

	@ApiModelProperty(value = "更新者", hidden = true)
	private long updateBy;

	@ApiModelProperty(value = "状态：0为正常，1位删除", hidden = true)
	private Integer isDelete = 0;

	@ApiModelProperty(value = "搜索", hidden = true)
	private String search;


	@ApiModelProperty(hidden = true)
	public Page getRequestPage() {
		return new Page(this.getPageNumber(), this.getPageSize());
	}

}
