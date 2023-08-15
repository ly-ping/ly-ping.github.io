package com.ping.petservice.resp;

/**
 * <p>
 *  通用返回类
 * </p>
 *
 * @author ping
 * @since 2023-03-29
 */

public class CommonResp<T> {

//    业务上的成功与失败
    private Boolean success = true;

//    返回信息
    private String message;

//     返回范型数据 自定义类型
    private T content;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommonResp{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
}