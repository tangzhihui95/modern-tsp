package com.modern.web.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.modern.common.core.domain.entity.SysDept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author pm
 * @apiNode piaomiao
 * @date 2022/6/812:06
 * <p>
 * Copyright: Copyright (C) 2019 tlcd, Inc. All rights reserved.
 * <p>
 * Company: 湖南成为科技有限公司
 */
public class SysUserSqlProvider implements ProviderMethodResolver {
    public String deptPostList(@Param(Constants.WRAPPER)QueryWrapper<SysDept> ew){
        return new SQL(){{
            SELECT("a.remark,b.user_id,t.post_id,c.dept_id,b.user_name,t.post_name,c.dept_name,t.post_sort")
                    .FROM("sys_post t")
                    .LEFT_OUTER_JOIN("sys_user_post a ON t.post_id = a.post_id")
                    .LEFT_OUTER_JOIN("sys_user b ON b.user_id = a.user_id")
                    .LEFT_OUTER_JOIN("sys_dept c ON t.dept_id = c.dept_id ${ew.customSqlSegment}");
        }}.toString();
    }
}
