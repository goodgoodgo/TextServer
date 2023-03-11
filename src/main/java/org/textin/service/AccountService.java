package org.textin.service;

import com.alibaba.fastjson.JSONObject;

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
     * 识别小票种类
     * @param imgBytes
     * @return
     */
    String typeServer(byte[] imgBytes);
}
