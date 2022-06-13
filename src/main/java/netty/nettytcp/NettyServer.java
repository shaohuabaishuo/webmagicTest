package netty.nettytcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) {
//        System.out.println(Runtime.getRuntime().availableProcessors());
        server();
    }


    public static void server() {
        //boosGroup只处理链接请求-线程组，里面包含了很多线程
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        //workGroup处理客户端的业务逻辑-线程组，里面包含了很多线程0 = {NioEventLoop@1794}
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boosGroup, workerGroup)//设置两个线程组 线程+选择器不断循环找事件
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到的链接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动的链接状态
                    //.handler(null)//该handler对应的是boosGroup，下面的childHandler对应的是workerGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            System.out.println(boosGroup.hashCode()+"--"+workerGroup.hashCode());
                            System.out.println("客户端的socketChannel hashcode=" + ch.hashCode());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器已经启动");
            //设置占用的端口号
            ChannelFuture channelFuture = bootstrap.bind(9999).sync();
            //注册监听器监听关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("监听端口9999成功");
                    }
                }
            });
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
