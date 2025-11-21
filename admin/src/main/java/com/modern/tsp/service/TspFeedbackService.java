package com.modern.tsp.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspFeedback;
import com.modern.tsp.model.dto.TspFeedbackInfoDTO;
import com.modern.tsp.model.dto.TspFeedbackPageListDTO;
import com.modern.tsp.model.vo.TspFeedbackAddVO;
import com.modern.tsp.model.vo.TspFeedbackPageListVO;
import com.modern.tsp.repository.TspFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
public class TspFeedbackService {

    private final TspFeedbackRepository tspFeedbackRepository;

    /**
     * 问题反馈列表
     * @param vo
     * @return
     */
    public PageInfo<TspFeedbackPageListDTO> list(TspFeedbackPageListVO vo) {
//        Wrapper<TspFeedback> ew = tspFeedbackRepository.PageListEw(vo);
//        Page<TspFeedback> page = tspFeedbackRepository.page(Page.of(vo.getPageNum(),vo.getPageSize()),ew);
//        ArrayList<TspFeedbackPageListDTO> dtos = new ArrayList<>();
//
//        if (CollectionUtil.isNotEmpty(page.getRecords())) {
//           page.getRecords().forEach(pageDto->{
//               TspFeedbackPageListDTO tspFeedbackPageListDTO = new TspFeedbackPageListDTO();
//               BeanUtils.copyProperties(pageDto,tspFeedbackPageListDTO);
//               dtos.add(tspFeedbackPageListDTO);
//           });
//        }
//
//        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getTotal());

        Wrapper<TspFeedback> ew = tspFeedbackRepository.PageListEw(vo);
        Page<TspFeedback> page = tspFeedbackRepository.page(Page.of(vo.getPageNum(),vo.getPageSize()),ew);
        ArrayList<TspFeedbackPageListDTO> dtos = new ArrayList<>();
        HashMap<List<Long>, ArrayList<TspFeedbackPageListDTO>> hashMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(page.getRecords())) {
            page.getRecords().forEach(pageDto->{
                TspFeedbackPageListDTO tspFeedbackPageListDTO = new TspFeedbackPageListDTO();
                BeanUtils.copyProperties(pageDto,tspFeedbackPageListDTO);
                dtos.add(tspFeedbackPageListDTO);
            });
        }
        List<Long> pageId = dtos.stream().map(TspFeedbackPageListDTO::getId).collect(Collectors.toList());
        hashMap.put(pageId,dtos);
        return PageInfo.of(hashMap.get(pageId), page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 添加问题反馈
     * @param vo
     */
    public void add(TspFeedbackAddVO vo) {
        TspFeedback tspFeedback = new TspFeedback();
        BeanUtils.copyProperties(vo,tspFeedback);
        tspFeedback.setCreateBy(SecurityUtils.getUsername());
        tspFeedback.setUpdateBy(SecurityUtils.getUsername());
        tspFeedback.setFeedbackTime(vo.getFeedbackTime());
        tspFeedback.setDealStatus(0);
        tspFeedback.setIsDelete(0);
        tspFeedbackRepository.save(tspFeedback);
    }

    /**
     * 问题反馈详情(数据渲染)
     * @param tspFeedbackId
     * @return
     */
    public TspFeedbackInfoDTO get(Long tspFeedbackId) {
        TspFeedback tspFeedback = tspFeedbackRepository.getById(tspFeedbackId);
        TspFeedbackInfoDTO dto = new TspFeedbackInfoDTO();
        BeanUtils.copyProperties(tspFeedback,dto);
        return dto;
    }

    /**
     * 处理问题反馈
     * @param vo
     */
    public void deal(TspFeedbackAddVO vo) {
        TspFeedback tspFeedback = tspFeedbackRepository.getById(vo.getTspFeedbackId());
        BeanUtils.copyProperties(vo,tspFeedback);
        tspFeedback.setDealStatus(1);
        tspFeedback.setDealTime(vo.getUpdateTime());
        tspFeedback.setUpdateBy(SecurityUtils.getUsername());
        tspFeedbackRepository.updateById(tspFeedback);
    }

    /**
     * 删除反馈（是否删除设置为：是）
     * @param tspFeedbackId
     */
    public void delete(Long tspFeedbackId) {
        tspFeedbackRepository.removeById(tspFeedbackId);
    }
}
