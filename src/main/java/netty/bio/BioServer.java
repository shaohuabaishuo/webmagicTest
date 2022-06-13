package netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

    public static void main(String[] args) {
//        BioMethod();
        BioMethod_thread();
    }


    public static void BioMethod(){
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("服务端启动");
            Socket socket = serverSocket.accept();
            System.out.println("客户端链接");
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("收到客户端消息:" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void BioMethod_thread(){
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("服务端启动");
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            while(true){
                System.out.println("等待链接");
                Socket socket = serverSocket.accept();
                System.out.println("客户端链接");
                printThread();
                System.out.println("------------------------------");
                executorService.execute(() -> {
                    try{
                        InputStream inputStream = socket.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String msg;
                        while ((msg = bufferedReader.readLine()) != null) {
                            System.out.println("收到客户端消息:" + msg+" "+Thread.currentThread().getId()+Thread.currentThread().getName());

                        }
                    }catch (IOException e){

                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  static void printThread(){
        ThreadGroup threadGroup=Thread.currentThread().getThreadGroup();
        int count = Thread.activeCount();
        Thread[] threads=new Thread[count];
        threadGroup.enumerate(threads);
        for (Thread thread : threads) {
            System.out.println("线程"+thread.getId()+" "+thread.getName());
        }
    }

}
