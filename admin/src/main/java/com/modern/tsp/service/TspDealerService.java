package com.modern.tsp.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspDealer;
import com.modern.tsp.mapper.TspDealerMapper;
import com.modern.tsp.model.dto.TspDealerInfoDTO;
import com.modern.tsp.model.dto.TspDealerPageListDTO;
import com.modern.tsp.model.vo.TspDealerAddVO;
import com.modern.tsp.model.vo.TspDealerPageListVO;
import com.modern.tsp.repository.TspDealerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2022/10/15 15:25
 * @Version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspDealerService {

    private final TspDealerRepository tspDealerRepository;
    private final TspDealerMapper tspDealerMapper;

    /**
     * 经销商列表
     * @param vo
     * @return
     */
    public PageInfo<TspDealerPageListDTO> list(TspDealerPageListVO vo) {
        Wrapper<TspDealer> ew = tspDealerRepository.PageListEw(vo);
        Page<TspDealer> page = tspDealerRepository.page(Page.of(vo.getPageNum(),vo.getPageSize()),ew);
        ArrayList<TspDealerPageListDTO> dtos = new ArrayList<>();
        for (TspDealer record : page.getRecords()) {
            TspDealerPageListDTO dto = new TspDealerPageListDTO();
            BeanUtils.copyProperties(record,dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 添加经销商
     * @param vo
     */
    public void add(TspDealerAddVO vo) {
        if (tspDealerRepository.getByDealerName(vo.getDealerName()) != null) {
            ErrorEnum.TSP_DEALER_NAME_REPEAT_ERROR.throwErr();
        }
        if (tspDealerRepository.getByCode(vo.getDealerCode()) != null) {
            ErrorEnum.TSP_DEALER_CODE_REPEAT_ERROR.throwErr();
        }
        TspDealer tspDealer = new TspDealer();
        BeanUtils.copyProperties(vo,tspDealer);
        tspDealer.setIsDelete(0);
        tspDealer.setCreateBy(SecurityUtils.getUsername());
        tspDealer.setUpdateBy(SecurityUtils.getUsername());
        tspDealerRepository.save(tspDealer);
    }

    /**
     * 经销商详情(数据渲染)
     * @param tspDealerId
     * @return
     */
    public TspDealerInfoDTO get(Long tspDealerId) {
        log.info("根据经销商id查询经销商详情......{}",tspDealerId);
        TspDealer tspDealer = tspDealerRepository.getById(tspDealerId);
        if (tspDealer == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        TspDealerInfoDTO dto = new TspDealerInfoDTO();
        BeanUtils.copyProperties(tspDealer,dto);
        Map<String,String> dealerLngLat = new HashMap<>();
        // key：纬度
        dealerLngLat.put("lat",tspDealer.getDealerLat());
        // key：经度
        dealerLngLat.put("lng",tspDealer.getDealerLng());
        dto.setDealerLngLat(dealerLngLat);
        return dto;
    }

    /**
     * 修改经销商信息
     * @param vo
     */
    public void edit(TspDealerAddVO vo) {
        TspDealer tspDealer = tspDealerRepository.getById(vo.getTspDealerId());
        if (tspDealer == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        BeanUtils.copyProperties(vo,tspDealer);
        tspDealer.setUpdateBy(SecurityUtils.getUsername());
        tspDealerRepository.updateById(tspDealer);
    }

    /**
     * 删除经销商（是否删除设置为：是）
     * @param tspDealerId
     */
    public void delete(Long tspDealerId) {
        TspDealer tspDealer = tspDealerRepository.getById(tspDealerId);

        if (tspDealer == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        tspDealerMapper.deleteById(tspDealerId);


//        List<TspDealer> tspDealers = tspDealerRepository.lambdaQuery()
//                .eq(TspDealer::getIsDelete, 0)
//                .list();
//        if (tspDealers!=null&&tspDealers.size()>0){
//            tspDealerRepository.removeById(tspDealerId);
//        }

    }

    public void batchDelete(Long[] tspDealerIds) {
//        TspDealer tspDealer = tspDealerRepository.getById(tspDealerIds);
//        if (tspDealer == null) {
//            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
//        }
//        for (Long tspDealerId : tspDealerIds) {
//            tspDealerMapper.removeById(tspDealerId);
//        }

        for (Long dealerId : tspDealerIds){
            TspDealer tspDealer = tspDealerRepository.getById(dealerId);
            if (tspDealer == null) {
                ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
            }
        }

        List<TspDealer> tspDealers = tspDealerRepository.lambdaQuery()
                .eq(TspDealer::getIsDelete, 0)
                .list();
        for (Long tspDealerId : tspDealerIds) {
            if (CollectionUtil.isNotEmpty(tspDealers))
                tspDealerRepository.removeById(tspDealerId);
        }
    }
}
