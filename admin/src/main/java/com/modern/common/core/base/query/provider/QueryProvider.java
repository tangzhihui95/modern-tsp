package com.modern.common.core.base.query.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.modern.common.core.base.query.domain.Query;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/12 23:34
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */

public class QueryProvider {

    public String queryList(@Param(Constants.WRAPPER) QueryWrapper<Object> ew, @Param("query") Query query) {
        List<Class> followNames = query.getFollowName();
        ArrayList<String> strs = new ArrayList<>();
        if (!CollectionUtils.isEmpty(followNames)){
            for (Class followName : followNames) {
                TableInfo table = SqlHelper.table(followName);
                System.out.println(table);
                String tableName = table.getTableName();
                strs.add(tableName);
            }
        }
        return new SQL() {{
            SELECT("*");
            FROM(0 + "." + query.getName());
//            if (query.getFollowName().size() > 0) {
//                for (int num = 0; num < strs.size() - 1; num++) {
//                    LEFT_OUTER_JOIN(num + "." + strs.get(num) + "on " + )
//                }
//            }
        }}.toString();
    }
}
