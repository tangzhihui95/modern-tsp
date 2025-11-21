package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspDealer;
import com.modern.tsp.domain.TspTag;
import com.modern.tsp.mapper.TspTagMapper;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.TspDealerAddVO;
import com.modern.tsp.model.vo.TspDealerPageListVO;
import com.modern.tsp.model.vo.TspTagAddVO;
import com.modern.tsp.model.vo.TspTagPageListVO;
import com.modern.tsp.repository.TspDealerRepository;
import com.modern.tsp.repository.TspTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 15:25
 * @Version 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspTagService {

    private final TspTagRepository tspTagRepository;
    private final TspTagMapper tspTagMapper;

    /**
     * 标签列表
     * @param vo
     * @return
     */
    public PageInfo<TspTagPageListDTO> list(TspTagPageListVO vo) {
        Wrapper<TspTag> ew = tspTagRepository.PageListEw(vo);
        Page<TspTag> page = tspTagRepository.page(Page.of(vo.getPageNum(),vo.getPageSize()),ew);
        ArrayList<TspTagPageListDTO> dtos = new ArrayList<>();
        for (TspTag record : page.getRecords()) {
            TspTagPageListDTO dto = new TspTagPageListDTO();
            BeanUtils.copyProperties(record,dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 添加标签
     * @param vo
     */
    public void add(TspTagAddVO vo) {

        if (tspTagRepository.getByDealerName(vo.getTagName()) != null) {
            ErrorEnum.TSP_DEALER_NAME_REPEAT_ERROR.throwErr();
        }
        TspTag tspTag = new TspTag();
        BeanUtils.copyProperties(vo,tspTag);
        tspTag.setCreateBy(SecurityUtils.getUsername());
        tspTag.setUpdateBy(SecurityUtils.getUsername());
        tspTag.setIsDelete(0);
        tspTagRepository.save(tspTag);
    }

    /**
     * 标签详情(数据渲染)
     * @param tspTagId
     * @return
     */
    public TspTagInfoDTO get(Long tspTagId) {
        TspTag tspTag = tspTagRepository.getById(tspTagId);
        if (tspTag == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        TspTagInfoDTO dto = new TspTagInfoDTO();
        BeanUtils.copyProperties(tspTag,dto);
        return dto;
    }

    /**
     * 修改标签信息
     * @param vo
     */
    public void edit(TspTagAddVO vo) {

        TspTag tspTag = tspTagRepository.getById(vo.getTspTagId());
        if (tspTag == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        BeanUtils.copyProperties(vo,tspTag);
        tspTag.setUpdateBy(SecurityUtils.getUsername());
        tspTagRepository.updateById(tspTag);
    }

    /**
     * 删除标签（是否删除设置为：是）
     * @param tspTagId
     */
    public void delete(Long tspTagId) {

        TspTag tspTag = tspTagRepository.getById(tspTagId);
        if (tspTag == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        tspTagRepository.removeById(tspTagId);
    }

    /**
     * 标签下拉列表
     * @param tagType
     * @return
     */
    public List<TspTagLabelDTO> getLabel(Integer tagType) {
        List<TspTagLabelDTO> resultList = tspTagMapper.getLabel(tagType);
        return resultList;
    }
}
