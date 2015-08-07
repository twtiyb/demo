package Base.staticObjectCreate;

public class StaticInitialization {
    //加上static ，启动顺序 。
    public static void main(String[] args) {
        System.out.println("Creating new Cupboard() in main");
//		new Cupboard();
//		System.out.println("Creating new Cupboard() in main");
//		new Cupboard();
//		t2.f2(1);
//		t3.f3(1);
    }

    static tables s = tables();

    static class tables {
        public void tables() {
            System.out.print("-----tables()");
        }
    }

    static Table t2 = new Table();
    static Cupboard t3 = new Cupboard();

    private static tables tables() {
        System.out.println("-----tables()");
        return s;
    }

}
