package com.modern.common.core;


import com.modern.common.exception.ServiceException;
import com.modern.common.utils.LocalDateUtils;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 前后端交互数据标准
 */
@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	private boolean success;

	/**
	 * 失败消息
	 */
	private String message;

	/**
	 * 返回代码
	 */
	private Integer code;

	/**
	 * 时间戳
	 */
	private LocalDateTime timestamp = LocalDateTime.now();

	/**
	 * 结果对象
	 */
	private T result;

	private ResultErrInfo<org.apache.poi.ss.formula.functions.T> errInfo = new ResultErrInfo<>();

	private Long ct;

	public Result() {
	}

	private Result(boolean success, Integer code, String message, T result) {
		this.success = success;
		this.code = code;
		this.message = message;
		this.result = result;
		this.ct = LocalDateUtils.toEpochSecond();
	}

	private Result(boolean success, Integer code, String message) {
		this(success, code, message, null);
	}

	public static Result success() {
		return new Result(true, 200, "success");
	}

	public static Result success(String message) {
		return new Result(true, 200, message);
	}

	public static Result success(int code, String message) {
		return new Result(true, code, message);
	}

	public static <T> Result<T> success(int code, String message, T data) {
		return new Result(true, code, message, data);
	}

	public static Result fail() {
		return new Result(false, 500, "fail");
	}

	public static <T> Result fail(ServiceException exception) {
		Result result = Result.fail();
		if (null != exception.getErrInfo()) {
			result.errInfo = exception.getErrInfo();
		}
		result.ct = LocalDateUtils.toEpochSecond();
		return result;
	}

	public static Result fail(String message) {
		return new Result(false, 500, message);
	}

	public static Result fail(int code, String message) {
		return new Result(false, code, message);
	}

	public static <M> Result fail(int code, String message, M result) {
		return new Result(false, code, message, result);
	}

	public static <T> Result<T> data(T data) {
		return new Result(true, 200, "success", data);
	}

	public static <T> Result data(ResultInterface ri) {
		ri.exec();
		return success();
	}

	public interface ResultInterface {
		void exec();
	}
}
