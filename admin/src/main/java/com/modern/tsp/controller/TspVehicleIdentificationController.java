package com.modern.tsp.controller;

import com.modern.tsp.model.vo.TspVehicleIdentificationVO;
import com.modern.tsp.service.TspVehicleIdentificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Api(tags = "TSP - 对接短信-实名认证")
@RequestMapping("/tsp/identification")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspVehicleIdentificationController  {

    private final TspVehicleIdentificationService tspVehicleIdentificationService;

    @ApiOperation("获取实名认证")
    @PostMapping("/getRealName")
    public Map<String,Object> getRealName(@RequestBody @Valid TspVehicleIdentificationVO vo) {
        Map<String,Object> result = tspVehicleIdentificationService.getRealName(vo);
        return result;
    }

    /**
     * 推送车辆信息进行实名认证
     */
    @ApiOperation("实名认证详情")
    @GetMapping("/sendVehicleMessage/{id}")
    public Map<String,Object> sendVehicleMessage(@PathVariable("id") Long tspVehicleId) {
        TspVehicleIdentificationVO vo = new TspVehicleIdentificationVO();
        vo.setTspVehicleId(tspVehicleId);
        Map<String,Object> result = tspVehicleIdentificationService.sendVehicleMessage(vo);
        return  result;
    }

    /**
     * 接收车辆信息进行实名认证
     * 回调接口账号密码：
     * dianxinhuidiao
     * huidiaojiekou123456
     *
     * 调用方发起请求时携带认证头：
     authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImEzOTk4Yzk4LTc1MmItNGY3Yy05OWRjLTE2MWM3MWY4OTVmMCJ9.maTwUt3dN2PQt079p2VtY73nWYLigddMQipqS2PxWQeKirlssqLiSKvmuL9a4OK8OcqAjUwEcQDDX3y61L-n_w
     *
     * Redis中对应的token：
     login_tokens:a3998c98-752b-4f7c-99dc-161c71f895f0
     {"@type":"com.modern.common.core.domain.model.LoginUser","browser":"Chrome 13","deptId":100,"expireTime":1735275326366,"ipaddr":"175.2.123.72","loginLocation":"XX XX","loginTime":1735188926366,"os":"Windows 10","permissions":Set[],"token":"a3998c98-752b-4f7c-99dc-161c71f895f0","user":{"admin":false,"avatar":"","createBy":"admin","createTime":"2024-12-26T12:52:00","delFlag":"0","dept":{"children":[],"createBy":"","createTime":"2024-12-26T12:55:26.259","deptId":100,"deptName":"摩登汽车","isDelete":0,"leader":"刘昕","orderNum":"0","params":{"@type":"java.util.HashMap"},"parentId":0,"remark":"","status":"0","updateBy":"","updateTime":"2024-12-26T12:55:26.259"},"deptId":100,"email":"","isDelete":0,"loginIp":"","nickName":"电信实名认证回调","params":{"@type":"java.util.HashMap"},"password":"$2a$10$zSCuRDZK7B35lIKsK6K3p.bJLbE1bC3k1ubIc8WtKyMJFl..vWm3u","phonenumber":"13387654321","remark":"","roles":[{"admin":false,"createBy":"","createTime":"2024-12-26T12:55:26.259","dataScope":"1","deptCheckStrictly":false,"flag":false,"isDelete":0,"menuCheckStrictly":false,"params":{"@type":"java.util.HashMap"},"remark":"","roleId":137,"roleKey":"dianxinhuidiao","roleName":"dianxinhuidiao","roleSort":"0","status":"0","updateBy":"","updateTime":"2024-12-26T12:55:26.259"}],"sex":"0","status":"0","updateBy":"","updateTime":"2024-12-26T12:55:26.259","userId":100182,"userName":"dianxinhuidiao"},"userId":100182,"username":"dianxinhuidiao"}
     */
    @ApiOperation("接收实名认证")
    @PostMapping("/receiveRealNameResult")
    public Map<String,Object> receiveRealNameResult(@RequestBody @Valid Map<String,Object> receiveMap) {
        Map<String,Object> result = tspVehicleIdentificationService.receiveRealNameResult(receiveMap);
        return  result;
    }

    /**
     * 车辆卡号修改后，重新推送卡号信息
     */
    @ApiOperation("修改卡号信息")
    @GetMapping("/updateVehicleMessage/{id}")
    public Map<String,Object> updateVehicleMessage(@PathVariable("id") Long tspVehicleId) {
        TspVehicleIdentificationVO vo = new TspVehicleIdentificationVO();
        vo.setTspVehicleId(tspVehicleId);
        Map<String,Object> result = tspVehicleIdentificationService.updateVehicleMessage(vo);
        return  result;
    }

}
