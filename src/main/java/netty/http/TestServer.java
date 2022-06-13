package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

    public static void main(String[] args) {
        //boosGroup只处理链接请求-线程组，里面包含了很多线程
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        //workGroup处理客户端的业务逻辑-线程组，里面包含了很多线程
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boosGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器通道实现
                    .childHandler(new TestServerInitializer());
            System.out.println("服务器已经启动");
            //设置占用的端口号
            ChannelFuture channelFuture = bootstrap.bind(8080);
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
