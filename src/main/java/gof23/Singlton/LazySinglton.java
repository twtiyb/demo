package Gof23.Singlton;


public final class LazySinglton {
    private static LazySinglton obj;

    private LazySinglton() {
        System.out.println("singlton 开始实例化");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static LazySinglton getSinglton() {
        if (obj == null) {
            obj = new LazySinglton();
        }
        return obj;
    }

    public static void doOtherThing() {
        System.out.println("我在做其他事情");
    }
}
