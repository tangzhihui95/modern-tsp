package com.modern.common.constant;

import io.jsonwebtoken.Claims;

/**
 * 通用常量信息
 *
 * @author piaomiao
 */
public class Constants
{

    /**
     * 统计查询
     */
    public static final String TEP_QUERY_DATE = "TepQueryDate";

    /**
     * 服务参数
     */
    public static final String SERVICE_PARAMETER = " service_parameter";

    /**
     * 号码
     */

    public static final String MDN = "mdn";

    /**
     * 查询时间
     */
    public static final String MONTH = "month";

    /**
     * RG（非必填）
     */
    public static final String RG = "rg";

    /**
     * APN（非必填）
     */
    public static final String APN = "apn";

    /**
     * 协议版本
     */
    public static final String VERSION = "version";

    /**
     * 用户
     */
    public static final String CONSUMER= "consumer";

    /**
     * 密钥
     */

    public static final String PASSWORD= "password";

    /**
     * 服务名称
     */

    public static final String SERVICE_NAME= "service_name";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
//    public static final String RESOURCE_PREFIX = "/profile";
    public static final String RESOURCE_PREFIX = "/home/modern/tsp/admin/upload";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi://";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap://";

    /**
     * 时间
     */
    public static final String TIME = " 00:00:00";
    public static final String LAST_TIME = " 23:59:59";

    /**
     * 查询时间名称
     */
    public static final String START_TIME = "beginTime";
    public static final String END_TIME = "endTime";


    /**
     * 删除标识
     */
//    public static final Integer DEL_NO = 0;
//    public static final Integer DEL_YES = 1;
    public static final Integer HAS_DELETED = 1;


    /**
     * 联表前缀
     */
    public static final String JOIN_TABLE_PREFIX_T = "t.";
    public static final String JOIN_TABLE_PREFIX_A = "a.";
    public static final String JOIN_TABLE_PREFIX_B = "b.";
    public static final String JOIN_TABLE_PREFIX_C = "c.";
    public static final String JOIN_TABLE_PREFIX_D = "d.";
    public static final String JOIN_TABLE_PREFIX_E = "e.";
    public static final String JOIN_TABLE_PREFIX_F = "f.";
    public static final String JOIN_TABLE_PREFIX_TV = "tv.";

    /**
     * 全部状态标识
     */
    public static final Long ZERO  = 0L;


    /**
     * 全部状态标识
     */
    public static final String ALL_TEXT  = "全部";
}
