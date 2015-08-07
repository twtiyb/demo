package Thread;


/**
 * 多线程的测试
 *
 * @author Administrator
 */
public class SimpleRunnable implements Runnable {

    private String message;

    public static void main(String args[]) {
        SimpleRunnable r1 = new SimpleRunnable("Hello");
        SimpleRunnable r2 = new SimpleRunnable("Hello ro2");
        Thread t2 = new Thread(r2);
        Thread t1 = new Thread(r1);
        t1.start();
        t2.start();
        String[] aa = "aaa|bbb|ccc".split("\\|");
        for (int i = 0; i < aa.length; i++)
            System.out.println("--" + aa[i]);
    }

    public SimpleRunnable(String message) {
        this.message = message;
    }

    public void run() {
        for (; ; ) {
            System.out.println(message);
        }
    }
}