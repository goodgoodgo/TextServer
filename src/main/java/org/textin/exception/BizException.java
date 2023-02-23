package org.textin.exception;

import lombok.Data;
import org.textin.model.enums.ErrorCodeEn;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-23 14:13
 */
@Data
public class BizException extends RuntimeException{
    private Integer code;
    private String message;

    /**
     * 指定为系统异常
     */
    public BizException(){
        this(ErrorCodeEn.SYSTEM_ERROR);
    }

    /**
     * 带参数业务异常
     * @param errorCodeEn
     */
    public BizException(ErrorCodeEn errorCodeEn){
        this(errorCodeEn.getCode(),errorCodeEn.getMessage());
    }

    /**
     * 带参数业务异常
     * @param code
     * @param message
     */
    public BizException(Integer code,String  message){
        super(message);
        this.code=code;
        this.message=message;
    }
}
