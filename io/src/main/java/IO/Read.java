package io;

import java.io.*;
import java.net.URLDecoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Read {
    static String context = "";// 创建一个字符串用来记录目录及文件;
    static String desPath = "c:\\a.txt";
    static String sourPath = "c:\\b.txt";

    static void getDir(String strPath) throws Exception { // 递归
        try {
            File f = new File(strPath);
            if (f.isDirectory()) {
                File[] fList = f.listFiles();
                for (int j = 0; j < fList.length; j++) {
                    if (fList[j].isDirectory()) { // 判断是否为文件夹
                        System.out.println("Directory is: "
                                + fList[j].getPath());
                        context += "-" + fList[j].getName() + "\r\n"; // 输出文件夹名称
                        getDir(fList[j].getPath());
                    }
                }
                for (int j = 0; j < fList.length; j++) {
                    if (fList[j].isFile()) { // 判断是否为文件
                        String name = fList[j].getPath().toString();
                        System.out.println("filename  is: " + name);
                        context += "." + fList[j].getName() + "\r\n"; // 输出文件名称
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error： " + e);
        }
    }

    public void readeByBufferReader(String path) {
        File file = new File(path);
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                buffer.append(reader.readLine() + "\n");
            }
            System.out.print(buffer.toString());
            System.out.print("----------结束---------");
            while (reader.ready()) {
                buffer.append(reader.readLine() + "\n");
            }
            System.out.print(buffer.toString());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void readBigFile() throws FileNotFoundException {
        final int BUFFER_SIZE = 0x1200000;// 缓冲大小为12M

        System.out.println(BUFFER_SIZE);

        File f = new File("a.txt");

        int len = 0;
        Long start = System.currentTimeMillis();
        for (int z = 8; z > 0; z--) {
            MappedByteBuffer inputBuffer = null;
            try {
                inputBuffer = new RandomAccessFile(f, "r")
                        .getChannel().map(FileChannel.MapMode.READ_ONLY,
                                f.length() * (z - 1) / 8, f.length() * 1 / 8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] dst = new byte[BUFFER_SIZE];// 每次读出12M的内容
            for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
                if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
                    for (int i = 0; i < BUFFER_SIZE; i++)
                        dst[i] = inputBuffer.get(offset + i);
                } else {
                    for (int i = 0; i < inputBuffer.capacity() - offset; i++)
                        dst[i] = inputBuffer.get(offset + i);
                }
                int length = (inputBuffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE
                        : inputBuffer.capacity() % BUFFER_SIZE;

                len += new String(dst, 0, length).length();
                System.out.println(new String(dst, 0, length).length() + "-" + (z - 1) + "-" + (8 - z + 1));
            }
        }
        System.out.println(len);
        long end = System.currentTimeMillis();
        System.out.println("读取文件文件花费：" + (end - start) + "毫秒");
    }

    public static void main(String[] args) {
        Read read = new Read();
        read.readeByBufferReader(read.sourPath);

        String enStr;
        try {
//			enStr = URLEncoder.encode("天天%工");
            enStr = "天天%工";
            String deStr = URLDecoder.decode(enStr, "utf-8");
            System.out.print(enStr + "\n" + deStr);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


//		String strPath = "C:\\dev\\training"; // 选择路径
//		System.out.println(strPath);
//		try {
//			getDir(strPath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			File file = new File("c:\\a.txt"); // 创建一个file文件
//			FileWriter writer = new FileWriter(file);
//			writer.write(n); // 把目录写到文件file中
//			writer.close();
//		} catch (IOException ex) {
//			System.err.println(ex);
//		}
//		try {
//			File inFile = new File("C:\\a.txt");
//			File outFile = new File("c:\\b.txt");
//			FileInputStream fis = new FileInputStream(inFile);
//			FileOutputStream fos = new FileOutputStream(outFile);
//			int c;
//			while ((c = fis.read()) != -1)
//				fos.write(c);
//			fis.close();
//			fos.close();
//		} catch (FileNotFoundException e) {
//			System.out.println("FileStreamsTest" + e);
//		} catch (IOException e) {
//			System.err.println("FileStreamsTest: " + e);
//
//		}

    }
}
