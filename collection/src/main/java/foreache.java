import java.util.*;

public class foreache {
    //最常规的一种遍历方法，最常规就是最常用的，虽然不复杂，但很重要，这是我们最熟悉的，就不多说了！！
    public void work(Map<String, Student> map) {
        Collection<Student> c = map.values();
        Iterator it = c.iterator();
        for (; it.hasNext(); ) {
            System.out.println(it.next());
        }
    }

    //利用keyset进行遍历，它的优点在于可以根据你所想要的key值得到你想要的 values，更具灵活性！！
    public void workByKeySet(Map<String, Student> map) {
        Set<String> key = map.keySet();
        for (Iterator it = key.iterator(); it.hasNext(); ) {
            String s = (String) it.next();
            System.out.println(map.get(s));
        }
    }

    //比较复杂的一种遍历在这里，呵呵~~他很暴力哦，它的灵活性太强了，想得到什么就能得到什么~~
    public void workByEntry(Map<String, Student> map) {
        Set<Map.Entry<String, Student>> set = map.entrySet();
        for (Iterator<Map.Entry<String, Student>> it = set.iterator(); it.hasNext(); ) {
            Map.Entry<String, Student> entry = (Map.Entry<String, Student>) it.next();
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }
    }

    public static void main(String arg[]) {
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        student1.setAdress("aaaaaaaaaa111");
        student1.setGradId(1L);
        student1.setName("1");
        student1.setStuId(1L);
        student2.setAdress("aaaaaaaaaa222");
        student2.setGradId(2L);
        student2.setName("2");
        student2.setStuId(2L);
        student3.setAdress("aaaaaaaaaa333");
        student3.setGradId(3L);
        student3.setName("3");
        student3.setStuId(3L);
        Map map = new HashMap<String, Student>();
        map.put("1", student1);
        map.put("2", student2);
        map.put("3", student3);
        foreache foreacheTest = new foreache();
        System.out.println("-------------------------->常规 1");
        foreacheTest.work(map);
        System.out.println("-------------------------->set。我一般用这种 2");
        foreacheTest.workByKeySet(map);
        System.out.println("-------------------------->万能 3");
        foreacheTest.workByEntry(map);
    }
}
