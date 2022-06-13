package netty.nettychat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyChatClient {

    private int port;
    private String ip;

    public NettyChatClient(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public void run() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        //12 连接建立 addLast
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder"
                                    , new StringDecoder()//15
                            );
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new IdleStateHandler(5000, 1, 5000, TimeUnit.SECONDS));
                            pipeline.addLast(new NettyChatClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(ip, port)
                    .sync();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                channelFuture.channel()//连接对象
                        .writeAndFlush(scanner.nextLine() + "\r\n");
            }
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new NettyChatClient(9999, "127.0.0.1").run();
    }
}
