package org.textin.util;

import org.textin.model.result.ResultModel;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2022-12-23 14:32
 */
public class ResultModelUtil {

    public static ResultModel success() {
        return new ResultModel();
    }

    public static <T> ResultModel<T> success(T data) {
        ResultModel<T> resultModel = new ResultModel<T>();
        resultModel.setData(data);

        return resultModel;
    }

    public static ResultModel fail(Integer code, String message) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(code);
        resultModel.setMessage(message);
        resultModel.setSuccess(Boolean.FALSE);

        return resultModel;
    }

}
