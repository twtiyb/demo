package Thread;

/**
 * 线程同步问题
 */

/**
 * 线程锁加在当前类中，不能实现同步。
 *
 * @author Administrator
 */
public class ThreadTest implements Runnable {
    private int threadNo;
    private int synType;

    public ThreadTest(int threadNo, int synType) {
        this.threadNo = threadNo;
        this.synType = synType;
    }

    public ThreadTest() {

    }

    public static void main(String[] args) throws Exception {
        ThreadTest threadTest = new ThreadTest();
        for (int i = 1; i < 10; i++) {
            Thread t1 = new Thread(new ThreadTest(i, 1));
//			Thread t2 = new Thread(new ThreadTest(i,2));
//			Thread t3 = new Thread(new ThreadTest(i,3));
//			Thread t4 = new Thread(new ThreadTest(i,4));
            t1.start();
//			t2.start();
//			t3.start();
//			t4.start();
            Thread.sleep(1);
        }
    }

    public synchronized void run() {
        if (this.synType == 1) {
            runType1();
        }
        if (this.synType == 4) {
            runType4();
        }
        if (this.synType == 3) {
            runType3();
        }
        if (this.synType == 2) {
            runType2();
        }
    }

    public void runType1() {
        synchronized (this) {
            for (int i = 1; i < 10000; i++) {
                System.out.println("No." + threadNo + ":" + i);
            }
        }
    }

    public synchronized void runType2() {
        for (int i = 1; i < 10000; i++) {
            System.out.println("No." + threadNo + ":" + i);
        }
    }

    public synchronized void runType3() {
        for (int i = 1; i < 10000; i++) {
            System.out.println("No." + threadNo + ":" + i);
        }
    }

    public synchronized void runType4() {
        for (int i = 1; i < 10000; i++) {
            System.out.println("No." + threadNo + ":" + i);
        }
    }
}
