package org.textin;

import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.textin.util.ChineseNumberConverterUtil;
import org.textin.util.RecognitionUtil;
import org.textin.util.ResultModelUtil;
import org.textin.util.SocketUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-12 12:21
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String text="今天中午吃饭花了三块二毛八";
        List<Term> termList = HanLP.segment(text);
        termList.forEach(System.out::println);
    }
}
