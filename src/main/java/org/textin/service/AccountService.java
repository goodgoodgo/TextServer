package org.textin.service;

import org.springframework.stereotype.Service;
import org.textin.model.result.ResultModel;

import java.util.HashMap;

@Service
public interface AccountService {


    ResultModel<HashMap<String,String>> KeepAccountByImageShop(byte[] imgBytes);

    ResultModel<HashMap<String,String>> KeepAccountByImageTicket(byte[] imgBytes);

    String typeServer(byte[] imgBytes);

}
