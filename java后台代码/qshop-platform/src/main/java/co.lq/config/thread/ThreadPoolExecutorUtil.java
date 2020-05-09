package co.lq.config.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import co.lq.utils.SpringContextHolder;

/**
 * 用于获取自定义线程池
 *
 * @author billy
 * @date 2019年10月31日18:16:47
 */
public class ThreadPoolExecutorUtil {

    public static ThreadPoolExecutor getPoll() {
        AsyncTaskProperties properties = SpringContextHolder.getBean(AsyncTaskProperties.class);
        return new ThreadPoolExecutor(properties.getCorePoolSize(), properties.getMaxPoolSize(),
                properties.getKeepAliveSeconds(), TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(properties.getQueueCapacity()), new TheadFactoryName());
    }
}
