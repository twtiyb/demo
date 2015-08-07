package Base;

public class jiemi {
    public static void main(String[] arg) {
        String serString = "a9bc34281a9615ce035a0df957d135ec";
        serString = serString.substring(0, 16);
        jiemi test = new jiemi();
        serString = test.make_series(serString);
        System.out.println(serString);
    }


    public String make_series(String gs_hddseries) {
        String inputcode = gs_hddseries; //机器序列号
        String ls_returncode = "";
        inputcode = inputcode.substring(0, (int) (inputcode.length() / 2));
        for (int i = 1; i < (int) (inputcode.length() / 4); i++) {
            ls_returncode = ls_returncode + inputcode.substring((i - 1) * 4, (i - 1) * 4 + 4) + "-";
        }
        if (inputcode.substring(inputcode.length() - 1, inputcode.length()).equals("-")) {
            ls_returncode = ls_returncode.substring(0, ls_returncode.length() - 1);
        }
        System.out.print(ls_returncode);
        return ls_returncode;
    }
}
