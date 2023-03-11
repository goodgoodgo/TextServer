package org.textin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.textin.dao.BudgetDAO;
import org.textin.dao.ExpenditureDAO;
import org.textin.dao.IncomeDAO;
import org.textin.dao.UserDAO;
import org.textin.model.entity.Budget;
import org.textin.model.entity.Expenditure;
import org.textin.model.entity.Income;
import org.textin.model.vo.*;
import org.textin.service.BudgetService;
import org.textin.util.ResultModelUtil;
import org.textin.util.SocketUtil;
import org.textin.util.TextHttpUtil;
import org.textin.util.TransferUtil;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


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
    @Test
    void testString() throws FileNotFoundException {
        System.out.println(budgetDAO.findByIdAndBudgetDate(1l,"2022-01%"));
    }

    public static void main(String[] args) throws IOException {

    }
}