package gof23.Singlton;

public final class HolderSinglton {

    private static class SingltonHolder {
        public static final HolderSinglton singlton = new HolderSinglton();
    }

    public HolderSinglton() {
        System.out.println("singlton 开始实例化");
    }

    public static HolderSinglton getSinglton() {
        return SingltonHolder.singlton;
    }

    public static void doOtherThing() {
        System.out.println("我在做其他	事情");
    }
}
