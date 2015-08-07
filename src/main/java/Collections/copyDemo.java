package Collections;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class copyDemo {
    public static void main(String arg[]) throws CloneNotSupportedException,
            IOException, ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Student st1 = new Student();
        st1.setAdress("北大街5号");
        st1.setName("张三");
        ArrayList<Student> stList = new ArrayList<Student>();
        stList.add(st1);

        ArrayList<Student> copyList = new ArrayList<Student>();
        copyList.addAll(stList);

        System.out.println("测试addAll");
        copyDemo demo = new copyDemo();
        demo.print(copyList);
        st1.setName("李四");
        demo.print(copyList);

        System.out.println("\n\n\n");
        System.out.println("测试add");
        copyList = new ArrayList<Student>();
        st1.setName("张三");
        copyList.addAll(copyList);
        demo.print(copyList);
        st1.setName("李四");
        demo.print(copyList);

        System.out.println("\n\n\n");
        System.out.println("测试arryList.clone()");
        copyList = new ArrayList<Student>();
        copyList = (ArrayList<Student>) stList.clone();
        stList.get(0).setName("张三");
        demo.print(copyList);
        st1.setName("李四");
        demo.print(copyList);

        System.out.println("\n\n\n");
        System.out.println("测试entity.clone()");
        copyList = new ArrayList<Student>();
        copyList.add((Student) st1.clone());
        st1.setName("张三");
        demo.print(copyList);
        st1.setName("李四");
        demo.print(copyList);

        System.out.println("\n\n\n");
        System.out.println("测试序列化，反序列化 复制");
        copyList = (ArrayList<Student>) demo.copyBySerialize(stList);
        st1.setName("张三");
        demo.print(copyList);
        st1.setName("李四");
        demo.print(copyList);

        System.out.println("\n\n\n");
        System.out.println("测试深层复制");
        copyList = new ArrayList<Student>();
        demo.copyList(stList, copyList);
        st1.setName("张三");
        demo.print(copyList);
        st1.setName("李四");
        demo.print(copyList);

        for (Field method : Student.class.getDeclaredFields()) {
            System.out.println(method.getName());
        }
        for (Field method : Student.class.getFields()) {
            System.out.println(method.getName());
        }
        System.out.println("private方法");
        for (Method method : Student.class.getDeclaredMethods()) {
            System.out.println(method.getName());
        }
        System.out.println("public方法");
        for (Method method : Student.class.getMethods()) {

            System.out.println(method.getName());
        }
    }

    // 深拷贝1：递归深拷贝方法
    public <T> void copyList(List<T> src, List<T> dest) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (int i = 0; i < src.size(); i++) {
            T obj = src.get(i);
            if (obj instanceof List) {
                dest.add((T) new ArrayList());
                copyList((List) obj, (List) ((List) dest).get(i));
            } else {
                dest.add((T) obj.getClass().getDeclaredMethod("clone").invoke(obj));
            }
        }
    }

    // 深拷贝2:序列化|反序列化方法
    public List copyBySerialize(List src) throws IOException,
            ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut
                .toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List dest = (List) in.readObject();
        return dest;
    }

    public void print(ArrayList<? extends Object> list) {
        for (Object ob : list) {
            if (ob instanceof ArrayList) {
                print((ArrayList<Object>) ob);
            } else {
                Method[] methods = ob.getClass().getMethods();
                for (Method method : methods) {
                    if (method.getName().startsWith("get")) {
                        try {
                            System.out.println(method.invoke(ob));
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
