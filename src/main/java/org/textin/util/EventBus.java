package org.textin.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-22 15:58
 */
@Slf4j
public class EventBus {

    public enum MsgType{
        /**
         * 消息类型枚举
         */
        USER_LOGIN,
        USER_REGISTER,
        USER_LOGOUT,
        INCOME_CREATE,
        INCOME_UPDATE,
        EXPENDITURE_CREATE,
        EXPENDITURE_UPDATE,
        LEDGER_CREATE,
        LEDGER_UPDATE,
        USERINFO_CREATE,
        USERINFO_UPDATE
    }

    /**
     * 事件/事件处理 映射
     */
    public static final Map<MsgType,List<EventBus.EventHandler>> EVENT_HANDLER_MAP=new ConcurrentHashMap<>();

    /**
     * 从ExecutorFactory中获取线程池
     */
    private final static ExecutorService executorService = ExecutorFactory.getExecutorService(EventBus.class,10);


    /**
     * 在spring容器销毁之前执行，关闭线程池
     */
    @PreDestroy
    public void post(){
        executorService.shutdown();
    }

    /**
     *  触发事件，默认异步执行
     * @param eventEn
     * @param message
     */
    public static void emit(final MsgType eventEn, final Object message) {
        processEmitEvent(eventEn, message, true);
    }

    /**
     * 同步触发事件
     * @param eventEn
     * @param message
     */
    public static void emitSync(final MsgType eventEn, final Object message) {
        processEmitEvent(eventEn, message, false);
    }

    /**
     * 将某个事件发布给所有注册的事件处理器,发布并处理事件
     * @param eventEn
     * @param message
     * @param async
     */
    private static void processEmitEvent(MsgType eventEn ,Object message ,Boolean async){
        log.info("msgTypeEn[{}],message[{}]",eventEn,message);
        List<EventBus.EventHandler> eventHandlers=EVENT_HANDLER_MAP.get(eventEn);
        if (null==eventHandlers){
            log.warn("emit [{}] event, but not handler.", eventEn);
            return ;
        }
        eventHandlers.forEach(eventHandler -> {
            if(async){
                try {
                    executorService.submit(()->{
                        eventHandler.onMessage(message);
                    });
                } catch (Exception e) {
                    log.error("handler [{}] async process event [{}] error",eventHandler.getClass(),eventEn);
                }
            }else {
                try {
                    eventHandler.onMessage(message);
                } catch (Exception e) {
                    log.error("handler [{}] sync process event [{}] error",eventHandler.getClass(),eventEn);
                }
            }
        });
    }

    /**
     * 事件和对应处理器绑定，处理器数据集合初始化
     * @param eventEn
     * @param eventHandler
     */
    private static synchronized void on(MsgType eventEn,EventBus.EventHandler eventHandler){
        List<EventBus.EventHandler> eventHandlerList=EVENT_HANDLER_MAP.get(eventEn);
        if(null==eventHandlerList){
            eventHandlerList=new ArrayList<>();
        }
        eventHandlerList.add(eventHandler);
        EVENT_HANDLER_MAP.put(eventEn,eventHandlerList);
    }

    /**
     * 子类需要实现这两个方法，以便让事件总线(EventBus)能够正确地分发和处理消息。
     * @param <T>
     */
    public static abstract class EventHandler<T> implements InitializingBean{
        public abstract MsgType msgType();
        public abstract void onMessage(T message);

        @Override
        public void afterPropertiesSet(){
            EventBus.on(msgType(),this);
        }
    }
}
