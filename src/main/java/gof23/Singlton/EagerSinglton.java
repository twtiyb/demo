package Gof23.Singlton;

public final class EagerSinglton {
    private static EagerSinglton obj = new EagerSinglton();

    EagerSinglton() {
        System.out.println("singlton 开始实例化");
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static EagerSinglton getSinglton() {
        return obj;
    }

    public static void doOtherThing() {
        System.out.println("我在做其他事情");
    }
}
