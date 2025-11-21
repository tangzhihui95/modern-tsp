package com.modern.common.constant;


import com.modern.common.exception.ServiceException;

/**
 * @author piaomiao
 * @apiNode ErrorEnum
 * @date 2022/6/911:37
 * <p>
 * Copyright: Copyright (C) 2019 tlcd, Inc. All rights reserved.
 * <p>
 * Company: 湖南成为科技有限公司
 * 统一常量命名: 业务模块+具体产生错误的业务+ERR结尾 例如：SYS_USER_USER_NULL_ERR
 * type:  例如：1 递增区分业务
 * code: type数值+业务模块编号从0开始递增+错误异常编号从1开始递增  例如: 用户管理模块 添加异常 101 更新异常 102 角色模块 添加异常 111 更新异常 112
 * msg: 错误信息 例如: 用户不存在
 * SYS_USER_USER_NULL_ERR(1,101,msg)
 * code 全局唯一不能重复
 */
public enum ErrorEnum {

    SYS_USER_USER_NULL_ERR(1, 101, "用户不存在"),
    SYS_USER_PSW_IDENTICI_ERR(1, 102, "新密码不能与旧密码相同"),
    SYS_USER_PSW_ATYPISM_ERR(1, 103, "新密码与确认密码不一致"),
    SYS_USER_ROLE_NOT_PERMISSINON_ERR(1, 104, "你无权操作"),
    SYS_USER_BIND_VEHICLE_NULL_ERR(1, 105, "绑定车辆存在错误,请检查"),
    TSP_VEHICLE_VEHICLE_NULL_ERR(2, 106, "车辆不存在,请检查"),

    /**
     * 设备分类
     */
    TSP_EQUIPMENT_TYPE_NOT_NULL_ERR(3, 107, "设备分类已存在"),
    TSP_EQUIPMENT_TYPE_NULL_ERR(3, 108, "未找到设备分类"),
    TSP_EQUIPMENT_TYPE_DELETE_ERR(3, 109, "设备分类下存在设备型号，无法删除"),


    /**
     * 设备信息
     */
    TSP_EQUIPMENT_NOT_NULL_ERR(4, 112, "设备已存在"),

    TSP_EQUIPMENT_NULL_ERR(4, 113, "未找到设备信息,请检查"),
    TSP_EQUIPMENT_EQUIPMENT_DELETE_ERR(4, 114, "设备下存在绑定车辆，无法删除"),


    /**
     * 车辆分类
     */
    TSP_VEHICLE_TYPE_VEHICLE_TYPE_NOT_NULL_ERR(5, 115, "车辆分类已存在"),
    TSP_VEHICLE_TYPE_VEHICLE_TYPE_NULL_ERR(5, 116, "未找到车辆分类,请检查"),
    TSP_VEHICLE_TYPE_VEHICLE_MODEL_NOT_DELETE_ERR(5, 117, "车辆分类下存在车型，无法删除"),


    /**
     * 车辆车型
     */
    TSP_VEHICLE_MODEL_VEHICLE_MODEL_NOT_NULL_ERR(6, 118, "车辆车型已存在"),
    TSP_VEHICLE_MODEL_VEHICLE_MODEL_NULL_ERR(6, 119, "未找到该车型"),
    TSP_VEHICLE_MODEL_VEHICLE_MODEL_DELETE_ERR(6, 120, "车辆车型下存在型号车辆，无法删除"),


    /**
     * 车辆
     */
    TSP_VEHICLE_VEHICLE_CONFIGURE_NAME_NOT_NULL_ERR(7, 121, "车辆配置名称已存在"),
    TSP_VEHICLE_DELETE_STATE_ERR(7, 300, "删除失败！只能删除已解除绑定状态的车辆！"),
    TSP_VEHICLE_DELETES_STATE_ERR(7, 301, "删除失败！所选车辆中只能删除已解除绑定状态的车辆！"),

    TSP_VEHICLE_EQUIPMENT_BIND_DELETE_ERR(7,302,"删除失败！该车辆已绑定了设备！"),
    TSP_VEHICLE_EQUIPMENT_BIND_DELETES_ERR(7,303,"删除失败！所选车辆中已绑定了设备！"),


    TSP_VEHICLE_SOLD_NULL_ERR(7, 123, "未找到车辆销售信息,无法绑定"),
    TSP_EQUIPMENT_SCRAP_ERR(7, 124, "未解绑车辆不可进行报废操作,请先进行解绑"),
    TSP_VEHICLE_SCRAP_ERR(7, 125, "密码输入错误,请重新输入"),
    TSP_VEHICLE_NULL_AUDIT(7, 126, "未找到该车辆认证信息"),
    TSP_VEHICLE_NULL_SIGN_UP(7, 137, "车辆尚未被注册"),
    TSP_VEHICLE_VIN_EXIST(7, 138, "该VIN已经被其他车辆使用"),
    TSP_VEHICLE_NOT_FOUND(7, 158, "未找到该车辆实时信息，请检查车辆是否启动"),
    TSP_VEHICLE_NOT_NULL_PLATE_CODE(7, 168,"该车牌号不存在！"),
    TSP_VEHICLE_PLATE_CODE_EXIST(7,169,"该车牌号已存在"),



    /**
     * 用户
     */
    TSP_USER_USER_NOT_NULL_ERR(8, 127, "用户手机号已存在，不可重复添加"),
    TSP_USER_USER_NULL_ERR(8, 128, "未找到用户"),
    TSP_USER_USERS_NULL_ERR(8, 129, "所选列表中存在未知用户,请检查"),


    /**
     * 车型型号
     */
    TSP_VEHICLE_STD_MODEL_TSP_VEHICLE_STD_MODEL_ADD_ERR(9, 130, "车型型号已存在，不可重复添加"),
    TSP_VEHICLE_STD_MODEL_NULL_ERR(9, 131, "未找到该车型型号"),


    /**
     * 车辆告警
     */
    TSP_VEHICLE_ALERT_NULL_ERROR(10,132 , "未找到该记录"),

    /**
     * 告警事件规则
     */
    TSP_ALERT_EVENT_NULL_ERROR(10,133 ,"未找到该条数据" ),
    TSP_ALERT_EVENT_NAME_ERROR(10,134 ,"名称已存在" ),

    /**
     * 通知推送
     */
    TSP_MESSAGE_TITLE_REPEAT_ERROR(11, 135,"标题重复"),
    TSP_MESSAGE_NULL_ERROR(11, 136,"通知不存在" ),

    /**
     * 信息发布
     */
    TSP_INFORMATION_TITLE_REPEAT_ERROR(11, 140,"标题重复"),
    TSP_INFORMATION_NULL_ERROR(11, 141,"信息不存在" ),
    /**
     * 设备型号
     */
    TSP_EQUIPMENT_MODEL_NOT_NULL_ERR(12, 110, "设备型号已存在"),

    TSP_EQUIPMENT_MODEL_NULL_ERR(12, 111, "未找到该设备型号"),


    TSP_EQUIPMENT_MODEL_DELETE_ERR(13, 112,"设备型号下存在设备无法删除" ),

    /**
     * 经销商
     */
    TSP_DEALER_NAME_REPEAT_ERROR(14,142,"经销商名称重复"),
    TSP_DEALER_CODE_REPEAT_ERROR(14,143,"经销商编码重复"),
    TSP_DEALER_NULL_ERROR(14,144,"经销商不存在"),

    /**
     * 传入参数皆为空
     */
    TSP_PARAM_NULL_ERROR(15,145,"vin和车辆主键都是空值"),
    IDENTIFICATION_510_ERROR(15,146,"车辆Vin和车厂编码不匹配"),
    IDENTIFICATION_520_ERROR(15,147,"实名认证参数丢失，请联系管理员"),
    IDENTIFICATION_NAME_ERROR(15,148,"名称与实际认证不匹配，请检查输入名称是否正确"),
    IDENTIFICATION_CARD_ERROR(15,149,"证件号码与实际认证不匹配，请检查输入的身份证号码是否正确"),
    IDENTIFICATION_ICCID_ERROR(15,150,"该iccid在车厂中不存在"),
    IDENTIFICATION_FAIL_ERROR(15,151,"实名认证失败"),
    IDENTIFICATION_NOT_EXIST_ERROR(15,159,"该车辆暂无实名认证信息,请确认是否已完成认证"),

    /**
     * 设备信息
     */
    TSP_SN_NOT_UNIQ_ERR(4, 152, "该SN在设备中已存在"),
    TSP_ICCID_NOT_UNIQ_ERR(4, 153, "该ICCID在设备中已存在"),
    TSP_IMEI_NOT_UNIQ_ERR(4, 154, "该IMEI在设备中已存在"),
    TSP_ISMI_NOT_UNIQ_ERR(4, 155, "该IMSI在设备中已存在"),
    TSP_SIM_NOT_UNIQ_ERR(4, 156, "该SIM在设备中已存在"),
    TSP_VEHICLE_EQUIPMENT_EXIST(7,157,"该设备已与其它车辆绑定"),

    /**
     * 实名认证接口异常信息
     */
    IDENTIFICATION_ERR(16,160,"该设备已与其它车辆绑定"),
    IDENTIFICATION_CHECK_ERR(16,161,"缺少必须字段，请仔细检查"),
    IDENTIFICATION_USERNAME_ERR(16,162,"上传认证不通过，账号或密码错误"),
    IDENTIFICATION_DIANXIN_ERR(16,163,"电信认证平台系统异常"),
    IDENTIFICATION_SEND_ERR(16,164,"车卡信息推送失败"),
    IDENTIFICATION_NOT_FOUND_ERR(16,165,"服务不存在"),
    IDENTIFICATION_MOTOR_ENGINE_ERR(16,166,"电动机序列号和发动机号码必须有一个不为空"),
    IDENTIFICATION_OHTER_ERR(16,167,"车辆状态/新车标识存在空值"),
    IDENTIFICATION_MARKET_ERR(16,168,"销售信息不能为空"),
    IDENTIFICATION_EQUIPMENT_ERROR(15,169,"该车辆未有绑定设备信息"),
    IDENTIFICATION_EQUIPMENT_ONE_ERROR(15,170,"该车辆只有一次设备绑定记录，不满足更换条件"),

    CONFIG_DELETE_ERR(17,171,"内置参数无法删除,请先将参数设置为非系统内置"),
    IMPORT_EXCEL_ERR(17,172,"内置参数无法删除,请先将参数设置为非系统内置");

    private Integer type;
    private Integer code;
    private String msg;

    ErrorEnum(Integer type, Integer code, String msg) {
        this.type = type;
        this.code = code;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void throwErr(Exception e) {
        throw new ServiceException(this, e);
    }

    public void throwErr(String msg, Integer code, Integer type) {
        this.msg = msg;
        this.code = code;
        this.type = type;
        throw new ServiceException(this);
    }

    public void throwErr() {
        throw new ServiceException(this);
    }
}
