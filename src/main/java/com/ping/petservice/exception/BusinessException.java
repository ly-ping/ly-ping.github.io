package com.ping.petservice.exception;


//枚举类
public class BusinessException extends RuntimeException{

    private BusinessExceptionCode code;


    public BusinessException(BusinessExceptionCode code) {
        super(code.getDesc());
        this.code = code;
    }

    public BusinessExceptionCode getCode() {
        return code;
    }

    public void setCode(BusinessExceptionCode code) {
        this.code = code;
    }

//    不写入堆栈，提高性能
    @Override
    public Throwable fillInStackTrace(){
        return this;
    }

}
