package concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        Person p1 = new Person("A", semaphore);
        p1.start();
        Person p2 = new Person("B", semaphore);
        p2.start();
        Person p3 = new Person("C", semaphore);
        p3.start();
    }

  }

  class Person extends Thread {

      private Semaphore semaphore;

      public Person(String name, Semaphore semaphore) {
          super(name);
          this.semaphore = semaphore;
      }

      @Override
      public void run() {
          System.out.println(getName() + "is waiting ...");
          try {
              semaphore.acquire();
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          System.out.println(getName() + "is done!");
          semaphore.release();
      }
  }