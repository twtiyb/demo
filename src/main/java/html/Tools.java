package html;


import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * 图片读取、写入、切割工具类。
 *
 * @author yuezhen
 * @version $Id: Tools.java,v 0.1 2010-7-13 下午03:08:21 yuezhen Exp $
 */
public class Tools {
    /**
     * 将图片写入磁盘文件
     *
     * @param imgFile 文件路径
     * @param bi      BufferedImage 对象
     * @return 无
     */
    public static void writeImageToFile(String imgFile, BufferedImage bi) {
        // 写图片到磁盘上
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(imgFile
                .substring(imgFile.lastIndexOf('.') + 1));
        ImageWriter writer = (ImageWriter) writers.next();
        // 设置输出源
        File f = new File(imgFile);
        ImageOutputStream ios;

        try {
            ios = ImageIO.createImageOutputStream(f);
            writer.setOutput(ios);
            // 写入到磁盘
            writer.write(bi);
            ios.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * 将一个像素点转为白色或者黑色。
     *
     * @param pixel
     * @return 转换后的像素点（黑/白）
     */
    public static int pixelConvert(int pixel) {
        int result = 0;

        //获取R/G/B
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = (pixel) & 0xff;

        //默认黑色
        result = 0xff000000;

//        int tmp = r * r + g * g + b * b;
//        if (tmp > 3 * 128 * 128) {
//            //白色，全F
//            result += 0x00ffffff;
//        }
        if (r < 128 || g < 128 || b < 128) {
            result = 0xff000000;
        } else {
            result = 0x00ffffff;
        }
        return result;
    }


    /**
     * 获取图片
     *
     * @param path
     * @return
     */
    public static BufferedImage getImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
        }

        return image;
    }

    /**
     * 将一张图片按照规则切分为4张
     *
     * @param image
     * @return
     */
    public static BufferedImage[] getCheckCodes(BufferedImage image) {
        BufferedImage checkCode[] = new BufferedImage[4];
        int height = image.getHeight();
        int width = image.getWidth();
        checkCode[0] = image.getSubimage(0 * (width / checkCode.length), 0, width
                        / checkCode.length,
                height);
        checkCode[1] = image.getSubimage(1 * (width / checkCode.length) + 1, 0, width
                / checkCode.length
                , height);
        checkCode[2] = image.getSubimage(2 * (width / checkCode.length), 0, width
                        / checkCode.length,
                height);
        checkCode[3] = image.getSubimage(3 * (width / checkCode.length) - 2, 0, width
                        / checkCode.length,
                height);

        return checkCode;
    }
}
