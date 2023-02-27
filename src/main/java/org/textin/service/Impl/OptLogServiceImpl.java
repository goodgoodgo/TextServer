package org.textin.service.Impl;

import org.springframework.stereotype.Service;
import org.textin.dal.dao.OptLogDAO;
import org.textin.dal.dataobject.OptLogDO;
import org.textin.model.entity.OptLog;
import org.textin.service.OptLogService;

import javax.annotation.Resource;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 10:59
 */
@Service
public class OptLogServiceImpl implements OptLogService {

    @Resource
    private OptLogDAO optLogDAO;

    @Override
    public void save(OptLog optLog) {
        OptLogDO optLogDO = OptLogDO.builder()
                .content(optLog.getContent())
                .operatorId(optLog.getOperatorId())
                .type(optLog.getType().getValue())
                .build();
        optLogDO.initBase();

        optLogDAO.insert(optLogDO);
    }
}
