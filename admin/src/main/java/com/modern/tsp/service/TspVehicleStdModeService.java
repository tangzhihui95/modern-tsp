package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.constant.Constants;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.tsp.domain.TspVehicleStdModel;
import com.modern.tsp.domain.TspVehicleStdModelExtra;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.TspVehiclePageListVO;
import com.modern.tsp.model.vo.TspVehicleStdModelAddVO;
import com.modern.tsp.model.vo.TspVehicleStdModelExtraAddVO;
import com.modern.tsp.repository.TspVehicleStdModeRepository;
import com.modern.tsp.repository.TspVehicleStdModelExtraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/30 15:04
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleStdModeService extends TspBaseService {

    private final TspVehicleStdModeRepository tspVehicleStdModeRepository;
    private final TspVehicleStdModelExtraService tspVehicleStdModelExtraService;
    private final TspVehicleStdModelExtraRepository tspVehicleStdModelExtraRepository;

    @Transactional(rollbackFor = ServiceException.class)
    public void add(TspVehicleStdModelAddVO vo) {
        log.info("车型添加......");
        TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getByStdModeNameAndModelId(vo.getStdModeName(),vo.getTspVehicleModelId());
        if (stdModel != null) {
            ErrorEnum.TSP_VEHICLE_STD_MODEL_TSP_VEHICLE_STD_MODEL_ADD_ERR.throwErr();
        }
        stdModel = new TspVehicleStdModel();
        BeanUtils.copyProperties(vo, stdModel);
        stdModel.setUpdateBy(SecurityUtils.getUsername());
        stdModel.setCreateBy(SecurityUtils.getUsername());
        tspVehicleStdModeRepository.save(stdModel);

        // 新增额外扩展的车型 必须这个值不为null（里面包括车型id 设备id 续航里程等等）
        if (vo.getStdModelExtraAddVO() != null) {
            TspVehicleStdModelExtraAddVO extraAddVO = vo.getStdModelExtraAddVO();
            extraAddVO.setTspVehicleStdModelId(stdModel.getId());
            tspVehicleStdModelExtraService.add(extraAddVO);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void edit(TspVehicleStdModelAddVO vo) {
        log.info("进行车型编辑......");
        if (vo.getTspVehicleStdModelId() == null) {
            throw new RuntimeException("型号ID不能为空");
        }
        TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(vo.getTspVehicleStdModelId());
        if (stdModel == null) {
            ErrorEnum.TSP_VEHICLE_STD_MODEL_NULL_ERR.throwErr();
        }

        // 由于型号名称存在多种 可能会出现型号不是同一个 所以必须判断名称和型号必须为同一个
        // 比如车辆型号名称是SYL 但是很多车辆可能会有这个相同的型号名称 所以必须每个型号id 对应某个型号名称
        TspVehicleStdModel stdModelName = tspVehicleStdModeRepository.getByStdModeNameAndModelId(vo.getStdModeName(),vo.getTspVehicleModelId());
        if (stdModelName != null){
            if (!vo.getTspVehicleStdModelId().equals(stdModelName.getId())) {
                // 未找到该车型型号
                ErrorEnum.TSP_VEHICLE_STD_MODEL_NULL_ERR.throwErr();
            }
        }

        BeanUtils.copyProperties(vo, stdModel);
        stdModel.setUpdateBy(SecurityUtils.getUsername());
        tspVehicleStdModeRepository.updateById(stdModel);

        // 扩展信息存在才能set进行 才能编辑
        if (vo.getStdModelExtraAddVO() != null) {
            TspVehicleStdModelExtraAddVO extraAddVO = vo.getStdModelExtraAddVO();
            extraAddVO.setTspVehicleStdModelId(stdModel.getId());
            tspVehicleStdModelExtraService.edit(extraAddVO);
        }
    }

    public TspVehicleStdModelInfoDTO get(Long tspVehicleStdModelId) {
        log.info("根据车型id查询详情......{}",tspVehicleStdModelId);
        TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(tspVehicleStdModelId);
        if (stdModel == null){
            ErrorEnum.TSP_VEHICLE_STD_MODEL_NULL_ERR.throwErr();
        }
        TspVehicleStdModelInfoDTO dto = new TspVehicleStdModelInfoDTO();
        BeanUtils.copyProperties(stdModel,dto);
        TspVehicleStdModelExtra stdModelExtra = tspVehicleStdModelExtraRepository.getByTspStdModelId(tspVehicleStdModelId);

        // 型号扩展信息不存在 创建扩展信息对象
        if (stdModelExtra == null) {
            stdModelExtra = new TspVehicleStdModelExtra();
        }

        // 把DTO扩展信息属性赋值，再返回
        dto.setStdModelExtra(stdModelExtra);
        return dto;
    }

    public List<TspVehicleStdModelSelectListDTO> select() {
       log.info("车辆型号下拉列表......");
        QueryWrapper<TspVehicleStdModel> ew = new QueryWrapper<>();
        List<TspVehicleStdModelSelectListDTO> dtos = new ArrayList<>();
        tspVehicleStdModeRepository.list(ew).forEach(item->{
            TspVehicleStdModelSelectListDTO dto = new TspVehicleStdModelSelectListDTO();
            BeanUtils.copyProperties(item,dto);
            dtos.add(dto);
        });
        return dtos;
    }

    public void delete(Long tspVehicleStdModelId) {
        log.info("根据车型id进行删除......{}",tspVehicleStdModelId);
        TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(tspVehicleStdModelId);
        if (stdModel == null) {
            ErrorEnum.TSP_VEHICLE_STD_MODEL_NULL_ERR.throwErr();
        }
        tspVehicleStdModeRepository.removeById(tspVehicleStdModelId);
    }

    public TspVehicleStdModelLabelMapDTO getLabelMap() {
        return tspVehicleStdModeRepository.getLabelMap();
    }


}
