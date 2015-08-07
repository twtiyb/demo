package Gof23.StaticFactory;

public class Factory {
    public static Object getBean(String name) throws Exception {
        if (name == null) {
            throw new Exception();
        }
        if (name.equals("apple")) {
            return new ProductA();
        } else if (name.equals("li")) {
            return new ProductB();
        } else {
            throw new Exception();
        }
    }
}
