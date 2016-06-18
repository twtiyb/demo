package base.Serializable.base;

import java.io.*;

public class testSerializable extends parent implements Serializable {
    public String sonValue = "20";

    public contain con = new contain();

    public static void main(String[] arg) throws IOException, ClassNotFoundException {
        //输出序列化
        FileOutputStream fos = new FileOutputStream("c:\\out.txt");
        testSerializable test = new testSerializable();
        ObjectOutputStream ous = new ObjectOutputStream(fos);
        ous.writeObject(test);
        ous.flush();
        ous.close();

        //读取反序列化
        FileInputStream fis = new FileInputStream("c:\\out.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        testSerializable test1 = (testSerializable) ois.readObject();
        ois.close();
        System.out.println("sonValue:" + test1.sonValue);
        System.out.println("con:" + test1.con.version);
        System.out.println("publicParentValue:" + test1.publicValue);
    }
}
