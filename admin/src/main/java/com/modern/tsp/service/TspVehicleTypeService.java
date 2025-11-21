package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.FrontQuery;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.tsp.domain.TspVehicleModel;
import com.modern.tsp.domain.TspVehicleType;
import com.modern.tsp.model.dto.TspVehicleTypePageListDTO;
import com.modern.tsp.model.vo.TspVehicleTypeAddVO;
import com.modern.tsp.repository.TspVehicleModelRepository;
import com.modern.tsp.repository.TspVehicleTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 摩登 - TSP - 车辆分类 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleTypeService extends TspBaseService {
    private final TspVehicleTypeRepository tspVehicleTypeRepository;
    private final TspVehicleModelRepository tspVehicleModelRepository;
    public PageInfo<TspVehicleTypePageListDTO> getPageList(FrontQuery vo) {
        QueryWrapper<TspVehicleType> ew = new QueryWrapper<>();
        ew.orderByDesc(TspVehicleType.UPDATE_TIME);
        Page<TspVehicleType> page = tspVehicleTypeRepository.page(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        ArrayList<TspVehicleTypePageListDTO> dtos = new ArrayList<>();
        for (TspVehicleType vehicleType : page.getRecords()) {
            TspVehicleTypePageListDTO dto = new TspVehicleTypePageListDTO();
            BeanUtils.copyProperties(vehicleType,dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos,page.getCurrent(),page.getSize(),page.getTotal());
    }

    public void add(TspVehicleTypeAddVO vo) {
        TspVehicleType tspVehicleType = tspVehicleTypeRepository.getByTypeName(vo.getTypeName());
        if (tspVehicleType != null){
            ErrorEnum.TSP_VEHICLE_TYPE_VEHICLE_TYPE_NOT_NULL_ERR.throwErr();
        }
        tspVehicleType = new TspVehicleType();
        BeanUtils.copyProperties(vo,tspVehicleType);
        tspVehicleType.setCreateBy(SecurityUtils.getUsername());
        tspVehicleType.setUpdateBy(SecurityUtils.getUsername());
        tspVehicleTypeRepository.save(tspVehicleType);
    }

    public void edit(TspVehicleTypeAddVO vo) {
        if (vo.getTspVehicleTypeId() == null){
            throw new RuntimeException("车辆分类ID不能为空");
        }
        TspVehicleType vehicleType = tspVehicleTypeRepository.getById(vo.getTspVehicleTypeId());
        if (vehicleType == null){
            ErrorEnum.TSP_VEHICLE_TYPE_VEHICLE_TYPE_NULL_ERR.throwErr();
        }

        TspVehicleType tspVehicleType = tspVehicleTypeRepository.getByTypeName(vo.getTypeName());
        // 首先车辆分类名称得存在
        if (tspVehicleType != null){
            // 名称存在的情况，比较名称和车辆分类是否是同一个id （）
            if (!vehicleType.getId().equals(tspVehicleType.getId())){
                // 未找到车辆分类，请检查
                ErrorEnum.TSP_VEHICLE_TYPE_VEHICLE_TYPE_NULL_ERR.throwErr();
            }
        }
        BeanUtils.copyProperties(vo,vehicleType);
        vehicleType.setUpdateBy(SecurityUtils.getUsername());
        tspVehicleTypeRepository.updateById(vehicleType);
    }

    public void deletes(Long[] tspVehicleTypeIds) {
        log.info("车辆分类批量删除......{}",tspVehicleTypeIds);
        if (tspVehicleTypeIds.length == 0){
            throw new RuntimeException("车辆分类ID不能为空");
        }
        for (Long tspVehicleTypeId : tspVehicleTypeIds) {
            TspVehicleType vehicleType = tspVehicleTypeRepository.getById(tspVehicleTypeId);
            if (vehicleType == null){
                ErrorEnum.TSP_VEHICLE_TYPE_VEHICLE_TYPE_NULL_ERR.throwErr();
            }

            // 存在车型不能删除
            List<TspVehicleModel> tspVehicleModels = tspVehicleModelRepository.findByTspVehicleTypeId(tspVehicleTypeId);
            if (!CollectionUtils.isEmpty(tspVehicleModels)){
                // 车辆分类下存在车型，无法删除
                ErrorEnum.TSP_VEHICLE_TYPE_VEHICLE_MODEL_NOT_DELETE_ERR.throwErr();
            }
            tspVehicleTypeRepository.removeById(tspVehicleTypeId);
        }
    }

    public List<TspVehicleType> selectList(Long tspVehicleTypeId) {
        return tspVehicleTypeRepository.selectList(tspVehicleTypeId);
    }
}
