package netty.nettytcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程:" + Thread.currentThread().getName());
        System.out.println("server ctx:" + ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送消息:" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:" + ctx.channel().remoteAddress());

        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();

        /*Thread.sleep(10 * 1000);
        ctx.writeAndFlush(Unpooled.copiedBuffer("业务逻辑处理完成", CharsetUtil.UTF_8));
        */

        //taskQueue
        channel.eventLoop().execute(() -> {
            try {
                Thread.sleep(2 * 1000);
            } catch (Exception e) {
                System.out.println("发生异常：" + e.getMessage());
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("业务逻辑处理完成" + Thread.currentThread().getName(), CharsetUtil.UTF_8));
        });

        channel.eventLoop().execute(() -> {
            try {
                Thread.sleep(2 * 1000);
            } catch (Exception e) {
                System.out.println("发生异常：" + e.getMessage());
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("业务2逻辑处理完成" + Thread.currentThread().getName(), CharsetUtil.UTF_8));
        });

        channel.eventLoop().schedule(() -> {
            ctx.writeAndFlush(Unpooled.copiedBuffer("业务3逻辑处理完成" + Thread.currentThread().getName(), CharsetUtil.UTF_8));
        }, 5, TimeUnit.SECONDS);

        System.out.println("业务逻辑处理完成");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("读取完毕发送消息", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
