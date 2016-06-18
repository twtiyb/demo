package html;


import java.awt.image.BufferedImage;

/**
 * 验证码识别
 *
 * @author yuezhen
 * @version $Id: DECheckCode.java,v 0.1 2010-7-13 下午03:06:20 yuezhen Exp $
 */
public class DECheckCode {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            BufferedImage image = Tools.getImage("checkCode/check" + i + ".bmp");
            System.out.print("check" + i + ".bmp == ");
            compare(image);
            System.out.println("");
        }
    }

    public static void compare(BufferedImage image) {
        BufferedImage checkCode[] = Tools.getCheckCodes(image);

        //4位数字比对
        for (int t = 0; t < 4; t++) {
            boolean ckFlg = true;
            int num = -1;
            //进入0-9图片的比对
            for (int i = 0; i < 10; i++) {
                //初始化标志位
                num = -1;
                ckFlg = true;
                BufferedImage testImage = Tools.getImage("check" + (t + 1) + "/" + i + ".bmp");

                if (testImage == null) {
                    continue;
                }

                //一个像素一个像素的比对
                for (int y = 0; y < checkCode[t].getHeight(); ++y) {
                    for (int x = 0; x < checkCode[t].getWidth(); ++x) {
                        int expRGB = Tools.pixelConvert(checkCode[t].getRGB(x, y));
                        int cmpRGB = Tools.pixelConvert(testImage.getRGB(x, y));
                        if (expRGB != cmpRGB) {
                            ckFlg = false;
                            break;
                        }
                    }
                }

                //如果所有像素点都一样，标明就这数字了
                if (ckFlg) {
                    num = i;
                    break;
                }
            }
            if (ckFlg) {
                System.out.print(num);
            } else {
                //如果没找到匹配的用X表示，并存储这张图片（供程序学习）
                System.out.print("x");
                Tools.writeImageToFile("E:/studyImg-" + t + ".bmp", checkCode[t]);
            }
        }
    }
}
