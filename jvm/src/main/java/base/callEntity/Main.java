package base.callEntity;

public class Main {
    public static void main(String arg[]) {
        Main main = new Main();

        User aaa = new User();
        if (aaa.getSss() == null) {
            System.out.print("ll");
        }
        if (aaa.getSss() == null) {
            System.out.print("ll");
        }

        //普通的设置值
        User user = new User();
        System.out.println(" 普通的设置值 ");
        System.out.println(user.getUserName() + " " + user.getUserPass());
        main.changeValue(user);
        System.out.println(user.getUserName() + " " + user.getUserPass());

        //先new 再赋值
        user = new User();
        System.out.println(" 先new 再赋值 ");
        System.out.println(user.getUserName() + " " + user.getUserPass());
        main.changeValueByNew(user);
        System.out.println(user.getUserName() + " " + user.getUserPass());
    }

    public void changeValue(User user) {
        user.setUserName("bb");
        user.setUserPass("aa");
    }

    public void changeValueByNew(User user) {
        User aa = new User();
        aa = user;
        aa.setUserName("bb");
        aa.setUserPass("aa");
    }
}
