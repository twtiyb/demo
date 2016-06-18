package Thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadRun {

    public static List<String> tasks = Collections.synchronizedList(new ArrayList<String>());
    public static List<Th> threads = Collections.synchronizedList(new ArrayList<Th>());

    static {
        for (int i = 0; i < 100; i++) {
            tasks.add("任务" + i);
        }
        for (int i = 0; i < 10; i++) {
            Th th = new Th();
            th.setName("任务线程" + i);
            th.start();
            threads.add(th);
        }
    }

    public static void main(String[] args) {
        while (!tasks.isEmpty()) {
            String task = tasks.remove(0);
            if (!threads.isEmpty()) {
                Th t = threads.remove(0);
                t.setTask(task);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Th extends Thread {
    private String task;

    public void run() {
        while (!ThreadRun.tasks.isEmpty()) {
            if (task != null) {
                System.out.println("任务 ： [" + task + "]被[" + this.getName() + "]执行......");
                task = null;
                ThreadRun.threads.add(this);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTask(String task) {
        this.task = task;
    }
}
