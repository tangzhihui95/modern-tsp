package com.modern.tsp.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.domain.entity.SysDictData;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.DictUtils;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.StringUtils;
import com.modern.exInterface.entity.VehicleAlert;
import com.modern.exInterface.repository.VehicleAlertRepository;
import com.modern.tsp.abstracts.findData.TspFactory;
import com.modern.tsp.abstracts.findData.data.find.TspVehicleBaseFindDataService;
import com.modern.tsp.domain.TspDealer;
import com.modern.tsp.domain.TspVehicle;
import com.modern.tsp.domain.TspVehicleLicense;
import com.modern.tsp.domain.TspVehicleStdModel;
import com.modern.tsp.mapper.TspDealerMapper;
import com.modern.tsp.mapper.TspVehicleMapper;
import com.modern.tsp.model.dto.*;
import com.modern.tsp.model.vo.TspVehicleAlertDataVO;
import com.modern.tsp.model.vo.TspVehicleFlowDataVO;
import com.modern.tsp.model.vo.TspVehicleOnlineDataVO;
import com.modern.tsp.model.vo.TspVehicleVolumeDataVO;
import com.modern.tsp.repository.TspVehicleLicenseRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import com.modern.tsp.repository.TspVehicleStdModeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleDataService extends TspBaseService {

    private final TspVehicleMapper tspVehicleMapper;

    private final TspVehicleRepository tspVehicleRepository;

    private final VehicleAlertRepository vehicleAlertRepository;

    private final TspVehicleLicenseRepository tspVehicleLicenseRepository;

    private final TspVehicleStdModeRepository tspVehicleStdModeRepository;

    private final TspDealerMapper tspDealerMapper;

    @Autowired
    private TspFactory tspFactory;

    @Autowired
    private AIOTService aIOTService;

    /**
     * 获取过去几天的时间
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(today);
    }

    public PageInfo<TspVehicleFlowDataDTO> getFlowData(TspVehicleFlowDataVO vo) {
        QueryWrapper<TspVehicle> ew = tspVehicleRepository.getFlowDataVehicleEw(vo);
        Page<TspVehicleFlowDataDTO> p = Page.of(vo.getPageNum(), vo.getPageSize());
        p.setOptimizeCountSql(false);  //关闭分页查询优化，否则复杂查询语句可能导致分页查询的Total与实际总数不一致
        IPage<TspVehicleFlowDataDTO> page = tspVehicleMapper.getPageListFlowData(p, ew);

        for (TspVehicleFlowDataDTO record : page.getRecords()) {
            TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(record.getId());
            if (license == null) {
                license = tspVehicleLicenseRepository.getByPlateCode(record.getPlateCode());
            }

            if (license != null) {
                record.setPlateCode(license.getPlateCode() == null ? null : license.getPlateCode());
            }

            TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(record.getTspVehicleStdModelId());

            if (stdModel != null) {
                record.setStdModeName(stdModel.getStdModeName() == null ? null : stdModel.getStdModeName());
            }

            //TODO: edit
            if (page.getRecords().size() == 1) {
                String iccid = tspVehicleMapper.selectEquipmentICCIDByVin(record.getVin());
                record.setIccid(iccid);
                if (StringUtils.isNotEmpty(iccid)) {
                    String traffic = aIOTService.queryTraffic(iccid, "0");
                    log.error("queryTraffic iccid={}, result={}", iccid, traffic);
                    if (StringUtils.isNotEmpty(traffic)) {
                        record.setTraffic(traffic);
                    } else {
                        record.setTraffic(null);
                    }
                } else {
                    record.setTraffic(null);
                }
            }
        }
        return PageInfo.of(page);
    }

    public List<TspVehicleVolumeDataDTO> volumeData(TspVehicleVolumeDataVO vo) {
        if (vo.getStartTime() != null && vo.getEndTime() != null) {
            return this.dataStartTimeAndEndTime(vo);
        }
        if (vo.getTimeState() == null) {
            return tspVehicleMapper.findVolumeData();
        }
        TspVehicleBaseFindDataService findDataService = tspFactory.setFindType(vo.getTimeState());
        return findDataService.findVolumeData(vo);
    }

    /**
     * 告警统计
     *
     * @param vo 条件参数
     * @return 统计数据
     * @throws ServerException 字典异常
     */
    public List<TspVehicleAlertDataDTO> alertChartData(TspVehicleAlertDataVO vo) throws ServerException {

        if (checkAndApplyVO(vo)) {
            return new ArrayList<>();
        }

        List<SysDictData> alert_type = DictUtils.getDictCache("alert_type");
        if (alert_type == null) {
            throw new ServerException("字典查询为空");
        }

        List<VehicleAlert> list = vehicleAlertRepository
                .lambdaQuery()
                .select(VehicleAlert::getGeneralAlarmSign)
                .ne(VehicleAlert::getGeneralAlarmSign, 0)
                .in(!CollectionUtil.isEmpty(vo.getVinList()), VehicleAlert::getVin, vo.getVinList())
                .between(!Objects.isNull(vo.getStartTime()) && !Objects.isNull(vo.getEndTime()), VehicleAlert::getCreateTime, vo.getStartTime(), vo.getEndTime())
                .list();

        Map<String, Long> collect = list
                .stream()
                .filter(p -> !Objects.isNull(p) && !Objects.isNull(p.getGeneralAlarmSign()))
                .collect(Collectors.groupingBy((p) -> {
                    SysDictData sysDictData = alert_type
                            .stream()
                            .filter(j -> ((p.getGeneralAlarmSign() >>> Integer.parseInt(j.getDictValue())) & 1) == 1)
                            .findFirst()
                            .orElse(new SysDictData());
                    p.setGeneralAlarmSignText(sysDictData.getDictLabel());
                    if (p.getGeneralAlarmSignText() == null) {
                        return "其他";
                    }
                    return p.getGeneralAlarmSignText();
                }, Collectors.counting()));
        return collect
                .entrySet()
                .stream()
                .map(TspVehicleAlertDataDTO::new)
                .filter(p -> StringUtils.isNotBlank(p.getAlert()))
                .collect(Collectors.toList());

    }

    public Map<String, List<VolumeDTO>> volumeChartData(TspVehicleAlertDataVO vo) {
        if (checkAndApplyVO(vo)) {
            return new HashMap<>();
        }
        List<VolumeDTO> volumeDTOS = tspVehicleMapper.volumeChartData(vo);
        //获取所有的日期数据
        Set<String> dateList = volumeDTOS.stream().map(VolumeDTO::getDate).collect(Collectors.toSet());
        //对统计结果进行分组
        Map<String, List<VolumeDTO>> result = volumeDTOS.stream().collect(Collectors.groupingBy(VolumeDTO::getCarType));

        result.forEach((k, v) -> {
            //当前车型在哪些日期有接入量。
            Set<String> carTypeDateList = v.stream().map(VolumeDTO::getDate).collect(Collectors.toSet());
            //拆分出没有统计的日期
            Set<String> carTypeNoHaveDateList = dateList.stream().filter(p -> !carTypeDateList.contains(p)).collect(Collectors.toSet());
            //对没有的日期数据进行初始化。
            List<VolumeDTO> initDateList = carTypeNoHaveDateList.stream().map(p -> {
                VolumeDTO volumeDTO = new VolumeDTO();
                volumeDTO.setDate(p);
                volumeDTO.setCarType(k);
                volumeDTO.setCount(0);
                return volumeDTO;
            }).collect(Collectors.toList());

            v.addAll(initDateList);
            //进行日期排序
            v.sort((o1, o2) -> {
                LocalDate parse1 = LocalDate.parse(o1.getDate());
                LocalDate parse2 = LocalDate.parse(o2.getDate());
                return parse1.compareTo(parse2);
            });
        });

        return result;
    }

    private Boolean checkAndApplyVO(TspVehicleAlertDataVO vo) {
        if (vo.getTimeState() != null) {
            int days = 0;
            switch (vo.getTimeState()) {
                case 1:
                    days = 7;
                    break;
                case 2:
                    days = 15;
                    break;
                case 3:
                    days = 30;
                    break;
                case 90:
                    days = 90;
                    break;
                case 180:
                    days = 180;
                    break;
                case 365:
                    days = 365;
                    break;
                case 1095:
                    days = 1095;
                    break;
                case 1825:
                    days = 1825;
                    break;
            }
            String startTime = getPastDate(days);
            vo.setStartTime(LocalDateUtils.parseToLocalDateTime(startTime, LocalDateUtils.FORMAT_YYYY_MM_DD).toLocalDate());
            vo.setEndTime(LocalDate.now());
        }

        boolean canForward = false;
        if (StringUtils.isNotBlank(vo.getSearch())) {
            TspVehicleOnlineDataVO tempVo = new TspVehicleOnlineDataVO();
            tempVo.setSearch(vo.getSearch());
            List<String> strings = tspVehicleMapper.selectVin(tempVo);
            vo.getVinList().addAll(strings);
            canForward = true;
        }
        if (
                StringUtils.isNotBlank(vo.getProvinceValue())
                        || StringUtils.isNotBlank(vo.getCityValue())
                        || StringUtils.isNotBlank(vo.getAreaValue())

        ) {
            TspVehicleOnlineDataVO tempVo = new TspVehicleOnlineDataVO();
            BeanUtils.copyProperties(vo, tempVo);
            tempVo.setSearch(null);
            List<String> vinList = tspVehicleMapper.selectVin(tempVo);
            if (CollectionUtil.isNotEmpty(vinList)) {
                vo.getVinList().addAll(vinList);
            }
            canForward = true;
        }
        //当用户选中某个经销商进行统计时
        if (Objects.nonNull(vo.getDealerId())) {
            List<String> vinList = tspVehicleMapper.selectVinByDealerId(vo.getDealerId());
            if (CollectionUtil.isNotEmpty(vinList)) {
                vo.getVinList().addAll(vinList);
            }
            canForward = true;
            //用户没有选中某个经销商，但是进行了范围经销商筛选
        } else if (StringUtils.isNotBlank(vo.getDealerAddressValue())) {

            List<TspDealer> tspDealers = tspDealerMapper.saleNameListByLikeAddress(vo.getDealerAddressValue());
            if (CollectionUtil.isNotEmpty(tspDealers)) {
                Set<Long> dealerIds = tspDealers.stream().map(TspDealer::getId).collect(Collectors.toSet());
                List<String> vinList = tspVehicleMapper.selectVinByDealerIds(dealerIds);
                if (CollectionUtil.isNotEmpty(vinList)) {
                    vo.getVinList().addAll(vinList);
                }
            }
            canForward = true;
        }

        return canForward && vo.getVinList().size() == 0;
    }

}
