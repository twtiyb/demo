package util;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0) || (str == "");
    }

    public static boolean isNotEmpty(String str) {
        return (!(isEmpty(str)));
    }

    public static boolean isNumber(String arg0) {
        if (arg0 == null || arg0.length() == 0)
            return false;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(arg0);
        return m.matches();
    }

    public static boolean isDouble(String arg0) {
        if (arg0 == null || arg0.length() == 0)
            return false;
        try {
            Double.parseDouble(arg0);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String nullToEmpty(String arg) {
        if (arg == null || "null".equals(arg.toLowerCase())) {
            return "";
        } else {
            return arg;
        }
    }

    public static String joinString(String[] array, String separator) {
        String value = "";
        for (int i = 0; i < array.length; i++) {
            value += array[i] + separator;
        }
        if (value.length() > 1)
            value = value.substring(0, value.length() - separator.length());
        return value;
    }

    public static String joinString(List<String> list, String separator) {
        String value = "";
        for (int i = 0; i < list.size(); i++) {
            value += list.get(i) + separator;
        }
        if (value.length() > 1)
            value = value.substring(0, value.length() - separator.length());
        return value;
    }

    public static String joinString(Collection<String> coll, String separator) {
        String value = "";
        Iterator<String> iterator = coll.iterator();
        while (iterator.hasNext()) {
            value += iterator.next() + separator;
        }
        if (value.length() > 1)
            value = value.substring(0, value.length() - separator.length());
        return value;
    }

    public static String[] split(String str, String separator) {
        // return StringUtils.split(str, separator);
        // StringUtils.split Bug 分隔后空值不返回
        List<String> list = splitToList(str, separator);
        return list.toArray(new String[list.size()]);
    }

    public static List<String> splitToList(String str, String separator) {
        List<String> list = new ArrayList<String>();
        if (StringUtil.isEmpty(str)) {
            return list;
        }

        if (StringUtil.isEmpty(separator)) {
            list.add(str);
            return list;
        }
        int lastIndex = -1;
        int index = str.indexOf(separator);
        if (-1 == index && str != null) {
            list.add(str);
            return list;
        }
        while (index >= 0) {
            if (index > lastIndex) {
                list.add(str.substring(lastIndex + 1, index));
            } else {
                list.add("");
            }

            lastIndex = index;
            index = str.indexOf(separator, index + 1);
            if (index == -1) {
                list.add(str.substring(lastIndex + 1, str.length()));
            }
        }
        return list;
    }

    public static String lpad(String str, int length, String pad) {
        while (str.length() < length) {
            str = pad + str;
        }
        return str;
    }

    public static String rpad(String str, int length, String pad) {
        while (str.length() < length) {
            str = str + pad;
        }
        return str;
    }

    /**
     * 首字母大写
     *
     * @param srcStr
     * @return
     */
    public static String firstCharacterToUpper(String srcStr) {
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
    }

    /**
     * 替换字符串并让它的下一个字母为大写
     *
     * @param srcStr
     * @param org
     * @param ob
     * @return
     */
    public static String replaceUnderlineAndfirstToUpper(String srcStr,
                                                         String org, String ob) {
        String newString = "";
        int first = 0;
        while (srcStr.indexOf(org) != -1) {
            first = srcStr.indexOf(org);
            if (first != srcStr.length()) {
                newString = newString + srcStr.substring(0, first) + ob;
                srcStr = srcStr
                        .substring(first + org.length(), srcStr.length());
                srcStr = firstCharacterToUpper(srcStr);
            }
        }
        newString = newString + srcStr;
        return newString;
    }

    public static boolean isMobileNo(String mobile) {
        if (mobile == null || mobile.length() == 0)
            return false;
        Pattern p = Pattern.compile("^(1)\\d{10}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static boolean isPhoneNo(String phone) {
        if (phone == null || phone.length() == 0)
            return false;
        Pattern p = Pattern.compile("^(\\d{3,4}-)?\\d{7,8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static String stringCut(String aString, int aLen, String aHintStr) {
        if (aString == null)
            return aString;
        int lLen = aString.length(), i;
        for (i = 0; aLen >= 0 && i < lLen; ++i)
            if (isBigChar(aString.charAt(i)))
                aLen -= 2;
            else
                --aLen;
        if (aLen >= 0)
            return aString;
        if (aHintStr == null)
            return aString.substring(0, i - 1);

        aLen -= aHintStr.length();
        for (; aLen < 0 && --i >= 0; )
            if (isBigChar(aString.charAt(i)))
                aLen += 2;
            else
                ++aLen;

        return aHintStr == null ? aString.substring(0, i) : aString.substring(
                0, i)
                + aHintStr;
    }

    public static boolean isBigChar(char c) {
        return c < 0 || c > 256;
    }

    public static String trim(String arg) {
        if (arg == null)
            return null;
        return arg.trim();
    }

    public static String[] tokenizeToStringArray(
            String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null) {
            return null;
        }
        return collection.toArray(new String[collection.size()]);
    }

    public static String[] tokenizeToStringArray(
            String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    public static void main(String[] arg) {
        String[] basePackages = StringUtils.tokenizeToStringArray("ss.bb", ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        String ss = "aaa.bb".replace(".", "/");
        System.out.print(basePackages);
    }

    /**
     * 判断短信字数。（现在的服务商基本不按这个来。而是直接string.lenth()/67。不管它是字符还是汉字都按1来算。）
     *
     * @param str
     * @return
     */
    public float countSmsWords(String str) {
        if (str == null || str.length() <= 0) {
            return 0;
        }
        float len = 0;
        char c;
        for (int i = str.length() - 1; i >= 0; i--) {
            c = str.charAt(i);
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
                    || (c >= 'A' && c <= 'Z')) {
                // 字母, 数字
                len += 0.5;
            } else {
                if (Character.isLetter(c)) { // 中文
                    len++;
                } else { // 符号或控制字符
                    len += 0.5;
                }
            }
        }
        return len;
    }
}
