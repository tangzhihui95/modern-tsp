package com.modern.tsp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.utils.SecurityUtils;
import com.modern.tsp.domain.TspTboxVersion;
import com.modern.tsp.repository.TspTboxVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/8 10:16
 */

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspTboxVersionService extends TspBaseService{
    private final TspTboxVersionRepository tspTboxVersionRepository;
    public void updateVersion(TspTboxVersion tspTboxVersion) {
        // set版本值
        tspTboxVersion.setVersion("1.1");
        // 权限
        tspTboxVersion.setUpdateBy(SecurityUtils.getUsername());
        tspTboxVersionRepository.updateById(tspTboxVersion);
    }

    public TspTboxVersion get(String vin) {
        QueryWrapper<TspTboxVersion> ew = new QueryWrapper<>();
        ew.eq("vin",vin);
        return tspTboxVersionRepository.getOne(ew);
    }
}
