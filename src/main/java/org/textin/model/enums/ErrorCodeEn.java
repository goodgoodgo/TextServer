package org.textin.model.enums;


public enum ErrorCodeEn {

    SUCCESS(0,"成功"),
    SYSTEM_ERROR(9999,"系统异常");

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
