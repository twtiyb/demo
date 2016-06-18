package concurrent;

import java.util.concurrent.*;

public class ExecutorServiceDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);

        Future<Integer> r1 = es.submit(new MC(1, 100));
        Future<Integer> r2 = es.submit(new MC(100, 10000));
        Future<Integer> r3 = es.submit(new MC(100, 10000));
        System.out.println (r1.get ()+":"+r2.get ());


        es.submit(new RA());
        es.shutdown();
    }
}

class RA implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
class MC implements Callable<Integer> {
    private int begin, end;

    public MC(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = begin; i < end; i++) {
            sum += i;
        }
        System.out.println(Thread.currentThread().getName() + " : " + sum);
        return sum;
    }
}