package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class BaseSelectorClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
            if (!connect) {
                while (!socketChannel.finishConnect()) {
                    System.out.println("链接失败，但是客户端不阻塞!");
                }
            }
            socketChannel.write(ByteBuffer.wrap("hello world".getBytes()));
            System.in.read();
        } catch (IOException e) {

        }

    }
}
