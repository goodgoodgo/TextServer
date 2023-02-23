package org.textin.util;

import lombok.extern.slf4j.Slf4j;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @program: TextServer
 * @description:
 * 这段代码实现了一个ExecutorFactory，用于创建线程池。其中的方法getExecutorService用于返回一个固定大小的线程池，它接受两个参数：
 * cls表示类的Class对象，用于指定线程池名称；fixedThreads表示线程池中固定的线程数量。该方法内部使用了ThreadPoolExecutor来创建线程池，并指定了以下参数：
 * corePoolSize和maximumPoolSize都被设置为fixedThreads，表示线程池中的线程数量固定不变；
 * keepAliveTime被设置为0，表示线程池中的线程不需要等待空闲时间就会被销毁；
 * 使用ArrayBlockingQueue作为阻塞队列，容量为512，表示最多可以缓存512个任务；
 * 使用ThreadFactoryBuilder创建线程工厂，用于创建线程，并设置线程名、异常处理器和是否为守护线程；
 * 使用AbortPolicy作为线程池的饱和策略，表示当任务队列已满时直接抛出异常。
 * 此外，该类还提供了一个getCommonHandler方法，返回一个通用的未捕获异常处理器，用于处理线程中未捕获的异常。当线程发生未捕获的异常时，该处理器会记录线程名和异常信息。
 * @author: ma
 * @create: 2023-02-22 16:15
 */
@Slf4j
public class ExecutorFactory {

    public static ExecutorService getExecutorService(Class<?> cls,int fixedThreads){
        return new ThreadPoolExecutor(fixedThreads, fixedThreads, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(512),
                new ThreadFactoryBuilder()
                        .setNameFormat(cls.getSimpleName()+ "-%d")//设置线程名classname-1
                        .setUncaughtExceptionHandler(ExecutorFactory.getCommonHandler())//设置异常处理器
                        .setDaemon(false)//是否为守护线程；
                        .build(),
                new ThreadPoolExecutor.AbortPolicy());//使用AbortPolicy作为线程池的饱和策略，表示当任务队列已满时直接抛出异常。
    }

    public static Thread.UncaughtExceptionHandler getCommonHandler() {
        return (t, e) -> {
            log.error("GroupName:[{}], ThreadName:[{}]. "
                    , t.getThreadGroup().getName()
                    , t.getName());
            if(e!=null){
                log.error("Cause[{}],Message[{}]"
                        , e.getCause()
                        , e.getMessage()
                        , e);
            }
        };
    }
}
