package Thread;

public class MyThread extends Thread {
    class SleepThread extends Thread {
        public void run() {
            try {
                System.out.println("33");
                sleep(2000);
            } catch (Exception e) {
            }
        }
    }

    public void run() {

        System.out.println("33");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        MyThread thread = new MyThread();
        SleepThread sleepThread = thread.new SleepThread();
        sleepThread.start(); // 开始运行线程sleepThread
        sleepThread.join(); // 使线程sleepThread延迟2秒
        thread.start();
        sleep(5000);
        thread.start();
    }
}