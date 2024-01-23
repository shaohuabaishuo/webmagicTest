package thread;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest {


    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    private static final int RUNNING = -1 << COUNT_BITS;

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    public static void main(String[] args) {
        System.out.println(runStateOf(ctl.get()));
        int i=0;
        xx:
        for (;;){
            System.out.println("-----");
            for (;;){
                System.out.println("xxxxxx");
                i++;
                if(i==10){
                    break xx;
                }
            }
        }
        System.out.println("end");
    }
}
