package org.textin.service;

import org.textin.model.entity.Expenditure;
import org.textin.model.result.ResultModel;


public interface ExpenditureService {
    ResultModel<String> save(Expenditure expenditure);
    ResultModel<String> delete(Long id);
    ResultModel<String> update(Expenditure expenditure);
}
