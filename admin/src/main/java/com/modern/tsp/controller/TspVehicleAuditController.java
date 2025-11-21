package com.modern.tsp.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/6/20 11:39
 * <p>
 * 湖南成为科技有限公司 Copyright © 2016 HuNan Become Technology Co., Ltd. All rights reserved
 */
@RestController
@Api(tags = "TSP - 车辆审核认证")
@RequestMapping("/tsp/vehicleAudit")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleAuditController {
}
