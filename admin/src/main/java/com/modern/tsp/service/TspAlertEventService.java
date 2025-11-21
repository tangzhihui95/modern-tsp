package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.constant.ScheduleConstants;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.exception.job.TaskException;
import com.modern.common.utils.SecurityUtils;
import com.modern.exInterface.model.dto.TspAlertEventInfoDTO;
import com.modern.tsp.domain.TspAlertEvent;
import com.modern.tsp.model.dto.TspAlertEventPageListDTO;
import com.modern.tsp.model.vo.TspAlertEventAddVO;
import com.modern.tsp.model.vo.TspAlertEventPageListVO;
import com.modern.tsp.repository.TspAlertEventRepository;
import com.modern.web.quartz.domain.SysJob;
import com.modern.web.quartz.service.ISysJobService;
import com.modern.web.quartz.util.CronUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/14 15:40
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspAlertEventService extends TspBaseService {
    private final TspAlertEventRepository tspAlertEventRepository;
    private final ISysJobService sysJobService;

    public PageInfo<TspAlertEventPageListDTO> getPageList(TspAlertEventPageListVO vo) {

        Wrapper<TspAlertEvent> ew = tspAlertEventRepository.getPageListEw(vo);
        Page<TspAlertEvent> page = tspAlertEventRepository.page(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        List<TspAlertEventPageListDTO> dtos = new ArrayList<>();
        for (TspAlertEvent record : page.getRecords()) {
            TspAlertEventPageListDTO dto = new TspAlertEventPageListDTO();
            BeanUtils.copyProperties(record, dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos, vo.getPageNum(), vo.getPageSize(), page.getTotal());
    }

    public void delete(Long tspAlertEventId) {
        TspAlertEvent event = tspAlertEventRepository.getById(tspAlertEventId);
        if (event == null) {
            ErrorEnum.TSP_ALERT_EVENT_NULL_ERROR.throwErr();
        }
        tspAlertEventRepository.removeById(tspAlertEventId);
    }

    public void add(TspAlertEventAddVO vo) {

        TspAlertEvent alertEvent = tspAlertEventRepository.getByEventName(vo.getEventName());
        if (alertEvent != null) {
            ErrorEnum.TSP_ALERT_EVENT_NAME_ERROR.throwErr();
        }

        alertEvent = new TspAlertEvent();

        BeanUtils.copyProperties(vo, alertEvent);

        alertEvent.setCreateBy(SecurityUtils.getUsername());

        alertEvent.setUpdateBy(SecurityUtils.getUsername());

        tspAlertEventRepository.save(alertEvent);
    }

    public void edit(TspAlertEventAddVO vo) {

        if (vo.getTspAlertEventId() == null) {
            throw new RuntimeException("规则ID不能为空");
        }
        TspAlertEvent alertEvent = tspAlertEventRepository.getById(vo.getTspAlertEventId());
        if (alertEvent == null) {
            ErrorEnum.TSP_ALERT_EVENT_NULL_ERROR.throwErr();
        }
        TspAlertEvent eventName = tspAlertEventRepository.getByEventName(vo.getEventName());
        if (eventName != null) {
            if (!eventName.getId().equals(vo.getTspAlertEventId())) {
                ErrorEnum.TSP_ALERT_EVENT_NAME_ERROR.throwErr();
            }
        }
        BeanUtils.copyProperties(vo, alertEvent);
        alertEvent.setUpdateBy(SecurityUtils.getUsername());
        tspAlertEventRepository.updateById(alertEvent);
    }

    public TspAlertEventInfoDTO get(Long tspAlertEventId) {
        TspAlertEvent alertEvent = tspAlertEventRepository.getById(tspAlertEventId);
        if (alertEvent == null) {
            ErrorEnum.TSP_ALERT_EVENT_NULL_ERROR.throwErr();
        }
        TspAlertEventInfoDTO dto = new TspAlertEventInfoDTO();
        BeanUtils.copyProperties(alertEvent, dto);
        return dto;
    }

    public void setState(Long tspAlertEventId) {
        TspAlertEvent alertEvent = tspAlertEventRepository.getById(tspAlertEventId);
        if (alertEvent == null) {
            ErrorEnum.TSP_ALERT_EVENT_NULL_ERROR.throwErr();
        }

        alertEvent.setIsOpen(!alertEvent.getIsOpen());
        alertEvent.setUpdateBy(SecurityUtils.getUsername());
        tspAlertEventRepository.updateById(alertEvent);
    }
}
