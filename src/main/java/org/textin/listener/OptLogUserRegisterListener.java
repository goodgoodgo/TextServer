package org.textin.listener;

import org.springframework.stereotype.Component;
import org.textin.model.entity.OptLog;
import org.textin.model.entity.User;
import org.textin.service.OptLogService;
import org.textin.util.EventBus;

import javax.annotation.Resource;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-27 14:51
 */
@Component
public class OptLogUserRegisterListener extends EventBus.EventHandler<User> {

    @Resource
    private OptLogService optLogService;

    @Override
    public EventBus.MsgType msgType() {
        return EventBus.MsgType.USER_REGISTER;
    }

    @Override
    public void onMessage(User user) {
        optLogService.save(OptLog.createUserRegisterRecordLog(user.getId(), user));
    }
}
