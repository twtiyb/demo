package multiMedia.JavaSound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaSound {
    public static void main(String arg[]) throws IOException {
        File fileFile = new File("c:\\jp.mid");
        try {
            FileInputStream file = new FileInputStream(fileFile);
//			PlayerContext context = new PlayerContext().getInstance();
//			context.setFile(file);
            UI ui = new UI();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
