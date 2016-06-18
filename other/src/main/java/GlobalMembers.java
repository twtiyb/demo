import java.io.UnsupportedEncodingException;

public class GlobalMembers {
    public static int StrToInt(String str) {
        // + 43
        // - 45
        // 空格 32
        // 0 48 - 9 57
        String s = str;
        char[] aC = new char[s.length()];
        char[] bC = new char[s.length()];
        int a = 0;
        int b = 0;
        String bS = "";
        boolean isHead = true;
        try {
            byte[] aBytes = s.trim().getBytes("ASCII");
            if (aBytes.length == 0)
                return 0;
            for (int i = 0; i < aBytes.length; i++) {
                if ((47 < aBytes[i] && aBytes[i] < 58)) {
                    isHead = false;
                    if (48 == aBytes[i] && isHead)
                        continue;
                    aC[a++] = (char) (aBytes[i]);
                } else if ((43 == aBytes[i] || 45 == aBytes[i]) && isHead) {
                    bC[b++] = (char) (aBytes[i]);
                } else {
                    break;
                }
            }
            if (b > 1)
                return 0;
            if (a == 0)
                return 0;
            if (b == 1)
                bS = String.valueOf(bC[b - 1]).equals("+") ? "" : "-";

            return new Integer(bS + String.valueOf(aC, 0, a)).intValue();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // start 提示：自动阅卷起始唯一标识，请勿删除或增加。
    public static void main(String args[]) {
        GlobalMembers s = new GlobalMembers();
        String[] ss = {"1 ", "+1", "-1", "123  ", "-123 ", "010  ",
                "+00131204  ", "-01324000  ", "-2147483647", "abc  ", "-abc ",
                "1a", "23a8f", "-3924x8fc  ", " 321 ", "	-321 ", "123  456",
                "123  ", "  -321  ", "  --2", "  ++c"};
        for (String sa : ss) {
            System.out.println(s.StrToInt(sa));
        }
    }
    // end //提示：自动阅卷结束唯一标识，请勿删除或增加。
}