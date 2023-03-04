package org.textin.service.Impl;

import org.springframework.stereotype.Service;
import org.textin.dao.OptLogDAO;
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

        optLog.initBase();

        optLogDAO.insert(optLog);
    }
}
