package com.modern.common.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.base.ServicePlusImpl;
import com.modern.common.core.domain.Provinces;
import com.modern.common.core.mapper.ProvincesMapper;
import com.modern.tsp.model.dto.ProvincesTreeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 区县行政编码字典表 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-22
 */
@Service
public class ProvincesService extends ServicePlusImpl<ProvincesMapper, Provinces, Provinces> {

    public List<Provinces> getByDepth(int i) {
        QueryWrapper<Provinces> ew = new QueryWrapper<>();
        ew.eq(Provinces.DEPTH, i);
        return this.list(ew);
    }

    public List<Provinces> findCitys(String[] provinceNames) {
        List<Integer> ids = this.findInProvinceNamesByIds(provinceNames);
        QueryWrapper<Provinces> ew = new QueryWrapper<>();
        ew.eq(Provinces.DEPTH, 2);
        ew.in(Provinces.PARENT_ID, ids);
        return this.list(ew);
    }

    private List<Integer> findInProvinceNamesByIds(String[] provinceNames) {
        QueryWrapper<Provinces> ew = new QueryWrapper<>();
        ew.eq(Provinces.DEPTH, 1);
        ew.in(Provinces.CITY_NAME, Arrays.stream(provinceNames).toArray());
        return this.list(ew).stream().map(Provinces::getId).collect(Collectors.toList());
    }

//    public List<ProvincesTreeDTO> provincesTree() {
//        return provincesTrees();
//    }

    public List<ProvincesTreeDTO> provincesTrees() {
        QueryWrapper<Provinces> provincesEw = new QueryWrapper<>();
        provincesEw.eq(Provinces.DEPTH, 1);
        // 获取所有省份
        List<Provinces> provinces = this.list(provincesEw);
        ArrayList<ProvincesTreeDTO> dtos = new ArrayList<>();
        for (Provinces province : provinces) {
            ProvincesTreeDTO dto = new ProvincesTreeDTO();
            dto.setLabel(province.getCityName());
            dto.setValue(province.getCityName());
//            if (parentId != null) {
//                List<ProvincesTreeDTO> cityTrees = cityTrees(parentId);
//                dto.setChildren(cityTrees);
//            }
            dtos.add(dto);
        }
        return dtos;
    }


    public List<ProvincesTreeDTO> cityTrees(String value) {
        QueryWrapper<Provinces> cityEw = new QueryWrapper<>();
        cityEw.eq(Provinces.DEPTH, 2);
        cityEw.like(Provinces.MERGER_NAME, value);
        // 获取所有城市
        List<Provinces> provinces = this.list(cityEw);
        ArrayList<ProvincesTreeDTO> dtos = new ArrayList<>();
        for (Provinces province : provinces) {
            ProvincesTreeDTO dto = new ProvincesTreeDTO();
            dto.setLabel(province.getCityName());
            dto.setValue(province.getCityName());
//            if (parentId != null) {
//                List<ProvincesTreeDTO> areaTrees = areaTrees(parentId);
//                dto.setChildren(areaTrees);
//            }
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProvincesTreeDTO> areaTrees(String value) {
        QueryWrapper<Provinces> cityEw = new QueryWrapper<>();
        cityEw.eq(Provinces.DEPTH, 3);
        cityEw.like(Provinces.MERGER_NAME, value);
        // 获取所有区县
        List<Provinces> provinces = this.list(cityEw);
        ArrayList<ProvincesTreeDTO> dtos = new ArrayList<>();
        for (Provinces province : provinces) {
            ProvincesTreeDTO dto = new ProvincesTreeDTO();
            dto.setLabel(province.getCityName());
            dto.setValue(province.getCityName());
            dtos.add(dto);
        }
        return dtos;
    }

}
