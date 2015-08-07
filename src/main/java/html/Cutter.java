package html;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Cutter {
    public static void main(String[] args) {
        BufferedImage image = null;
        BufferedImage checkCode[] = new BufferedImage[4];
        try {
            image = ImageIO.read(new File("E:/check.bmp"));
        } catch (IOException e) {
            System.out.println("can't open checkCode");
        }

        checkCode = Tools.getCheckCodes(image);

        for (int i = 0; i < checkCode.length; i++) {
            try {
                ImageIO.write(checkCode[i], "BMP", new File("E:\\check" + (i + 1) + "\\test" + ".bmp"));
            } catch (IOException e) {
                System.out.println("can't open checkCode");
            }
        }
    }
}
