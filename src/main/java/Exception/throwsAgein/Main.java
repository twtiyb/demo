package Exception.throwsAgein;

public class Main {
    public static void main(String arg[]) {
        Main test = new Main();
        try {
            test.throws1();
        } catch (Exception e) {
            System.out.println("catch throws1");
        }
    }

    public void throws1() throws Exception {
        try {
            this.throws2();
        } catch (Exception e) {
            System.out.println("catch throws2");
//			throw e;
        }
    }

    public void throws2() throws Exception {
        throw new Exception("throw2");
    }
}
