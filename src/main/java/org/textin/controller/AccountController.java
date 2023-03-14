package org.textin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.textin.constant.Constant;
import org.textin.model.result.ResultModel;
import org.textin.service.AccountService;
import org.textin.util.ResultModelUtil;
import org.textin.util.TextHttpUtil;
import org.textin.util.ToWavUtil;
import ws.schild.jave.EncoderException;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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

        if(files==null){
            return ResultModelUtil.fail(40002,"图片上传失败");
        }
        if(files.size()>10){
            ResultModelUtil.fail(401,"所选图片过多");
        }

        List<JSONObject> result = new ArrayList<>();
        for (MultipartFile file : files) {
            byte[] imgBytes = file.getBytes();
            String base64Image=JSON.parseObject(JSON.parseObject(TextHttpUtil.postUrl(Constant.CLEAR_URI, imgBytes))
                    .getString("result")).getString("image");
            imgBytes = Base64.getDecoder().decode(base64Image);
            if (accountService.typeServer(imgBytes).equals("train_ticket")){
                result.add(accountService.KeepAccountByImageTicket(imgBytes));
            }else if(accountService.typeServer(imgBytes).equals("shipping_invoice")){
                result.add(accountService.keepAccountByImageBill(imgBytes));
            }else if(accountService.typeServer(imgBytes).equals("taxi_ticket")){
                result.add(accountService.keepAccountByImageTaxi(imgBytes));
            } else {
                result.add(accountService.KeepAccountByImageShop(imgBytes));
            }
        }
        return ResultModelUtil.success(result);
    }

    @PostMapping("wav")
    public ResultModel<JSONObject> upLoadWAV(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException, EncoderException {
        byte[] bytes=file.getBytes();
        return accountService.keepAccountByWAV(ToWavUtil.convertToWAVAndToBytes(bytes));
    }
}
