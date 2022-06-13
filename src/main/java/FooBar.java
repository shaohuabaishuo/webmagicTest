import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FooBar {
    private int n;
    private volatile boolean bl;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public FooBar(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(10);
        try {
            fooBar.bar(()-> System.out.println("Bar"));
            fooBar.foo(()-> System.out.println("Foo"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                while (bl) { //当false时输出foo,否则在这里阻塞
                    condition.await();
                }
                // printFoo.run() outputs "foo".
                printFoo.run();
                bl = true;
                condition.signal();
            } finally {
                lock.unlock();
            }

        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                while (!bl) { //当true时输出bar,否则在这里阻塞
                    condition.await();
                }
                // printBar.run() outputs "bar".
                printBar.run();
                bl = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
