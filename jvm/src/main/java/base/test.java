package base;

/**
 * 题目：String a = new String("a");
 * 此句创建了几个对象。
 * <p/>
 * 答：2个。
 * <p/>
 * 关于对象的创建，以及new的作用。
 * 首先创建
 * String a =  "aaa" ; 这句执行是jvm在string池中创建“aaa”对象，并返回该对象的引用给a
 * String a = new String("aaa") ; 这句执行是先执行上句，然后再执行new，即在内存中创建“aaa”字串符，并返回该引用。即创建了两个对象。
 * 再看int,因为是基本类型。所以只会在基本类型池中。而且不能执行new语句。基本类型不会存在于内存中。
 * Long与Sring一样。
 *
 * @author Administrator
 */
public class test {
    public static void main(String arg[]) {
        test test = new test();
        System.out.println("字符符测试----对象");
        test.test1();
        test.test2();
        test.test3();
        System.out.println("int测试----基本类型");
        test.test4();
        System.out.println("Long测试----对象");
        test.test5();
        test.test6();
        test.test7();
        String s1 = new String("abc");
        String s2 = new String("abc");
        s1 = "f";
        s1 = "abc";
        System.out.println(Math.ceil(1.2));
    }

    public void test1() {
        String a = new String("aaa");
        String b = new String("aaa");
        System.out.println("     test1   ------   " + (a == b));
    }

    public void test2() {
        String a = "aaa";
        String b = new String("aaa");
        System.out.println("     test1   ------   " + (a == b));
    }

    public void test3() {
        String a = "aaa";
        String b = "aaa";
        System.out.println("     test1   ------   " + (a == b));
    }

    public void test4() {
        int a = 1;
        int b = 1;
        System.out.println("     test1   ------   " + (a == b));
    }

    public void test5() {
        Long a = new Long(1L);
        Long b = new Long(1L);
        System.out.println("     test1   ------   " + (a == b));
    }

    public void test6() {
        Long a = 1L;
        Long b = new Long(1L);
        System.out.println("     test1   ------   " + (a == b));
    }

    public void test7() {
        Long a = 1L;
        Long b = 1L;
        System.out.println("     test1   ------   " + (a == b));
    }
}
