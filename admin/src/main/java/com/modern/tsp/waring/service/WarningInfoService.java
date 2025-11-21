package com.modern.tsp.waring.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.tsp.waring.domain.WarningInfo;
import com.modern.tsp.waring.mapper.WarningInfoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author piaomiao
 */
@Service
public class WarningInfoService extends ServiceImpl<WarningInfoMapper, WarningInfo> {

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */

    public WarningInfo selectWarningInfoById(Long id) {
        return baseMapper.selectWarningInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param warningInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */

    public List<WarningInfo> selectWarningInfoList(WarningInfo warningInfo) {
        return baseMapper.selectWarningInfoList(warningInfo);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param warningInfo 【请填写功能名称】
     * @return 结果
     */

    public int insertWarningInfo(WarningInfo warningInfo) {
        warningInfo.setCreateTime(LocalDateTime.now());
        return baseMapper.insertWarningInfo(warningInfo);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param warningInfo 【请填写功能名称】
     * @return 结果
     */

    public int updateWarningInfo(WarningInfo warningInfo) {
        warningInfo.setUpdateTime(LocalDateTime.now());
        return baseMapper.updateWarningInfo(warningInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */

    public int deleteWarningInfoByIds(Long[] ids) {
        return baseMapper.deleteWarningInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */

    public int deleteWarningInfoById(Long id) {
        return baseMapper.deleteWarningInfoById(id);
    }

}
