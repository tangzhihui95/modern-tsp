package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.tsp.domain.TspInformation;
import com.modern.tsp.mapper.TspInformationMapper;
import com.modern.tsp.model.dto.TspInformationInfoDTO;
import com.modern.tsp.model.dto.TspInformationPageListDTO;
import com.modern.tsp.model.vo.TspInformationAddVO;
import com.modern.tsp.model.vo.TspInformationPageListVO;
import com.modern.tsp.repository.TspInformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author nut
 * @version V1.0.0
 * @date 2022/10/12 20:42
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspInformationService {
    private final TspInformationRepository tspInformationRepository;
    private final TspInformationMapper tspInformationMapper;

    /**
     * 展示列表
     * @param vo
     * @return
     */
    public PageInfo<TspInformationPageListDTO> list(TspInformationPageListVO vo) {
        Wrapper<TspInformation> ew = tspInformationRepository.pageListEw(vo);
        Page<TspInformation> page = tspInformationRepository.page(Page.of(vo.getPageNum(),vo.getPageSize()),ew);
        ArrayList<TspInformationPageListDTO> dtos = new ArrayList<>();
        for (TspInformation record : page.getRecords()) {
            TspInformationPageListDTO dto = new TspInformationPageListDTO();
            BeanUtils.copyProperties(record, dto);
            String status = null;
            String type = null;
            // "状态、0-待发布，1-已发布、2-已下线"
            switch (record.getInformationStatus()) {
                case 0 : status = "待发布";
                    break;
                case 1 : status = "已发布";
                    break;
                case 2 : status = "已下线";
                    break;
            }
            // 信息位;0：系统消息；1：告警通知；2：推荐消息；3：问卷调查；4：轮播广告；5：启动页广告；6：保养提醒"
            switch (record.getInformationType()) {
                case 0 : type = "系统消息";
                    break;
                case 1 : type = "告警通知";
                    break;
                case 2 : type = "推荐消息";
                    break;
                case 3 : type = "问卷调查";
                    break;
                case 4 : type = "轮播广告";
                    break;
                case 5 : type = "启动页广告";
                    break;
                case 6 : type = "保养提醒";
                    break;
            }
            dto.setType(type);
            dto.setStatus(status);
            dtos.add(dto);
        }
        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 查询详情（数据渲染）
     * @param tspInformationId
     * @return
     */
    public TspInformationInfoDTO get(Long tspInformationId) {
        TspInformation tspInformation = tspInformationRepository.getById(tspInformationId);
        if (tspInformation == null) {
            ErrorEnum.TSP_INFORMATION_NULL_ERROR.throwErr();
        }
        TspInformationInfoDTO dto = new TspInformationInfoDTO();
        BeanUtils.copyProperties(tspInformation, dto);
        // getTerm()：有效期
        if (tspInformation.getTerm() != null && !"".equals(tspInformation.getTerm())) {
            List<String> termStr = new ArrayList<>();
            termStr.add(0,tspInformation.getTerm().substring(0,19));
            termStr.add(1,tspInformation.getTerm().substring(22));
            dto.setTermStr(termStr);
        }
        return dto;
    }

    /**
     * 新增信息
     * @param vo
     */
    @Transactional(rollbackFor = ServiceException.class)
    public void add(TspInformationAddVO vo) {
        if (tspInformationRepository.getByTitle(vo.getInformationTitle()) != null) {
            ErrorEnum.TSP_INFORMATION_TITLE_REPEAT_ERROR.throwErr();
        }
        TspInformation information = new TspInformation();
        BeanUtils.copyProperties(vo, information);
        if (vo.getTerm() != null && !"".equals(vo.getTerm())) {
            String term = vo.getTerm();
            String start = term.substring(0, 19);
            String end = term.substring(22);
            LocalDateTime startTime = LocalDateUtils.parseToLocalDateTime(start, LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
            LocalDateTime endTime = LocalDateUtils.parseToLocalDateTime(end, LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
            // 有效期的开始时间小于当前时间，有效期的结束时间大于当前时间
            // startTime  now   endTime
            // 开始时间在当前时间的前面   结束时间是在当前时间的后面
            if (startTime.isBefore(LocalDateTime.now()) && endTime.isAfter(LocalDateTime.now())) {
                //"状态、0-待发布，1-已发布、2-已下线
                // 1-已发布
                information.setInformationStatus(1);
            }
            // 有效期的结束时间小于等于当前时间 （当前时间在endTime的后面 然后取反）
            // 结束数据没在当前时间的后面
            if (!endTime.isAfter(LocalDateTime.now())) {
                // 2-已下线
                information.setInformationStatus(2);
            }
        }
        // 鉴定权限，创建者、更新者 必须is_delete = 0
        information.setCreateBy(SecurityUtils.getUsername());
        information.setUpdateBy(SecurityUtils.getUsername());
        information.setIsDelete(0);
        tspInformationRepository.save(information);
    }

    /**
     * 发布信息修改
     * @param vo
     */
    @Transactional(rollbackFor = ServiceException.class)
    public void edit(TspInformationAddVO vo) {
        TspInformation tspInformation = tspInformationRepository.getById(vo.getTspInformationId());
        if (tspInformation == null) {
            ErrorEnum.TSP_INFORMATION_NULL_ERROR.throwErr();
        }
        vo.setInformationStatus(null);
        if (vo.getTerm() != null && !"".equals(vo.getTerm())) {
            // 如果设置了有效期！！！
            String term = vo.getTerm();
            String start = term.substring(0, 19);
            String end = term.substring(22);
            LocalDateTime startTime = LocalDateUtils.parseToLocalDateTime(start, LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
            LocalDateTime endTime = LocalDateUtils.parseToLocalDateTime(end, LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
            // 有效期的开始时间小于当前时间，有效期的结束时间大于当前时间
            // 开始时间是在当前时间的前面  结束时间是在当前时间的后面
            if (startTime.isBefore(LocalDateTime.now()) && endTime.isAfter(LocalDateTime.now())) {
                // 1-已发布
                tspInformation.setInformationStatus(1);
            }
            // 有效期的结束时间小于等于当前时间
            // 结束时间没有在当前时间的后面
            if (!endTime.isAfter(LocalDateTime.now())) {
                // 2-已下线
                tspInformation.setInformationStatus(2);
            }
        }
        // 前端修改的数据 必须到我们数据库 所以将vo转换到tspInformation中
        BeanUtils.copyProperties(vo,tspInformation);
        // 更新者的权限
        tspInformation.setUpdateBy(SecurityUtils.getUsername());
        tspInformationRepository.updateById(tspInformation);
    }

    /**
     * 下线
     * @param tspInformationId
     */
    public void unload(Long tspInformationId) {
        TspInformation tspInformation = tspInformationRepository.getById(tspInformationId);
        if (tspInformation == null) {
            ErrorEnum.TSP_INFORMATION_NULL_ERROR.throwErr();
        }
        // "状态、0-待发布，1-已发布、2-已下线
        // 如果是获取的状态是0-待发布 那么就set为1-已发布
        if (tspInformation.getInformationStatus() == 0) {
            tspInformation.setInformationStatus(1);
        }
        // 否则如果为1-已发布 那么set为2-已下线
        else if (tspInformation.getInformationStatus() == 1) {
            tspInformation.setInformationStatus(2);
            // 下线时间为当前时间
            tspInformation.setUnloadTime(LocalDateUtils.getCurrentTime());
        }
        tspInformationRepository.updateById(tspInformation);
    }

    public List<TspInformationPageListDTO> listInformation(TspInformationPageListVO vo) {
        return tspInformationMapper.selectUserList(vo);
    }
}
