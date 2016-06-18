package parser.htmlParser;

import util.IOUtil;

import java.io.File;
import java.util.List;

public class sourceHtml {
    public static void main(String[] arg) {
        String fileStr = "C:\\Documents and Settings\\Administrator\\My Documents\\Downloads\\源代码";
        sourceHtml ss = new sourceHtml();
        try {
            List<File> fileList = IOUtil.getFile(new File(fileStr));
            for (File file : fileList) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
