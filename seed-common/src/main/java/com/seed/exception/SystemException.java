package com.seed.exception;

import com.seed.enums.AppHttpCodeEnum;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 17:44
 */
public class SystemException extends RuntimeException{
    private int code;
    private String msg;
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
