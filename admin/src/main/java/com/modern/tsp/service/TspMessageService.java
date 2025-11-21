package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.tsp.domain.TspMessage;
import com.modern.tsp.domain.TspTag;
import com.modern.tsp.model.dto.TspMessageInfoDTO;
import com.modern.tsp.model.dto.TspMessagePageListDTO;
import com.modern.tsp.model.vo.TspMessageAddVO;
import com.modern.tsp.model.vo.TspMessagePageListVO;
import com.modern.tsp.repository.TspMessageRecordRepository;
import com.modern.tsp.repository.TspMessageRepository;
import com.modern.tsp.repository.TspTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/1 20:42
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspMessageService extends TspBaseService {
    private final TspMessageRepository tspMessageRepository;
//    private final TspMessageRecordRepository tspMessageRecordRepository;  //不使用tspMessageRecord表
    private final TspTagRepository tspTagRepository;

    @Transactional(rollbackFor = ServiceException.class)
    public void add(TspMessageAddVO vo) {
        if (tspMessageRepository.getByTitle(vo.getTitle()) != null) {
            ErrorEnum.TSP_MESSAGE_TITLE_REPEAT_ERROR.throwErr();
        }
        TspMessage message = new TspMessage();
        BeanUtils.copyProperties(vo, message);
        message.setCreateBy(SecurityUtils.getUsername());
        message.setUpdateBy(SecurityUtils.getUsername());
        if (vo.getUserLabels() != null && vo.getUserLabels().size() != 0) {
            for (Long tspTagId : vo.getUserLabels()) {
                TspTag tspTag = tspTagRepository.getById(tspTagId);
                tspTag.setTagCount(tspTag.getTagCount() + 1);
                tspTagRepository.updateById(tspTag);
            }
            message.setUserLabels(vo.getUserLabels().toString());
        }
        tspMessageRepository.save(message);


//不使用tspMessageRecord表
//        if (StringUtils.isNotNull(vo.getSendType())) {
//            if (vo.getSendType() != 1 && vo.getSendType() != 3) {
//                if (!CollectionUtils.isEmpty(vo.getSendTimes()) && vo.getSendTimes().get(0) != null) {
//                    for (String sendTime : vo.getSendTimes()) {
//                        if (StringUtils.isNotEmpty(sendTime)) {
//                            TspMessageRecord record = new TspMessageRecord();
//                            record.setSendTime(LocalDateUtils.parseToLocalDateTime(sendTime, LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS));
//                            record.setTspMessageId(message.getId());
//                            record.setCreateBy(SecurityUtils.getUsername());
//                            record.setUpdateBy(SecurityUtils.getUsername());
//                            tspMessageRecordRepository.save(record);
//                        }
//                    }
//                }
//            }
//        }


    }

    @Transactional(rollbackFor = ServiceException.class)
    public void edit(TspMessageAddVO vo) {
        TspMessage message = tspMessageRepository.getById(vo.getTspMessageId());
        if (message == null) {
            ErrorEnum.TSP_MESSAGE_NULL_ERROR.throwErr();
        }
        if (vo.getTitle().equals(message.getTitle()) && !vo.getTspMessageId().equals(message.getId())) {
            ErrorEnum.TSP_MESSAGE_TITLE_REPEAT_ERROR.throwErr();
        }
        // 得到标签id
        String labelStr = message.getUserLabels();
        if ((labelStr != null && !"".equals(labelStr)) && !"[]".equals(labelStr)) {
            List<String> strings = Arrays.asList(labelStr.split(","));
            for (String string : strings) {
                if (string.contains("[")) {
                    string = string.replace("[", "");
                }
                if (string.contains("]")) {
                    string = string.replace("]", "");
                }
                if (string.contains(" ")) {
                    string = string.replace(" ", "");
                }
                // 将原来的标签绑定值全部减一全部
                Long tagId = Long.valueOf(string);
                TspTag oldTag = tspTagRepository.getById(tagId);
                oldTag.setTagCount(oldTag.getTagCount() - 1);
                oldTag.setUpdateBy(SecurityUtils.getUsername());
                tspTagRepository.updateById(oldTag);
            }
        }
        List<Long> newLabelList = vo.getUserLabels();
        // 标签对比新增和减少绑定数量
        if (newLabelList != null && newLabelList.size() != 0) {
            // 将新的标签绑定值全部加一
            for (Long newLabel : newLabelList) {
                TspTag newTag = tspTagRepository.getById(newLabel);
                newTag.setTagCount(newTag.getTagCount() + 1);
                newTag.setUpdateBy(SecurityUtils.getUsername());
                tspTagRepository.updateById(newTag);
            }
        }


//不使用tspMessageRecord表
//        if (vo.getSendType() == 2) {
//            if (vo.getSendTimes() != null && vo.getSendTimes().size() > 0) {
//                tspMessageRecordRepository.removeByIdAndSendState(message.getId(), 0);
//                for (String sendTime : vo.getSendTimes()) {
//                    List<TspMessageRecord> records = tspMessageRecordRepository.findByTspMessageId(message.getId());
//                    if (records.size() > 0) {
//                        for (TspMessageRecord record : records) {
//                            record.setSendTime(LocalDateUtils.parseToLocalDateTime(sendTime, LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS));
//                            record.setTspMessageId(message.getId());
//                            record.setCreateBy(SecurityUtils.getUsername());
//                            record.setUpdateBy(SecurityUtils.getUsername());
//                            tspMessageRecordRepository.updateById(record);
//                        }
//                    } else {
//                        TspMessageRecord record = new TspMessageRecord();
//                        record.setSendTime(LocalDateUtils.parseToLocalDateTime(sendTime, LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS));
//                        record.setTspMessageId(message.getId());
//                        record.setCreateBy(SecurityUtils.getUsername());
//                        record.setUpdateBy(SecurityUtils.getUsername());
//                        tspMessageRecordRepository.save(record);
//                    }
//                }
//            }
//        } else {
//            List<Long> ids = tspMessageRecordRepository.findByTspMessageId(message.getId())
//                    .stream().map(TspMessageRecord::getId).collect(Collectors.toList());
//            if (ids.size() > 0) {
//                tspMessageRecordRepository.removeByIds(ids);
//            }
//        }


        BeanUtils.copyProperties(vo, message);
        message.setUpdateBy(SecurityUtils.getUsername());
        message.setUserLabels(vo.getUserLabels() == null ? null : vo.getUserLabels().toString());
        tspMessageRepository.updateById(message);
    }

    public void state(Long tspMessageId) {
        TspMessage message = tspMessageRepository.getById(tspMessageId);
        if (message == null) {
            ErrorEnum.TSP_MESSAGE_NULL_ERROR.throwErr();
        }
        message.setState(message.getState() == 1 ? 0 : 1);
        message.setUpdateBy(SecurityUtils.getUsername());
        tspMessageRepository.updateById(message);
    }

    public PageInfo<TspMessagePageListDTO> list(TspMessagePageListVO vo) {
        Wrapper<TspMessage> ew = tspMessageRepository.pageListEw(vo);
        Page<TspMessage> page = tspMessageRepository.page(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        ArrayList<TspMessagePageListDTO> dtos = new ArrayList<>();
        for (TspMessage record : page.getRecords()) {
            TspMessagePageListDTO dto = new TspMessagePageListDTO();
            BeanUtils.copyProperties(record, dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getTotal());
    }

    public TspMessageInfoDTO get(Long tspMessageId) {
        TspMessage message = tspMessageRepository.getById(tspMessageId);
        if (message == null) {
            ErrorEnum.TSP_MESSAGE_NULL_ERROR.throwErr();
        }
        TspMessageInfoDTO dto = new TspMessageInfoDTO();
        BeanUtils.copyProperties(message, dto);
        // 得到标签id
        String label = message.getUserLabels();
        if ((label != null && !"".equals(label)) && !"[]".equals(label)) {
            List<String> strings = Arrays.asList(label.split(","));
            List<Long> labelLong = new ArrayList<>();
            for (String string : strings) {
                if (string.contains("[")) {
                    string = string.replace("[", "");
                }
                if (string.contains("]")) {
                    string = string.replace("]", "");
                }
                if (string.contains(" ")) {
                    string = string.replace(" ", "");
                }
                labelLong.add(Long.valueOf(string));
            }
            dto.setUserLabels(labelLong);
        }


//不使用tspMessageRecord表
//        List<TspMessageRecord> records = tspMessageRecordRepository.findByTspMessageId(message.getId());
//        ArrayList<Map<String,String>> list = new ArrayList<>();
//        for (TspMessageRecord record : records) {
//            Map<String,String> time = new HashMap<>();
//            time.put("sendTimeStart",String.valueOf(record.getSendTime()));
//            list.add(time);
//        }
//        dto.setSendTimes(list);


        return dto;
    }
}
