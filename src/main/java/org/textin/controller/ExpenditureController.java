package org.textin.controller;

import com.alibaba.fastjson2.JSON;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.textin.model.result.ResultModel;
import org.textin.service.ExpenditureService;
import org.textin.util.ResultModelUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
*@program: ServerTest
*@description:
*@author: ma
*@create: 2022-12-10 15:51
*/
@RestController
@RequestMapping("/keepAccounts")
public class ExpenditureController {

    @Resource
    private ExpenditureService accountService;

    @PostMapping("/image")
    public ResultModel<String> upLoadImg(@RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        List<HashMap<String, String>> data = new ArrayList<>();

        for (MultipartFile file : files) {
            byte[] imgBytes = file.getBytes();
            if (accountService.typeServer(imgBytes).contains("ticket")){
                data.add(accountService.KeepAccountByImageTicket(imgBytes));
            }else {
                data.add(accountService.KeepAccountByImageShop(imgBytes));
            }
        }

        return ResultModelUtil.success(JSON.toJSONString(data));
    }
}
