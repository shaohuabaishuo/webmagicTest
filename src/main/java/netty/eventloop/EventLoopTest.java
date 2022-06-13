package netty.eventloop;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class EventLoopTest {

    public static void main(String[] args) {
        //io事件，普通任务，定时任务
        EventLoopGroup group=new NioEventLoopGroup(2);

        //获取下一个事件循环对象
        for (int i = 0; i < 5; i++) {
            System.out.println(group.next());
        }

        //执行普通任务
        group.next().submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("嘎嘎");
        });

        group.next().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info("哈哈");
            }
        },0,1,TimeUnit.SECONDS);

        log.info("main");

        //普通任务，定时任务
        EventLoopGroup defaultEventLoopGroup = new DefaultEventLoopGroup();

    }
}
