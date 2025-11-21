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
 * @date 2022/9/7 19:46
 */
@RestController
@RequestMapping("/tsp/message/record")
@Api(tags = "TSP - 通知推送记录")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspMessageRecordController {
}
