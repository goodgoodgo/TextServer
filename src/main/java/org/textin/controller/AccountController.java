package org.textin.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.textin.model.result.ResultModel;
import org.textin.service.AccountService;
import org.textin.service.ExpenditureService;
import org.textin.util.ResultModelUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-10 15:39
 */
@RestController
@RequestMapping("/keepAccounts")
public class AccountController {
    @Resource
    private AccountService accountService;

    @PostMapping("/image")
    public ResultModel<List<JSONObject>> upLoadImg(@RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        if(files.size()>10){
            ResultModelUtil.fail(401,"所选图片过多");
        }
        List<JSONObject> result = new ArrayList<>();
        for (MultipartFile file : files) {
            byte[] imgBytes = file.getBytes();
            if (accountService.typeServer(imgBytes).contains("ticket")){
                result.add(accountService.KeepAccountByImageTicket(imgBytes));
            }else {
                result.add(accountService.KeepAccountByImageShop(imgBytes));
            }
        }
        return ResultModelUtil.success(result);
    }
}
