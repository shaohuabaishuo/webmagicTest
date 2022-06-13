package netty.future;

import com.sun.xml.internal.ws.util.CompletedFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FutureTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 100;
            }
        });

        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 1;
            }
        });

        completableFuture.whenComplete(new BiConsumer<Integer, Throwable>() {
            @Override
            public void accept(Integer integer, Throwable throwable) {
                System.out.println(integer);
            }
        }).exceptionally(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) {
                System.out.println(throwable.getMessage());
                return -1;
            }
        });


        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop next = group.next();
        io.netty.util.concurrent.Future<Integer> submit = next.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });

//        submit.get();
        submit.addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Integer>>() {
            @Override
            public void operationComplete(io.netty.util.concurrent.Future<? super Integer> future) throws Exception {
                System.out.println("结果哦" + future.getNow());
            }
        });

        DefaultPromise<Integer> promise = new DefaultPromise<>(group.next());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("开始处理");
                    Thread.sleep(1000);
                    int i = 1 / 0;
                    promise.setSuccess(1);
                } catch (Exception e) {
                    promise.setFailure(e);
                }

            }
        }).start();

        System.out.println("等待结果");
        try {
            System.out.println("结果" + promise.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
