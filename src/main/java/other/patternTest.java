package other;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用正则表达式
 */

public class patternTest {

    public static void main(String[] args) throws IOException {
        Pattern patternClass = Pattern.compile(" (class|style)=\".*\" ");
        Reader buff = new FileReader("c:\\html.txt");
        FileWriter write = new FileWriter("c:\\target.txt");
        BufferedReader in = new BufferedReader(buff);
        String s;
        while ((s = in.readLine()) != null) {
            Matcher matcher = patternClass.matcher(s);
            if (matcher.find()) {
                s = matcher.replaceFirst(" ");
                System.out.println(s);
            } else {
                System.out.println(s);
            }
            write.write(s + "\n");
        }
        write.close();
        in.close();
    }
}
