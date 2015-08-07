package IO;

import java.io.*;

/**
 * 文件写入 比较
 * filewriter 最快 ，
 *
 * @author Administrator
 */
public class OutTofile {
    public static void main(String[] args) throws IOException {
        byte[] buff = new byte[]{};
        try {
            //fileOutputStream 流文件的读写
            String aa = "你好123";
            buff = aa.getBytes();
            FileOutputStream out = new FileOutputStream("D://out.txt");
            out.write(buff, 0, buff.length);
            out.close();

            //
            //true为追加，false为覆盖
            FileWriter fw = new FileWriter("d:/sss.txt", true);
            fw.append("dssss");
            for (int i = 0; i < 10; i++) {
                fw.append("333 \n");
            }
            fw.close();
            System.out.print("------------");


            //FileOutputStream
            FileOutputStream bos = new FileOutputStream(new File("d:/sssddd.txt"));
            BufferedOutputStream boss = new BufferedOutputStream(bos);
            boss.write("ddsss".getBytes());
//			boss.flush();
            boss.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
