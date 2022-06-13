package netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
public class MultiThreadServer {

    public static void main(String[] args) {

        try {
            Thread.currentThread().setName("boos");
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            Selector boosSelector = Selector.open();

            serverSocketChannel.bind(new InetSocketAddress(9999));
            //非阻塞
            serverSocketChannel.configureBlocking(false);
            //将通道注册到selector并设置监听事件类型
            serverSocketChannel.register(boosSelector, SelectionKey.OP_ACCEPT);

            Worker worker = new Worker("worker-" + 1);


            while (true) {
                boosSelector.select();
                Set<SelectionKey> selectionKeys = boosSelector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        log.info("街道连接消息:{}", socketChannel.getRemoteAddress());
                        worker.register(socketChannel);
                        log.info("连接之后:{}", socketChannel.getRemoteAddress());
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static class Worker implements Runnable {
        private Thread thread;
        private Selector workerSelector;
        private String name;
        private volatile boolean start = false;
        private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel socketChannel) throws IOException {
            if (!start) {
                workerSelector = Selector.open();
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }
            queue.add(() -> {
                try {
                    socketChannel.register(workerSelector, SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            workerSelector.wakeup();
        }

        @Override
        public void run() {
            while (true) {
                try {
//                    System.out.println(123);
                    workerSelector.selectNow();
                    Runnable poll = queue.poll();
                    if(poll!=null){
                        poll.run();
                    }

                    Iterator<SelectionKey> iterator = workerSelector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(100);
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            socketChannel.read(buffer);
                            buffer.flip();
                            System.out.println("work读取" + String.valueOf(buffer.get()));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
