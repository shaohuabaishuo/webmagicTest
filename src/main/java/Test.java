import java.util.concurrent.Semaphore;

public class Test {

    public volatile int a = 1;

    public Semaphore semaphore2=new Semaphore(0);
    public Semaphore semaphore3=new Semaphore(0);

    public static void main(String[] args) {
        Test t = new Test();
        Runnable runnable1 = () -> System.out.println("first");
        Runnable runnable2 = () -> System.out.println("second");
        Runnable runnable3 = () -> System.out.println("third");

        try{
            for (int i = 0; i < 100; i++) {
                t.first_1(runnable1);
                t.second_1(runnable2);
                t.third_1(runnable3);
            }
        }catch (InterruptedException e){

        }

        /**
         *
         */

    }


    public void first(Runnable printFirst) {
        while (a!=1);
        printFirst.run();
        a++;
    }

    public void second(Runnable printSecond) {
        while (a !=2) ;
        printSecond.run();
        a++;
    }

    public void third(Runnable printThird) {
        while (a !=3) ;
        printThird.run();
        a = 1;
    }


    public void first_1(Runnable printFirst) {
        printFirst.run();
        semaphore2.release();
    }

    public void second_1(Runnable printSecond) throws InterruptedException {
        semaphore2.acquire();
        printSecond.run();
        semaphore3.release();
    }

    public void third_1(Runnable printThird) throws InterruptedException {
        semaphore3.acquire();
        printThird.run();
    }

}
