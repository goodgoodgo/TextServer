package org.textin;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.textin.dao.*;
import org.textin.service.BudgetService;
import org.textin.util.*;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:25
 */
@SpringBootTest
public class STest {

    @Resource
    private IncomeDAO incomeDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private ExpenditureDAO expenditureDAO;
    @Resource
    private BudgetService budgetService;
    @Resource
    private BudgetDAO budgetDAO;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    private LedgerDAO ledgerDAO;

    @Test
    void testString() throws FileNotFoundException {
    }

    public static void main(String[] args) throws TencentCloudSDKException, IOException {
        String audioPath = "src/main/resources/m4a/4.wav";
        byte[] audioBytes = Files.readAllBytes(Paths.get(audioPath));
        System.out.printf( RecognitionUtil.speechRecognition(audioBytes));
    }
}