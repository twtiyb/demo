package Thread;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ThreadSynchronizedTest {

    int j = 100;

    /**
     * @param args
     */
    public static void main(String[] args) {
        ThreadSynchronizedTest tt = new ThreadSynchronizedTest();
        Inc inc = tt.new Inc();
        long start = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(inc);
            t.start();
        }
        long end = System.nanoTime();
        System.out.println(end - start);
    }

    private synchronized void inc() throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream("c:\\" + Thread.currentThread().getName() + ".txt", true);
            String out = Thread.currentThread().getName() + ": " + j++;
            fos.write(out.getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    class Inc implements Runnable {
        public void run() {
            for (int i = 0; i < 90; i++) {
                try {
                    inc();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
} 