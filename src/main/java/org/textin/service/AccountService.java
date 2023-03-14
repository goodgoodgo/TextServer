package org.textin.service;

import com.alibaba.fastjson.JSONObject;
import org.textin.model.result.ResultModel;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-10 15:38
 */
public interface AccountService {
    /**
     * 识别商户小票
     *
     * @param imgBytes
     * @return
     */
    JSONObject KeepAccountByImageShop(byte[] imgBytes);
    /**
     * 识别火车票
     * @param imgBytes
     * @return
     */
    JSONObject KeepAccountByImageTicket(byte[] imgBytes);

    /**
     * 通用票据识别
     * @param imgBytes
     * @return
     */
    JSONObject keepAccountByImageBill(byte[] imgBytes);

    JSONObject keepAccountByImageTaxi(byte[] imgBytes);

    /**
     * 识别小票种类
     * @param imgBytes
     * @return
     */
    String typeServer(byte[] imgBytes);

    /**
     * wav语音识别
     * @param bytes
     * @return
     */
    ResultModel<JSONObject> keepAccountByWAV(byte[] bytes);
}
