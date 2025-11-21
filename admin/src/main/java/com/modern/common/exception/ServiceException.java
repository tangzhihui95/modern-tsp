package com.modern.common.exception;

import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.ResultErrInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.formula.functions.T;

/**
 * 业务异常
 *
 * @author piaomiao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public  class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * type
     */
    private Integer type = 0;

    /**
     * 错误码
     */
    private Integer code = 0;

    /**
     * 错误提示
     */
    private String message = "异常，请联系管理员";

    /**
     * 错误明细，内部调试错误
     *
     * 和 {@link CommonResult#getDetailMessage()} 一致的设计
     */
    private String detailMessage;


    private ResultErrInfo<Object> errInfo = new ResultErrInfo<>();



    /**
     * 空构造方法，避免反序列化问题
     * @param errorEnum
     * @param e
     */
    public ServiceException(ErrorEnum errorEnum, Exception e)
    {
    }
    public ServiceException(ErrorEnum errorEnum)
    {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMsg();
        this.type = errorEnum.getType();
        this.errInfo.setErrcode(1);
        this.errInfo.setErrmsg(this.message);
        this.errInfo.setHasInfo(false);
        this.errInfo.setInfo(null);
    }
    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message)
    {
        errInfo.setErrcode(1);
        errInfo.setErrmsg(message);
        errInfo.setHasInfo(false);
        errInfo.setInfo(null);
        this.message = message;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }
}
