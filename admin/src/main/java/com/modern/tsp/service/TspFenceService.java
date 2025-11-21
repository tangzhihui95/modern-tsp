package com.modern.tsp.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.tsp.domain.TspFence;
import com.modern.tsp.domain.TspFenceVehicle;
import com.modern.tsp.mapper.TspFenceMapper;
import com.modern.tsp.mapper.TspFenceVehicleMapper;
import com.modern.tsp.model.dto.TspFenceInfoDTO;
import com.modern.tsp.model.dto.TspFencePageListDTO;
import com.modern.tsp.model.dto.TspVehiclePageListDTO;
import com.modern.tsp.model.vo.TspFenceAddVO;
import com.modern.tsp.model.vo.TspFencePageListVO;
import com.modern.tsp.repository.TspFenceRepository;
import com.modern.tsp.repository.TspFenceVehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
public class TspFenceService {

    private final TspFenceRepository tspFenceRepository;
    private final TspFenceMapper tspFenceMapper;
    private final TspFenceVehicleRepository tspFenceVehicleRepository;
    private final TspFenceVehicleMapper tspFenceVehicleMapper;

    /**
     * 电子围栏列表
     *
     * @param vo
     * @return
     */
    public PageInfo<TspFencePageListDTO> list(TspFencePageListVO vo) {
        Wrapper<TspFence> ew = tspFenceRepository.PageListEw(vo);
        Page<TspFence> page = tspFenceRepository.page(Page.of(vo.getPageNum(), vo.getPageSize()), ew);
        ArrayList<TspFencePageListDTO> dtos = new ArrayList<>();
        for (TspFence record : page.getRecords()) {
            TspFencePageListDTO dto = new TspFencePageListDTO();
            //数据库查询的数据很多 把需要需要传入的数据传入到前端请求的类
            BeanUtils.copyProperties(record, dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 添加电子围栏
     *
     * @param vo
     */
    public void add(TspFenceAddVO vo) {
        if (tspFenceRepository.getByDealerName(vo.getFenceName()) != null) {
            ErrorEnum.TSP_DEALER_NAME_REPEAT_ERROR.throwErr();
        }

        TspFence tspFence = copyVOPropertiesToDO(vo, new TspFence());
        tspFence.setCreateBy(SecurityUtils.getUsername());
        tspFence.setIsDelete(0);
        tspFenceRepository.save(tspFence);

        List<TspVehiclePageListDTO> vehicleList = vo.getVehicleList();
        for (TspVehiclePageListDTO tspVehiclePageListDTO : vehicleList) {
            TspFenceVehicle tspFenceVehicle = new TspFenceVehicle();
            tspFenceVehicle.setTspFenceId(tspFence.getId());
            tspFenceVehicle.setTspVehicleId(tspVehiclePageListDTO.getId());
            tspFenceVehicleRepository.save(tspFenceVehicle);
        }
    }

    private TspFence copyVOPropertiesToDO(TspFenceAddVO vo, TspFence tspFence) {
        BeanUtils.copyProperties(vo, tspFence);
        // SendChannel：推送渠道
        tspFence.setSendChannel(vo.getSendChannel().toString());
        // FenceDay：监控日(1-星期一,2-星期二,3-星期三,4-星期四,5-星期五,6-星期六,7-星期日)
        tspFence.setFenceDay(vo.getFenceDay().toString());
        tspFence.setFenceTime(vo.getFenceTime().toString());
        tspFence.setUpdateBy(SecurityUtils.getUsername());
        tspFence.setFenceSource("后台");
        // FenceShape："围栏形状(1：圆型，2：多边形,3：矩形
        if (vo.getFenceShape() == 1) {
            Map<String, Double> circleMap = (Map<String, Double>) vo.getLngLatList();
            tspFence.setLng(circleMap.get("lng").toString());
            tspFence.setLat(circleMap.get("lat").toString());
        }
        if (vo.getFenceShape() == 2 || vo.getFenceShape() == 3) {
            List<Map<String, Double>> rectangleList = (List<Map<String, Double>>) vo.getLngLatList();
            tspFence.setLngLatList(rectangleList.toString());
        }

        return tspFence;
    }

    /**
     * 电子围栏详情(数据渲染)
     *
     * @param tspFenceId
     * @return
     */
    public TspFenceInfoDTO get(Long tspFenceId) {
        TspFence tspFence = tspFenceRepository.getById(tspFenceId);
        if (tspFence == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        TspFenceInfoDTO dto = new TspFenceInfoDTO();
        BeanUtils.copyProperties(tspFence, dto);
        // 天数
        char[] dayArray = tspFence.getFenceDay().toCharArray();
        List<Integer> dayList = new ArrayList<>();
        for (char day : dayArray) {
            if (StringUtils.isNumeric(String.valueOf(day))) {
                int fenceDay = Integer.parseInt(String.valueOf(day));
                dayList.add(fenceDay);
            }
        }
        // 时间段
        char[] timeArray = tspFence.getFenceTime().toCharArray();
        List<Integer> timeList = new ArrayList<>();
        for (char time : timeArray) {
            if (StringUtils.isNumeric(String.valueOf(time))) {
                int fenceTime = Integer.parseInt(String.valueOf(time));
                timeList.add(fenceTime);
            }
        }
        // 时间段
        char[] sendArray = tspFence.getSendChannel().toCharArray();
        List<Integer> sendChannel = new ArrayList<>();
        for (char sendEach : sendArray) {
            if (StringUtils.isNumeric(String.valueOf(sendEach))) {
                int send = Integer.parseInt(String.valueOf(sendEach));
                sendChannel.add(send);
            }
        }
        if (tspFence.getFenceShape() == 1) {
            Map<String, Double> circleMap = new HashMap<>();
            circleMap.put("lat", Double.valueOf(tspFence.getLat()));
            circleMap.put("lng", Double.valueOf(tspFence.getLng()));
            dto.setLngLatList(circleMap);
        }
        if (tspFence.getFenceShape() == 2 || tspFence.getFenceShape() == 3) {
            List<Map<String, Object>> lngLatList = new ArrayList<>();
            String lngLatListStr = tspFence.getLngLatList();
            String replace = lngLatListStr.replace("=", ":");
            JSONArray jsonArray = JSONArray.parseArray(replace);
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                String one = it.next().toString();
                JSONObject jo = JSONObject.parseObject(one);
                Map<String, Object> resultMap = jo;
                lngLatList.add(resultMap);
            }
            dto.setLngLatList(lngLatList);
        }
        List<TspVehiclePageListDTO> vehicleList = tspFenceVehicleMapper.getVehicleList(tspFenceId);
        for (TspVehiclePageListDTO tspVehiclePageListDTO : vehicleList) {
            String mode = "";
            if (sendChannel.get(0) == 1) {
                mode = "APP";
                if (sendChannel.get(1) == 1) {
                    mode = "APP/短信";
                }
            }
            if (sendChannel.get(0) == 0 && sendChannel.get(1) == 1) {
                mode = "短信";
            }
            tspVehiclePageListDTO.setMode(mode);
        }
        dto.setFenceDay(dayList);
        dto.setFenceTime(timeList);
        dto.setSendChannel(sendChannel);
        dto.setFenceStatus(tspFence.getFenceStatus().getValue());
        dto.setVehicleList(vehicleList);
        return dto;
    }

    /**
     * 修改电子围栏信息
     *
     * @param vo
     */
    public void edit(TspFenceAddVO vo) {
        TspFence tspFence = tspFenceRepository.getById(vo.getTspFenceId());
        if (tspFence == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        tspFence = copyVOPropertiesToDO(vo, tspFence);

        tspFenceRepository.updateById(tspFence);

//        Stream<TspVehiclePageListDTO> vehicleListStream = CollectionUtils.union(dbVehicleList, vo.getVehicleList()).stream();

        List<TspVehiclePageListDTO> dbVehicleList = tspFenceVehicleMapper.getVehicleList(vo.getTspFenceId());
        List<TspVehiclePageListDTO> editVehicleList = vo.getVehicleList();
        //删除或保留数据库中数据
        for (TspVehiclePageListDTO tspVehiclePageListDTO : dbVehicleList) {
            boolean isExist = editVehicleList.stream().anyMatch(vehicle -> vehicle.getId().longValue() == tspVehiclePageListDTO.getId().longValue());
            if (isExist) {
                //保留
            } else {
                //删除
                tspFenceVehicleRepository.deleteByFenceIdAndVehicleId(tspFence.getId(), tspVehiclePageListDTO.getId());
            }
        }
        //新增数据
        for (TspVehiclePageListDTO tspVehiclePageListDTO : editVehicleList) {
            boolean isNew = dbVehicleList.stream().noneMatch(vehicle -> vehicle.getId().longValue() == tspVehiclePageListDTO.getId().longValue());
            if (isNew) {
                //新增
                TspFenceVehicle tspFenceVehicle = new TspFenceVehicle();
                tspFenceVehicle.setTspFenceId(tspFence.getId());
                tspFenceVehicle.setTspVehicleId(tspVehiclePageListDTO.getId());
                tspFenceVehicleRepository.save(tspFenceVehicle);
            }
        }
    }

    /**
     * 删除电子围栏（是否删除设置为：是）
     *
     * @param tspFenceId
     */
    public void delete(Long tspFenceId) {
        TspFence tspFence = tspFenceRepository.getById(tspFenceId);
        if (tspFence == null) {
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        tspFenceVehicleRepository.deleteByFenceId(tspFenceId);
        tspFenceMapper.deleteById(tspFenceId);
    }

}
