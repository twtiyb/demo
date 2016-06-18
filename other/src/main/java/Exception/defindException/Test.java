package Exception.defindException;

public class Test {
    public void regist(int num) throws MyException {
        if (num < 0)
            throw new MyException();
        else
            System.out.println("注册成功！");
    }

    public static void main(String[] args) {
        try {
            System.out.print("acbcd".toUpperCase());
            Test test = new Test();
            test.regist(-1);
        } catch (MyException e) {
            e.f();
        }
    }
}