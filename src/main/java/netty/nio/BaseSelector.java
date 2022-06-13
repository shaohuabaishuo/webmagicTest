package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class BaseSelector {

    public static void main(String[] args) {
        nio_server();
    }


    private static void nio_server() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            Selector selector = Selector.open();

            serverSocketChannel.bind(new InetSocketAddress(9999));
            //非阻塞
            serverSocketChannel.configureBlocking(false);
            //将通道注册到selector并设置监听事件类型
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("注册后selectionKey的数量:" +serverSocketChannel.hashCode()+ selector.keys().size()+"---"+Thread.currentThread().getName());
            while (true) {
                int select = selector.select(1000);
                if (select == 0) {
//                    System.out.println("服务器等待了1s，无连接!");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("有事件发生的selectKey数量:"+selectionKeys.size());
                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        System.out.println("客户端链接成功:" + socketChannel.hashCode()+"---"+Thread.currentThread().getName());
                        System.out.println("客户端连接后注册的selectionKey数量:" + selector.keys().size()+"---"+Thread.currentThread().getName());
                        System.out.println("--------------------------------------------------");
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel readChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        readChannel.read(buffer);
                        System.out.println("from 客户端:" + new String(buffer.array()));
                        System.out.println("当前线程"+Thread.currentThread().getName());
                        System.out.println("当前channel:"+readChannel.hashCode());
                        System.out.println("--------------------------------------------------");
                    }
//                    selectionKeys.remove(selectionKey);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
