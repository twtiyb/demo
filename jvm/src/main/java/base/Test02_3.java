package base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐纯
 *         <p/>
 *         2013-1-5 下午06:01:08
 */
public class Test02_3 {
    public static void main(String arg[]) {
        final List<Object> level33 = new ArrayList<Object>() {{
            add(6);
            add(2);
        }};
        final List<Object> level23 = new ArrayList<Object>() {{
            add(level33);
            add(5);
        }};
        final List<Object> level21 = new ArrayList<Object>() {{
            add(3);
            add(4);
        }};
        List<Object> level11 = new ArrayList<Object>() {{
            add(level21);
            add(8);
            add(level23);
        }};
        Test02_3 test = new Test02_3();
        test.printValue(level11);
    }

    public void printValue(Object objList) {
        for (Object obj : (List) objList) {
            if (obj instanceof List) {
                printValue(obj);
            } else {
                System.out.print(obj);
            }
        }
    }
}
