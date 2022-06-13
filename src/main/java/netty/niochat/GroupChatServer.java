package netty.niochat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class GroupChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private static final int port = 9999;

    public GroupChatServer() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect_listen() {
        System.out.println("监听线程:" + Thread.currentThread().getName());
        try {
            while (true) {
                int select = selector.select();
                if (select == 0) {
//                    System.out.println("等待客户端链接!");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        SelectionKey register = socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(socketChannel.getRemoteAddress() + "已上线");
                    }
                    if (selectionKey.isReadable()) {
                        readMessage(selectionKey);
                    }
                     selectionKeys.remove(selectionKey);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }


    private void readMessage(SelectionKey selectionKey) {
        SocketChannel readChannel = (SocketChannel) selectionKey.channel();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = readChannel.read(buffer);
            if (read > 0) {
                String message = new String(buffer.array());
                System.out.println("收到" + readChannel.getRemoteAddress() + "消息:" + message);
                relayMessage(message, readChannel);
            }

        } catch (IOException e) {
            try {
                System.out.println(readChannel.getRemoteAddress() + "掉线了");
                selectionKey.cancel();
                readChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void relayMessage(String message, SocketChannel selfSocketChannel) {
        try {
            System.out.println("服务器开始转发消息" + Thread.currentThread().getName());
            for (SelectionKey selectionKey : selector.keys()) {
                Channel channel = selectionKey.channel();
                if (channel instanceof SocketChannel && !channel.equals(selfSocketChannel)) {
                    SocketChannel socketChannel = (SocketChannel) channel;
                    socketChannel.write(ByteBuffer.wrap(message.getBytes()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GroupChatServer().connect_listen();
    }

}
