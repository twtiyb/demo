package base;

/**
 * @author 徐纯
 *         <p/>
 *         2013-1-5 下午06:01:08
 */
public class Test02_1 {
    public static void main(String arg[]) {
        //测试静态成员变量
        Test02_1 test1 = new Test02_1();
        Test02_1 test2 = new Test02_1();
        System.out.println("初始值----" + test1.a);
        test1.staticMethodTest01();
        System.out.println("实例1运行值----" + test1.a);
        test2.staticMethodTest02();
        System.out.println("实例2运行值----" + test1.a);

        //测试静态方法

    }

    public void staticMethodTest01() {
        a++;
    }

    public void staticMethodTest02() {
        a++;
    }

    static Long a = 0L;
}
