package org.textin.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.textin.service.AccountService;

import javax.annotation.Resource;
import java.io.IOException;

/**
*@program: ServerTest
*@description:
*@author: ma
*@create: 2022-12-10 15:51
*/
@RestController
@RequestMapping("/keepAccounts")
public class AccountsController {

    @Resource
    private AccountService accountService;

    @PostMapping("/image")
    public String upLoadImg(@RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {

        byte[] imgBytes = multipartFile.getBytes();
        if (accountService.typeServer(imgBytes).contains("ticket")){
            return JSON.toJSONString(accountService.KeepAccountByImageTicket(imgBytes));
        }else {
            return JSON.toJSONString(accountService.KeepAccountByImageShop(imgBytes));
        }
    }
}
