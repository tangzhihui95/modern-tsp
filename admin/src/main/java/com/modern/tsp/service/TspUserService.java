package com.modern.tsp.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.annotation.DataScope;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.FrontPageInfo;
import com.modern.common.core.service.ProvincesService;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.tsp.domain.TspTag;
import com.modern.tsp.domain.TspUser;
import com.modern.tsp.domain.TspUserDTO;
import com.modern.tsp.mapper.TspUserMapper;
import com.modern.tsp.model.dto.TspUserPageListDTO;
import com.modern.tsp.model.vo.TspUserAddVO;
import com.modern.tsp.model.vo.TspUserExcelVO;
import com.modern.tsp.model.vo.TspUserPageListVO;
import com.modern.tsp.repository.TspTagRepository;
import com.modern.tsp.repository.TspUserRepository;
import com.modern.tsp.repository.TspVehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * <p>
 * 摩登 - TSP - TSP用户 服务实现类
 * </p>
 *
 * @author piaomiao
 * @since 2022-06-13
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspUserService extends TspBaseService {

    private final TspVehicleRepository tspVehicleRepository;
    private final TspUserRepository tspUserRepository;
    private final TspUserMapper tspUserMapper;
    private final TspTagRepository tspTagRepository;
    private final ProvincesService provincesService;

    public void add(TspUserAddVO vo) {
        TspUser tspUser = tspUserRepository.getByMobile(vo.getMobile());
        if (tspUser != null) {
            ErrorEnum.TSP_USER_USER_NOT_NULL_ERR.throwErr();
        }
        tspUser = new TspUser();
        BeanUtils.copyProperties(vo, tspUser);
        if (vo.getLabel() != null && vo.getLabel().size() != 0) {
            for (Long tspTagId : vo.getLabel()) {
                TspTag tspTag = tspTagRepository.getById(tspTagId);
                tspTag.setTagCount(tspTag.getTagCount() + 1);
                tspTagRepository.updateById(tspTag);
            }
            tspUser.setLabel(vo.getLabel().toString());
        }
        tspUser.setUpdateBy(SecurityUtils.getUsername());
        tspUser.setCreateBy(SecurityUtils.getUsername());
        tspUser.setIsDelete(0);
        if (StringUtils.isNotEmpty(tspUser.getUserCardBackImg()) && StringUtils.isNotEmpty(tspUser.getUserCardFrontImg())) {
            tspUser.setRealNameAudit(1);
        }
        tspUserRepository.save(tspUser);
    }

    public void edit(TspUserAddVO vo) {
        if (vo.getTspUserId() == null) {
            throw new ServiceException("用户ID不能为空");
        }
        TspUser mobile = tspUserRepository.getByMobile(vo.getMobile());
        TspUser tspUser = tspUserRepository.getById(vo.getTspUserId());
        if (tspUser == null) {
            ErrorEnum.TSP_USER_USER_NULL_ERR.throwErr();
        }
        if (mobile != null && !(mobile.getId()).equals(tspUser.getId())) {
            ErrorEnum.TSP_USER_USER_NOT_NULL_ERR.throwErr();
        }
        // 得到标签id
        String labelStr = tspUser.getLabel();
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
        List<Long> newLabelList = vo.getLabel();
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
        BeanUtils.copyProperties(vo, tspUser);
        tspUser.setLabel(vo.getLabel() == null ? null : vo.getLabel().toString());
        if (StringUtils.isNotEmpty(tspUser.getUserCardBackImg()) && StringUtils.isNotEmpty(tspUser.getUserCardFrontImg())) {
            tspUser.setRealNameAudit(1);
        }
        tspUserRepository.updateById(tspUser);
    }

    // @DataScope若以框架注解 不需要我们手动在sql语句后面加上过滤条件了
    @DataScope(userAlias = "sysu", deptAlias = "sysd")
    public FrontPageInfo<TspUserPageListDTO> getPageList(TspUserPageListVO vo) {
        // 过滤：like（手机号 姓名） 时间降序排序
        QueryWrapper<TspUser> listEw = tspUserRepository.getPageListEw(vo);
        Page<TspUser> page = tspUserRepository.page(Page.of(vo.getPageNum(), vo.getPageSize()), listEw);
        List<TspUserPageListDTO> dtos = new ArrayList<>();
        page.getRecords().forEach(item -> {
            TspUserPageListDTO dto = new TspUserPageListDTO();
            BeanUtils.copyProperties(item, dto);
            Integer count = tspVehicleRepository.countByTspUserId(item.getId());
            dto.setVehicleCount(count);
//            dto.setRegTime(item.getCreateTime().toString().replace("T"," "));
            dto.setRegTime(item.getCreateTime());
            dtos.add(dto);
        });
        return FrontPageInfo.of(dtos, vo.getPageNum(), vo.getPageSize(), page.getTotal());
    }

    /*
    在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,
    加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
     */
    // 加入此注解 此处为事务性 原子性  误删可以实现回滚
    @Transactional(rollbackFor = Exception.class)
    public void deletes(Long[] tspUserIds) {
        log.info("根据{}批量删除......",tspUserIds);
        if (tspUserIds.length > 0) {
            for (Long tspUserId : tspUserIds) {
                TspUser tspUser = tspUserRepository.getById(tspUserId);
                if (tspUser == null) {
                    // 所选列表中存在未知用户,请检查
                    ErrorEnum.TSP_USER_USERS_NULL_ERR.throwErr();
                }
                tspUserRepository.removeById(tspUserId);
                // 得到标签id
                String labelStr = tspUser.getLabel();
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
            }
        }
    }

    @SneakyThrows
    public String importUser(MultipartFile file, Boolean isUpdateSupport) {
        ExcelUtil<TspUserExcelVO> util = new ExcelUtil<>(TspUserExcelVO.class);
        List<TspUserExcelVO> dtos = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TspUserExcelVO dto : dtos) {
            try {
                // 数据校验
                Map<String,Object> checkMap = toCheckUser(dto, failureMsg, failureNum);
                failureNum = (Integer) checkMap.get("failureNum");
                failureMsg = (StringBuilder) checkMap.get("failureMsg");
                if (failureNum == 0) {
                    TspUser tspUser = tspUserRepository.getByMobile(dto.getMobile());
                    Integer addressId = tspUserMapper.getAddress("中国," + dto.getProvince() + "," + dto.getCity() + "," + dto.getArea());
                    if (addressId == null) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、地址 ").append(dto.getProvince())
                                .append(dto.getCity()).append(dto.getArea()).append(" 填写不正确");
                    }
                    else {
                        // 新增
                        if (tspUser == null) {
                            tspUser = new TspUser();
                            BeanUtils.copyProperties(dto, tspUser);
                            if (dto.getLabel() != null && !"".equals(dto.getLabel())) {
                                TspTag tag = tspTagRepository.getByDealerName(dto.getLabel());
                                List<Long> label = new ArrayList<>();
                                if (tag != null) {
                                    tag.setTagCount(tag.getTagCount() + 1);
                                    tspTagRepository.updateById(tag);
                                    label.add(tag.getId());
                                    tspUser.setLabel(label.toString());
                                } else {
                                    tspUser.setLabel(null);
                                }
                            }
                            tspUser.setCreateBy(SecurityUtils.getUsername());
                            tspUser.setUpdateBy(SecurityUtils.getUsername());
                            if (dto.getBirthday() != null && !"".equals(dto.getBirthday())) {
                                tspUser.setBirthday(LocalDate.parse(dto.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            }
                            tspUserRepository.save(tspUser);
                            successNum++;
                            successMsg.append("<br/>").append(successNum).append("、用户 ").append(dto.getRealName()).append(" 导入成功");
                        } else {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、用户 ").append(dto.getRealName()).append("的电话号码").append(dto.getMobile()).append(" 已被使用");
                        }
                    }
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、用户 " + dto.getRealName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    private Map<String, Object> toCheckUser(TspUserExcelVO dto, StringBuilder failureMsg, int failureNum) {
        Map<String,Object> checkMap = new HashMap<>();
        // 车主姓名
        if (dto.getRealName() == null || dto.getRealName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车主姓名 ").append(dto.getRealName()).append(" 不能为空");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 手机号码
        String phoneRegexp = "^\\d{11}$";
        if (dto.getMobile() == null || !dto.getMobile().matches(phoneRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、手机号码 ").append(dto.getMobile()).append(" 格式异常");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 身份证号
        String idRegexp = "^(\\d{17})(\\d|X|x)$";
        if (dto.getIdCard() == null || !dto.getIdCard().matches(idRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、身份证号 ").append(dto.getIdCard()).append(" 格式异常");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 生日
        String dateRegexp = "^([0-9]{4})(-([0-1][0-9]))(-[0-3][0-9])$";
        if ((dto.getBirthday() != null && !"".equals(dto.getBirthday())) && !dto.getBirthday().matches(dateRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、生日 ").append(dto.getBirthday()).append(" 格式异常");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 省份
        if (dto.getProvince() == null || dto.getProvince().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、注册地址（省份） ").append(dto.getProvince()).append(" 不能为空");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 市
        if (dto.getCity() == null || dto.getCity().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、注册地址（市） ").append(dto.getCity()).append(" 不能为空");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 区（县）
        if (dto.getArea() == null || dto.getArea().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、注册地址（区/县） ").append(dto.getArea()).append(" 不能为空");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 用户标签
        if (dto.getLabel() == null || dto.getLabel().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、用户标签 ").append(dto.getLabel()).append(" 不能为空");
            checkMap.put("failureNum",failureNum);
            checkMap.put("failureMsg",failureMsg);
            return checkMap;
        }
        // 用户标签
        if (dto.getLabel() != null && !dto.getLabel().equals("")) {
            TspTag tag = tspTagRepository.getByDealerName(dto.getLabel());
            if (tag == null) {
                failureNum++;
                failureMsg.append("<br/>").append(failureNum).append("、所填的用户标签无法在标签管理中找到对应标签 ");
                checkMap.put("failureNum", failureNum);
                checkMap.put("failureMsg", failureMsg);
                return checkMap;
            }
        }
        checkMap.put("failureNum",failureNum);
        checkMap.put("failureMsg",failureMsg);
        return checkMap;
    }

    public List<TspUserPageListDTO> exportList(TspUserPageListVO vo) {
        vo.setPageNum(1);
        vo.setPageSize(Integer.MAX_VALUE);
        return this.getPageList(vo).getList();
    }

    public TspUserDTO get(Long tspUserId) {
        TspUser tspUser = tspUserRepository.getById(tspUserId);
        TspUserDTO dto = new TspUserDTO();
        BeanUtils.copyProperties(tspUser, dto);
        // 得到标签id
        String label = tspUser.getLabel();
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
            dto.setLabel(labelLong);
        }
        if (tspUser == null) {
            ErrorEnum.TSP_USER_USER_NULL_ERR.throwErr();
        }
        return dto;
    }

    /**
     * 查询当前绑定记录
     *
     * @param tspUserId
     * @return
     */

    public List<Map<String, Object>> findCarInfo(Long tspUserId) {
        List<Map<String, Object>> mapList = tspUserMapper.findCarInfo(tspUserId);
        return getMaps(mapList);
    }


    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getMaps(List<Map<String, Object>> mapList) {
        if (mapList != null && mapList.size() == 1) {
            if (mapList.get(0).get("vin") == null) {
                return null;
            }

            for (Map<String, Object> map : mapList) {
                List<Map<String, Object>> cardInfoList = (List<Map<String, Object>>) JSONArray.parse((String) map.get("cardInfo"));
                if (cardInfoList != null && cardInfoList.size() != 0) {
                    for (Map<String, Object> cardInfoMap : cardInfoList) {
                        if (map.get("ICCID") != null) {
                            if (map.get("ICCID").equals(cardInfoMap.get("ICCID"))) {
                                map.replace("status", cardInfoMap.get("IsAuth").equals("true") ? "已实名认证" : "未实名认证");
                                break;
                            }
                        }
                    }
                }
            }
        }

        return mapList;
    }

    /**
     * 查询历史绑定记录
     *
     * @param tspUserId
     * @return
     */
    public List<Map<String, Object>> findHistory(Long tspUserId) {
        List<Map<String, Object>> mapList = tspUserMapper.findHistory(tspUserId);
        return getMaps(mapList);
    }
}
