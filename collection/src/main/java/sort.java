import java.util.*;

/**
 * 对list进行排序
 *
 * @author 徐纯
 *         <p/>
 *         2013-5-30 上午11:12:27
 */
public class sort {
    Map<Long, Map<String, List<Long>>> map = new TreeMap<Long, Map<String, List<Long>>>(
            new Comparator<Long>() {
                public int compare(Long o1, Long o2) {
                    return o2.compareTo(o1);
                }
            });

    public static void main(String[] arg) {
        ArrayList<Long> list = new ArrayList<Long>();
        list.add(4L);
        list.add(2L);
        Collections.sort(list);
        for (Long i : list) {
            System.out.println(i);
        }
        list.add(3L);
        for (Long i : list) {
            System.out.println(i);
        }

    }
}
