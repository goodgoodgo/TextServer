package org.textin.service;

import org.textin.model.result.ResultModel;

import java.util.HashMap;


public interface ExpenditureService {


    /**
     * 识别商户小票
     * @param imgBytes
     * @return
     */
    HashMap<String,String> KeepAccountByImageShop(byte[] imgBytes);
    /**
     * 识别火车票
     * @param imgBytes
     * @return
     */
    HashMap<String,String> KeepAccountByImageTicket(byte[] imgBytes);
    /**
     * 识别小票种类
     * @param imgBytes
     * @return
     */
    String typeServer(byte[] imgBytes);

}
