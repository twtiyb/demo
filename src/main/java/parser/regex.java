package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex {
    public static void main(String args[]) {
        Pattern pattern = Pattern.compile("[^0-9]*");
        Matcher matcher = pattern.matcher("4564564565");
        if (matcher.find() && !matcher.group().equals("")) {
            System.out.println(matcher.group());
        } else {
            System.out.println("false");

        }
        System.out.print("add".substring(0, 1).toUpperCase() + "dd");
    }
}
