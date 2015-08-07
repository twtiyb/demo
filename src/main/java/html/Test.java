package html;


import java.awt.image.BufferedImage;

/**
 * @author yuezhen
 * @version $Id: Test.java,v 0.1 2010-8-18 下午04:00:38 yuezhen Exp $
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            BufferedImage image = Tools.getImage("csdn/" + i + ".bmp");

            Filter.blackAndWhiteFilter(image);
            Tools.writeImageToFile("csdn/" + i + "-a.bmp", image);

            Filter.dotFilter(image);
            Tools.writeImageToFile("csdn/" + i + "-b.bmp", image);
        }
    }
}
