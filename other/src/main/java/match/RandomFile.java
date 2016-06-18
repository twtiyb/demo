package match;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by 王尼玛 on 2014/3/26.
 */
public class RandomFile {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 15;
    private static final int STR_NUM = 200000000;
    private static final String FILEPATH = "a.txt";
    private static final Random random = new Random(System.currentTimeMillis());
    private static final Random random_char = new Random(System
            .currentTimeMillis());

    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILEPATH));
        for (int i = 0; i < STR_NUM; i++) {
            bw.write(getRandom());
            bw.newLine();
            if (i % 200 == 0) {
                bw.flush();
            }
        }
        bw.flush();
        bw.close();
        long end = System.currentTimeMillis();
        System.out.println(STR_NUM + ":" + (end - begin));
    }

    List<String> getTopN(String filepath, int n) throws IOException {
        // TODO 实现
        BufferedReader reader = new BufferedReader(new FileReader(FILEPATH));
        StringBuffer buffer = new StringBuffer();
        List<String> list = new ArrayList<String>();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String ss = "";
        while (reader.ready()) {
            ss = reader.readLine();
            if (map.containsKey(ss)) {
                map.get(ss);
            }
        }

        return list;
    }

    private static String getRandom() {
        StringBuilder sb = new StringBuilder();
        int size = random.nextInt(MAX_LENGTH - MIN_LENGTH) + MIN_LENGTH;
        for (int i = 0; i <= size; i++) {
            int index = random_char.nextInt(chars_length);
            sb.append(source_chars[index]);
        }
        return sb.toString();
    }

    private static final char[] source_chars = new char[('9' - '0' + 1)
            + ('G' - 'A' + 1)];
    private static final int chars_length = source_chars.length;

    static {
        int i = 0;
        for (char c = '0'; c <= '9'; c++) {
            source_chars[i++] = c;
        }
        for (char c = 'A'; c <= 'G'; c++) {
            source_chars[i++] = c;
        }
    }
}