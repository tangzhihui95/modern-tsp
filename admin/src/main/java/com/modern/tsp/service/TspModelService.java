package com.modern.tsp.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspMessage;
import com.modern.tsp.domain.TspModel;
import com.modern.tsp.domain.TspTag;
import com.modern.tsp.mapper.TspModelMapper;
import com.modern.tsp.model.dto.TspMessagePageListDTO;
import com.modern.tsp.model.dto.TspModelDTO;
import com.modern.tsp.model.dto.TspTagInfoDTO;
import com.modern.tsp.model.vo.TspModelVO;
import com.modern.tsp.model.vo.TspTagAddVO;
import com.modern.tsp.repository.TspModelRepository;
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
public class TspModelService {

    private final TspModelRepository tspModelRepository;
    private final TspModelMapper tspModelMapper;

    /**
     * 短信模板列表
     * @return
     */
    public List<TspModel> list() {
//        List<TspModel> list = tspModelRepository.list().subList(1,5);
        // like：模板标题 模板内容
        List<TspModel> modelList = tspModelMapper.getList();
        return modelList;
    }

    public PageInfo<TspModelDTO> listModel(TspModelVO vo) {
        Wrapper<TspModel> tspModelWrapper = tspModelRepository.pageListEw(vo);
        Page<TspModel> page = tspModelRepository.page(Page.of(vo.getPageNum(),vo.getPageSize()),tspModelWrapper);
        ArrayList<TspModelDTO> dtos = new ArrayList<>();
        for (TspModel record : page.getRecords()) {
            TspModelDTO dto = new TspModelDTO();
            BeanUtils.copyProperties(record, dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 添加短信模板
     * @param vo
     */
    public void add(TspModel vo) {

        if (tspModelRepository.getByDealerName(vo.getModelTitle()) != null) {
            ErrorEnum.TSP_DEALER_NAME_REPEAT_ERROR.throwErr();
        }
        TspModel tspModel = new TspModel();
        BeanUtils.copyProperties(vo,tspModel);
        tspModel.setCreateBy(SecurityUtils.getUsername());
        tspModel.setUpdateBy(SecurityUtils.getUsername());
        tspModelRepository.save(tspModel);
    }

    /**
     * 短信模板详情(数据渲染)
     * @param tspModelId
     * @return
     */
    public TspModel get(Long tspModelId) {

        TspModel tspModel = tspModelRepository.getById(tspModelId);
        if (tspModel == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        return tspModel;
    }

    /**
     * 修改标签信息
     * @param vo
     */
    public void edit(TspModel vo) {
        log.info("修改标签信息......{}",vo);
        TspModel tspModel = tspModelRepository.getById(vo.getId());
        if (tspModel == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        BeanUtils.copyProperties(vo,tspModel);
        tspModel.setUpdateBy(SecurityUtils.getUsername());
        tspModelRepository.updateById(tspModel);
    }

    /**
     * 删除短信模板（是否删除设置为：是）
     * @param tspModelId
     */
    public void delete(Long tspModelId) {

        TspModel tspModel = tspModelRepository.getById(tspModelId);
        if (tspModel == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        tspModelRepository.removeById(tspModelId);
    }

    /**
     * 标签下拉列表
     * @param tagType
     * @return
     */
    public List<Map<String,Object>> getLabel(Integer tagType) {
        List<Map<String,Object>> resultList = tspModelMapper.getLabel(tagType);
        return resultList;
    }
}
