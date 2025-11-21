package com.modern.task;

import com.modern.common.utils.LocalDateUtils;
import com.modern.tsp.domain.TspInformation;
import com.modern.tsp.mapper.TspInformationMapper;
import com.modern.tsp.repository.TspInformationRepository;
import com.modern.tsp.service.TspBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通过前台 系统监控->定时任务 调用
 */
@Slf4j
@Component("TspInformationTask")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspInformationTask extends TspBaseService {

    private final TspInformationMapper tspInformationMapper;
    private final TspInformationRepository tspInformationRepository;

    public void informationStatusChange() {
        log.debug("TSP - informationTask: 信息发布开始执行");
        // 得到所有未被删除的信息以及未下线的信息
        List<TspInformation> informationList = tspInformationMapper.getAllInformation();
        // 遍历
        int count = 0;
        if (informationList != null && informationList.size() != 0) {
            for (TspInformation tspInformation : informationList) {
                if (tspInformation.getTerm() != null && !"".equals(tspInformation.getTerm())) {
                    // 开始时间
                    String startTimeStr = tspInformation.getTerm().substring(1, 20);
                    LocalDateTime startTime = LocalDateUtils.parseToLocalDateTime(startTimeStr, LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
                    // 结束时间
                    String endTimeStr = tspInformation.getTerm().substring(23, tspInformation.getTerm().length() - 1);
                    LocalDateTime endTime = LocalDateUtils.parseToLocalDateTime(endTimeStr, LocalDateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
                    // 信息状态
                    int informationStatus = tspInformation.getInformationStatus();
                    // 如果当前时间大于等于开始时间，则将信息状态改为发布中
                    if (informationStatus == 0 && !LocalDateTime.now().isBefore(startTime)) {
                        tspInformation.setInformationStatus(1);
                        tspInformation.setTerm(null);
                        tspInformation.setUnloadTime(null);
                        count++;
                        tspInformationRepository.updateById(tspInformation);
                    }
                    // 如果当前时间大于等于结束时间，则将信息状态改为已下线
                    if (informationStatus == 1 && !LocalDateTime.now().isBefore(endTime)) {
                        tspInformation.setInformationStatus(2);
                        tspInformation.setTerm(null);
                        tspInformation.setUnloadTime(LocalDateTime.now());
                        count++;
                        tspInformationRepository.updateById(tspInformation);
                    }
                }
            }
        }
        if (count > 0) {
            log.info("TSP - informationTask: 修改条数-" + count + ",执行结束");
        } else {
            log.debug("TSP - informationTask: 修改条数-" + count + ",执行结束");
        }
    }
}
