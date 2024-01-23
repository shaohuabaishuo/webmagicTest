package thread;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.*;

public class ThreadPoolTest {

    ThreadFactory threadFactory = new ThreadFactory(){
        @Override
        public Thread newThread(Runnable r) {
            Thread t=new Thread();
            t.setName("xxx");
            return t;
        }
    };


    ExecutorService executorService = new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), threadFactory,new ThreadPoolExecutor.AbortPolicy());




    private void test() {
        for (int i = 0; i < 10; i++) {
            executorService.execute(()->{
                System.out.println("12");
            });
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.execute(()->{
            System.out.println("12");
        });
    }

    public static void main(String[] args) {
        ThreadPoolTest threadPoolTest = new ThreadPoolTest();
        threadPoolTest.test();
        System.out.println(Integer.SIZE);
    }
}
