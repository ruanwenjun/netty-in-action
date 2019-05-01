package nia.chapter1.bio;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author weruan
 * @date 2019-04-29
 */
public class ClientThreadFactory implements ThreadFactory {
    private ThreadGroup threadGroup = new ThreadGroup("ClientThreadGroup");
    private AtomicInteger threadNum = new AtomicInteger(1);





    @Override
    public Thread newThread(Runnable r) {
        return new Thread(threadGroup, r, "thread-" + threadNum.getAndIncrement(), 0);
    }
}
