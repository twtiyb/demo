import java.util.Date;

public class test2 {
    public static void main(String[] args) {
        Date begin = new Date();
        Date end = new Date();
        int m = new Double(Math.floor((end.getTime() - begin.getTime()) / 1000.0 / 3600)).intValue();
        System.out.print(m);
    }
}
