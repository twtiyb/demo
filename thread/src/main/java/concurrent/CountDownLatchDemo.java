package concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Racer("A", countDownLatch).start();
        new Racer("B", countDownLatch).start();
        new Racer("C", countDownLatch).start();
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(3 - i);
            countDownLatch.countDown();
            if (i == 2) {
                System.out.println("Start");
            }
        }
    }

}

class Racer extends Thread {

    private CountDownLatch countDownLatch;

    public Racer(String name, CountDownLatch countDownLatch) {
        super(name);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            for (int i = 0; i < 3; i++) {
                System.out.println(getName() + ":" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}