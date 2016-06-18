package objectToBean;

import objectToBean.bean.Car;
import objectToBean.bean.Respons;
import objectToBean.bean.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class test {
    public static void main(String arg[]) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
//		User user1 = new User();
//		user1.setName("dd1d1");
//		user1.setPass("ddsf1");
//		
//		Class cal = User.class;
//		Method method = cal.getMethod("setName", String.class);
//		method.invoke(user1, "3333");
//		System.out.print(user1);
//		
//		User user2 = new User();
//		user2.setName("ddd2");
//		user2.setPass("ddsf2");
//		
//		Car car = new Car();
//		car.setPrice("ss");
//		car.setName("car");
//
//		Respons res = new Respons();
//		res.setClassName("User");
//		List<Object> objList = new ArrayList();
//		objList.add(user1);
//		objList.add(user2);
//		objList.add(car);
//		res.setObjList(objList);
//		parseRespons(res);
//		
        try {
            User user = new User();
            Field field = user.getClass().getDeclaredField("car");
            field.setAccessible(true);
            Car car = (Car) field.get(user);
            System.out.print(car.a);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void parseRespons(Respons res) {
        List<Object> objList = res.getObjList();
        for (Object obj : objList) {
            if (obj instanceof User) {
                User user = (User) obj;
                System.out.println(user.getName());
            }
            if (obj instanceof Car) {
                Car car = (Car) obj;
                System.out.println(car.getName());
            }
        }
    }
}
