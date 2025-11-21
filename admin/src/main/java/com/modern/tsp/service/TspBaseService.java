package com.modern.tsp.service;

import com.modern.common.constant.Constants;
import com.modern.common.utils.file.FileUploadUtils;
import com.modern.tsp.config.TspUploadConfig;
import com.modern.tsp.mapper.TspVehicleMapper;
import com.modern.tsp.model.dto.TspVehicleVolumeDataDTO;
import com.modern.tsp.model.vo.TspVehicleVolumeDataVO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/7/19 10:01
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Service
public abstract class TspBaseService {
    @Resource
    private TspVehicleMapper tspVehicleMapper;

    @Resource
    private TspUploadConfig config;

    public List<TspVehicleVolumeDataDTO> dataStartTimeAndEndTime(TspVehicleVolumeDataVO vo) {
        return tspVehicleMapper.dataStartTimeAndEndTime(vo.getStartTime() + Constants.TIME, vo.getEndTime() + Constants.LAST_TIME);
    }

    @SneakyThrows
    public String upload(MultipartFile file, Integer type) {
        String property = System.getProperty("os.name");
        boolean isLinux = property != null && property.startsWith("Linux");
        String path = "";
        switch (type) {
            case 1:
                path = FileUploadUtils.upload(isLinux?config.getSalePath():"D://", file);
                break;
            case 2:
                path = FileUploadUtils.upload(isLinux?config.getUpPlatePath():"D://", file);
                break;
            case 3:
                path = FileUploadUtils.upload(isLinux?config.getAuditPath():"D://", file);
                break;
            case 4:
                path = FileUploadUtils.upload(isLinux?config.getUserPath():"D://", file);
                break;
            default:
                throw new RuntimeException("上传失败");
        }
        if(isLinux){
            path.replace(Constants.RESOURCE_PREFIX,"/image/");
        }else{
            path = path.replace("D://", "/image/");
        }
        return path;
    }
}
