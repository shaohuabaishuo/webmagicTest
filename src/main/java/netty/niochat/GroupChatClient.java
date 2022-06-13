package netty.niochat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import static java.lang.Thread.sleep;

public class GroupChatClient {

    private static final String server_ip = "127.0.0.1";
    private static final int server_port = 9999;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;


    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(server_ip, server_port));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString();
            System.out.println(userName + "客户端初始化完成!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(String message) {
        message = userName + "说:" + message;
        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {

        }
    }

    private void receiveMessage() {
        try {
            int select = selector.selectNow();
            if (select == 0) {
//                System.out.println("没有可用通道!");
                return;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys) {
                if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    System.out.println("接收到服务端消息:" + new String(buffer.array()));
                    selectionKeys.remove(selectionKey);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();

        new Thread(() -> {
            while (true) {
                groupChatClient.receiveMessage();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();
            groupChatClient.sendMessage(str);
        }
    }
}
