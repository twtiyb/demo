package gof23.Singlton;

public final class SynSinglton {
    private static SynSinglton obj;

    private SynSinglton() {
        System.out.println("singlton 开始实例化");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static synchronized SynSinglton getSinglton() {
        if (obj == null) {
            obj = new SynSinglton();
        }
        return obj;
    }

    public static void doOtherThing() {
        System.out.println("我在做其他事情");
    }
}
