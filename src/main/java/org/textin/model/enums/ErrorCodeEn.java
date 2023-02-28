package org.textin.model.enums;


public enum ErrorCodeEn {

    SUCCESS(0,"成功"),
    SYSTEM_ERROR(9999,"系统异常"),
    PARAM_CHECK_ERROR(9998,"请求路径为空"),
    COMMON_CACHE_KEY_EMPTY(9997,"缓存值key不为空"),
    EMAIL_NOT_VAlID(9996,"邮箱格式不合规");

    private Integer code;
    private String message;
    ErrorCodeEn(Integer code,String message){
        this.code=4000_000+code;
        this.message=message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
