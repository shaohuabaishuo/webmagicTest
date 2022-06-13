package netty.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import netty.websocket.WebSocketHandler;
import org.apache.commons.lang3.CharSet;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class  PipeLineTest {
    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //head->h1->h2->h5->h3->h4->h6->tail
                            pipeline.addLast("h1", new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf byteBuf = (ByteBuf) msg;
                                    String str = byteBuf.toString(Charset.defaultCharset());
                                    System.out.println("1");
                                    super.channelRead(ctx, str);
                                }
                            });
                            pipeline.addLast("h2", new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println("2");
                                    Map<String, String> map = new HashMap<>();
                                    map.put("str", msg.toString());
                                    super.channelRead(ctx, map);
                                }
                            });
                            pipeline.addLast("h5", new ChannelOutboundHandlerAdapter() {
                                @Override
                                public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                    System.out.println(5);
                                    super.write(ctx, msg, promise);
                                }
                            });
                            pipeline.addLast("h3", new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println("3" + msg + "---" + msg.getClass());
                                    ch.writeAndFlush(123);
                                }
                            });
                            pipeline.addLast("h4", new ChannelOutboundHandlerAdapter() {
                                @Override
                                public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                    System.out.println(4);
                                    super.write(ctx, msg, promise);
                                }
                            });

                            pipeline.addLast("h6", new ChannelOutboundHandlerAdapter() {
                                @Override
                                public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                    System.out.println(6);
                                    super.write(ctx, msg, promise);
                                }
                            });

                        }
                    });
            System.out.println("服务器启动");
            ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
