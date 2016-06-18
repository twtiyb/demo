package html;


import java.awt.image.BufferedImage;

/**
 * 针对图片操作的filter
 *
 * @author yuezhen
 * @version $Id: Filter.java,v 0.1 2010-8-18 下午05:59:52 yuezhen Exp $
 */
public class Filter {
    /**
     * 黑白过滤器
     *
     * @param image
     * @return
     */
    public static void blackAndWhiteFilter(BufferedImage image) {
        if (image == null) {
            return;
        }

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                image.setRGB(j, i, Tools.pixelConvert(image.getRGB(j, i)));
            }
        }
    }

    /**
     * 孤点过滤器
     *
     * @param image
     * @return
     */
    public static void dotFilter(BufferedImage image) {
        if (image == null) {
            return;
        }
        int z = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                z = 0;
                if (i > 0 && j > 0 && i < (image.getHeight() - 1) && j < (image.getWidth() - 1)) {
                    if (image.getRGB(j, i) == 0xff000000) {
//                        if (image.getRGB(j - 1, i) == 0xffffffff
//                            && image.getRGB(j - 1, i - 1) == 0xffffffff
//                            && image.getRGB(j, i - 1) == 0xffffffff
//                            && image.getRGB(j + 1, i) == 0xffffffff
//                            && image.getRGB(j + 1, i + 1) == 0xffffffff
//                            && image.getRGB(j, i + 1) == 0xffffffff) {
//                            image.setRGB(j, i, 0xffffffff);
//                        }
                        if (image.getRGB(j - 1, i) == 0xff000000) z++;
                        if (image.getRGB(j - 1, i - 1) == 0xff000000) z++;
                        if (image.getRGB(j, i - 1) == 0xff000000) z++;
                        if (image.getRGB(j + 1, i) == 0xff000000) z++;
                        if (image.getRGB(j + 1, i + 1) == 0xff000000) z++;
                        if (image.getRGB(j - 1, i + 1) == 0xff000000) z++;
                        if (image.getRGB(j + 1, i - 1) == 0xff000000) z++;
                        if (z < 2) {
                            image.setRGB(j, i, 0xffffffff);
                        }
                    }
                }
                if (i == 0 || j == 0) {
                    image.setRGB(j, i, 0xffffffff);
                }
            }
        }
    }
}
