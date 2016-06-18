package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {
    /**
     * 获取当前目录下的所有文件，包括子目录的
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static List<File> getFile(File file) throws FileNotFoundException {
        List<File> fileList = new ArrayList<File>();
        if (file.isDirectory()) {
            for (String fileStr : file.list()) {
                fileList.addAll(getFile(new File(file.getPath() + "\\" + fileStr)));
            }
        } else {
            fileList.add(file);
        }
        return fileList;
    }
}
