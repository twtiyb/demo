package Gof23.StaticFactory;

public class FactoryTest {
    public static void main(String[] args) throws Exception {
        ProductA appble = (ProductA) Factory.getBean("apple");
        System.out.println(appble.getName());
    }
}
