package com.modern.common.core.domain;

import com.modern.common.constant.HttpStatus;
import com.modern.common.core.ResultErrInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.LocalDateUtils;
import com.modern.common.utils.StringUtils;

import java.util.HashMap;

/**
 * 操作消息提醒
 *
 * @author piaomiao
 */
public class AjaxResult<T> extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 */
	public static final String CODE_TAG = "code";

	/**
	 * 返回内容
	 */
	public static final String MSG_TAG = "msg";

	/**
	 * 数据对象
	 */
	public static final String DATA_TAG = "data";

	public static final String ERR_INFO = "errInfo";

	public static final String C_T = "ct";

	private final ResultErrInfo<T> errInfo = new ResultErrInfo<>();

	/**
	 * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
	 */
	public AjaxResult() {
	}

	/**
	 * 初始化一个新创建的 AjaxResult 对象
	 *
	 * @param code 状态码
	 * @param msg  返回内容
	 */
	public AjaxResult(int code, String msg) {
		super.put(CODE_TAG, code);
		super.put(MSG_TAG, msg);
		super.put(C_T, LocalDateUtils.toEpochSecond());
		super.put(ERR_INFO, errInfo);
	}

	/**
	 * 初始化一个新创建的 AjaxResult 对象
	 *
	 * @param code 状态码
	 * @param msg  返回内容
	 * @param data 数据对象
	 */
	public AjaxResult(int code, String msg, Object data,ResultErrInfo<Object> errInfo) {
		super.put(CODE_TAG, code);
		super.put(MSG_TAG, msg);
		super.put(C_T, LocalDateUtils.toEpochSecond());
		if (StringUtils.isNotNull(data)) {
			super.put(DATA_TAG, data);
		}
		super.put(ERR_INFO, errInfo);

	}

	public AjaxResult(int code, String msg, Object data) {
		super.put(CODE_TAG, code);
		super.put(MSG_TAG, msg);
		super.put(C_T, LocalDateUtils.toEpochSecond());
		if (StringUtils.isNotNull(data)) {
			super.put(DATA_TAG, data);
		}
		super.put(ERR_INFO, errInfo);

	}

	public AjaxResult(ServiceException exception) {
		super.put(CODE_TAG, HttpStatus.ERROR);
		super.put(MSG_TAG, "错误");
		super.put(C_T, LocalDateUtils.toEpochSecond());
		super.put(ERR_INFO, null != exception.getErrInfo() ? exception.getErrInfo() : errInfo);
	}

	/**
	 * 返回成功消息
	 *
	 * @return 成功消息
	 */
	public static AjaxResult<?> success() {
		return AjaxResult.success("操作成功");
	}

	/**
	 * 返回成功数据
	 *
	 * @return 成功消息
	 */
	public static AjaxResult<?> success(Object data) {
		return AjaxResult.success("操作成功", data);
	}

	/**
	 * 返回成功消息
	 *
	 * @param msg 返回内容
	 * @return 成功消息
	 */
	public static AjaxResult success(String msg) {
		return AjaxResult.success(msg, null);
	}

	/**
	 * 返回成功消息
	 *
	 * @param msg  返回内容
	 * @param data 数据对象
	 * @return 成功消息
	 */
	public static AjaxResult success(String msg, Object data) {
		return new AjaxResult(HttpStatus.SUCCESS, msg, data);
	}

	/**
	 * 返回错误消息
	 *
	 * @return
	 */
	public static AjaxResult error() {
		return AjaxResult.error("操作失败");
	}

	/**
	 * 返回错误消息
	 *
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static AjaxResult error(String msg) {
		return AjaxResult.error(msg, null);
	}

	public static AjaxResult error(ServiceException exception) {
		return new AjaxResult(exception);
	}

	/**
	 * 返回错误消息
	 *
	 * @param msg  返回内容
	 * @param data 数据对象
	 * @return 警告消息
	 */
	public static AjaxResult error(String msg, Object data) {
		return new AjaxResult(HttpStatus.ERROR, msg, data);
	}

	public static AjaxResult error(int code ,String msg) {
		return new AjaxResult(code, msg, null);
	}

	/**
	 * 返回错误消息
	 *
	 * @param code 状态码
	 * @param msg  返回内容
	 * @return 警告消息
	 */
	public static AjaxResult error(int code, String msg,ResultErrInfo<Object> errInfo) {
		return new AjaxResult(code, msg, null,errInfo);
	}
}
