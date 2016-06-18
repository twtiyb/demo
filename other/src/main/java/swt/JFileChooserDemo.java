package swt;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class JFileChooserDemo {
    public static void main(String ar[]) {
        JFileChooser file = new JFileChooser();
        file.showOpenDialog(null);
        File fl = file.getSelectedFile();
        byte b[] = new byte[(int) fl.length()];
        try {
            FileInputStream fileinput = new FileInputStream(fl);
            fileinput.read(b);
            String s = new String(b);
            System.out.print(s);
            file.showSaveDialog(null);
            File fout = file.getSelectedFile();
            FileOutputStream fileout = new FileOutputStream(fout);
            fileout.write(b);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}