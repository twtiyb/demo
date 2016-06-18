package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("Game start");
            }
        });

        new Player("A", cyclicBarrier).start();
        new Player("B", cyclicBarrier).start();
        new Player("C", cyclicBarrier).start();
    }
}

class Player extends Thread {
    private CyclicBarrier cyclicBarrier;

    public Player(String name, CyclicBarrier cyclicBarrier) {
        super(name);
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println(getName() + " is waiting other players...");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}