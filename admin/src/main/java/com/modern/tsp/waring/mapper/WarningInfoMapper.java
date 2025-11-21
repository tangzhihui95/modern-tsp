package com.modern.tsp.waring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.tsp.waring.domain.WarningInfo;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author piaomiao
 */
public interface WarningInfoMapper extends BaseMapper<WarningInfo> {

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    WarningInfo selectWarningInfoById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param warningInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<WarningInfo> selectWarningInfoList(WarningInfo warningInfo);

    /**
     * 新增【请填写功能名称】
     *
     * @param warningInfo 【请填写功能名称】
     * @return 结果
     */
    int insertWarningInfo(WarningInfo warningInfo);

    /**
     * 修改【请填写功能名称】
     *
     * @param warningInfo 【请填写功能名称】
     * @return 结果
     */
    int updateWarningInfo(WarningInfo warningInfo);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    int deleteWarningInfoById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWarningInfoByIds(Long[] ids);

}
