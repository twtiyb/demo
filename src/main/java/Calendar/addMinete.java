package Calendar;

import java.util.Calendar;

public class addMinete {
    public static void main(String arg[]) {
        addMinete test = new addMinete();
        test.testAddMinete01();
    }

    /**
     * 增加超过当前极限时，是否向前进位。
     */
    public void testAddMinete01() {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime());
        cal.add(Calendar.MINUTE, 70);
        System.out.println(cal.getTime());
    }
}
