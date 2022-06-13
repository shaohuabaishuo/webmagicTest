package netty.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BaseChannel {

    public static void main(String[] args) {
//        FileChannelWrite("hello word");
//        FileChannelRead();
        FileChannelWriteAndRead("hello world");
    }


    public static void FileChannelWrite(String str) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("d:\\file.txt");
            FileChannel channel = fileOutputStream.getChannel();
            channel.write(ByteBuffer.wrap(str.getBytes()));
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void FileChannelRead() {
        try {
            FileInputStream fileInputStream = new FileInputStream("d:\\file.txt");
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.MAX_VALUE);
            channel.read(byteBuffer);
            System.out.println(new String(byteBuffer.array()));
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void FileChannelWriteAndRead(String str) {
        try {
            FileChannelWrite(str);

            FileInputStream fileInputStream = new FileInputStream("d:\\file.txt");
            FileChannel channel01 = fileInputStream.getChannel();

            FileOutputStream fileOutputStream = new FileOutputStream("d:\\file-1.txt");
            FileChannel channel02 = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(512);

            while (true) {
                byteBuffer.clear();
                int read = channel01.read(byteBuffer);
                System.out.println(read);
                if (read == -1) {
                    break;
                }
                byteBuffer.flip();
                channel02.write(byteBuffer);
            }
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
