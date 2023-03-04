package org.textin.util;

import org.textin.dao.ExpenditureDAO;
import org.textin.model.entity.Expenditure;
import org.textin.model.entity.Income;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-03 14:42
 */
public class TransferUtil {
    public static List<Income> toIncome(List<Expenditure> expenditures){
        List<Income> incomes=new ArrayList<>();
        expenditures.forEach(expenditure -> {
            Income income=Income.builder()
                    .amount(expenditure.getAmount())
                    .comment(expenditure.getComment())
                    .creatorId(expenditure.getCreatorId())
                    .ledgerId(expenditure.getLedgerId())
                    .subcategory(expenditure.getSubcategory())
                    .category(expenditure.getCategory())
                    .IncomeDate(expenditure.getExpenditureDate())
                    .build();
            income.setCreateAt(expenditure.getCreateAt());
            income.setId(expenditure.getId());
            income.setUpdateAt(expenditure.getUpdateAt());
            incomes.add(income);
        });
        return incomes;
    }
}
