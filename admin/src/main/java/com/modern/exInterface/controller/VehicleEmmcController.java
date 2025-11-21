package com.modern.exInterface.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.modern.common.core.Result;
import com.modern.common.core.redis.RedisCache;
import com.modern.exInterface.model.dto.VehicleEmmcDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/9/7 16:40
 */
@RestController
@Api(tags = "TSP - TBOX存储容量数据")
@RequestMapping("/tsp/vehicle/emmc")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleEmmcController {

    private final RedisCache redisCache;

    @ApiOperation("TBOX存储容量数据")
    @GetMapping("/getEmmc/{vin}")
    public Result<VehicleEmmcDTO> getEmmc(@PathVariable("vin") String vin) {
        Object o = redisCache.getCacheMapValue(vin, "VehicleEMMC");
        VehicleEmmcDTO dto = JSONObject.toJavaObject(JSONObject.parseObject(JSONObject.toJSONString(o)), VehicleEmmcDTO.class);
        Double capacity = Double.parseDouble(dto.getCapacity());
        Double usedCapacity = Double.parseDouble(dto.getUsedCapacity());
        if (dto.getCapacity().length() >= 6) {
            dto.setCapacity(Integer.parseInt(dto.getCapacity()) / 1024 / 1024 + "GB");
        } else if (dto.getCapacity().length() >= 5) {
            dto.setCapacity(Integer.parseInt(dto.getCapacity()) / 1024 + "MB");
        } else {
            dto.setCapacity(Integer.parseInt(dto.getCapacity()) + "KB");
        }

        if (dto.getUsedCapacity().length() >= 6) {
            dto.setUsedCapacity(Integer.parseInt(dto.getUsedCapacity()) / 1024 / 1024 + "GB");
        } else if (dto.getUsedCapacity().length() >= 5) {
            dto.setUsedCapacity(Integer.parseInt(dto.getUsedCapacity()) / 1024 + "MB");
        } else {
            dto.setUsedCapacity(Integer.parseInt(dto.getUsedCapacity()) + "KB");
        }
        if (String.valueOf((capacity - usedCapacity)).length() >= 6) {
            dto.setSurplusCapacity((capacity - usedCapacity) / 1024 / 1024+"GB");
        } else if (String.valueOf((capacity - usedCapacity)).length() >= 5) {
            dto.setSurplusCapacity((capacity - usedCapacity) / 1024 +"MB");
        } else {
            dto.setSurplusCapacity((capacity - usedCapacity)+"KB");
        }
        return Result.data(dto);
    }
}
