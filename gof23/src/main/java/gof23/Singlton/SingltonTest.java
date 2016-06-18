package gof23.Singlton;

public class SingltonTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("饥饿模式 ，只要调用就会初始化。不管这个方法有没有用到." +
                "它是利用了static关键字进行初始化。因为它的特性，不会出现线程安全的问题");
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    EagerSinglton.doOtherThing();
                }
            }).start();
        }
        while (Thread.activeCount() != 1) {
        }
        System.out.println();

        System.out.println("延迟加载模式 ，调用其他方法，不会影响到获取实例.但是存在线程安全问题。" +
                "如果实例创建的很快，那么其实应该获取的是只有一次初始化的，但是创建实例比较慢。则第一个线程还没有创建好，第二个线程也会去创建");
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    LazySinglton.getSinglton();
                }
            }).start();
        }


        while (Thread.activeCount() != 1) {
        }
        System.out.println();

        System.out.println("延迟加载模式+同步 ，为了解决上一个问题，加入同步的关键字。" +
                "这样，当第二个线程去执行这个方法的时候，就会等第一个线程执行完了才去执行。" +
                "关键键字加在方法上，执行效率慢。关键字加在方法里边，会形成DOUBLE-CHECK-LOCK的效果。" +
                "但是这个有问题，第二个线程会得到一个并没有初始化完成的对象");
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    LazySinglton.getSinglton();
                }
            }).start();
        }

        while (Thread.activeCount() != 1) {
        }
        System.out.println();

        System.out.println("内部类 ，利用内部类在加载时不会初始化，只有使用的时候才去初始化，而且初始化的时候会有天然的锁机制，不会造成线程安全的问题。");
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    HolderSinglton.getSinglton();
                }
            }).start();
        }
//		System.exit(0);
    }
}
