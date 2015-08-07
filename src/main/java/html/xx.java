package html;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

public class xx extends Thread {
    public void homePage() throws Exception {
        Date dateNow = new Date();
        WebClient webClient = new WebClient();
        HtmlPage page = webClient
                .getPage("http://jkyy2.aycgs.com:8080/Login.aspx");
        HtmlInput userName = (HtmlInput) page.getElementById("txtSfzmhm");
        userName.setValueAttribute("410526199110280049");
        HtmlPasswordInput password = (HtmlPasswordInput) page.getElementById("txtUserPwd");
        password.setText("antong");

//		HtmlImage verifyimg = (HtmlImage) page.getElementById("verifyimg");
//		boolean result = true;
//		String value = "";
//		while (result) {
//			value = "";
//			File imgFile = new File("c:/1.jpg");
//			verifyimg.saveAs(imgFile);
//			BufferedImage bufImg = ImageIO.read(imgFile);
//			BufferedImage[] bufArry = Tools.getCheckCodes(bufImg);
//			File imgFileList = new File("c:/ss");
//			File[] list = imgFileList.listFiles();
//			
//
//			for (BufferedImage scrImg : bufArry) {
//				int i = 10;
//				double percent2 = 0;
//				for (File img : list) {
//					if (!img.getName().endsWith(".jpg"))
//						continue;
//					System.out.println(img.getName());
//					BufferedImage tarImg = ImageIO.read(img);
//					double percent = comparImg(scrImg, tarImg) ;
//					if(percent>percent2)  {
//						percent2=percent;
//						i=NumberUtils.toInt(img.getName().subSequence(0, 1)
//								.toString());
//					}
//				}
//				if (i != 10) {
//					value += i;
//					result = false;
//				} else {
//					result = true;
//					verifyimg.click();
//					break;
//				}
//			}
//		}
        String pageAsText = page.asText();
        System.out.print(pageAsText);
//		HtmlInput verifycode = (HtmlInput) page.getElementByName("verifycode");
//		verifycode.setValueAttribute(value);

        //??
        HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementById("btnLogin");
        page = submit.click();
        //??????
        HtmlAnchor y = (HtmlAnchor) page.getElementById("y");
        page = y.click();
        //?????
        pageAsText = page.asText();
        HtmlAnchor c1 = (HtmlAnchor) page.getElementById("rptSchool_lbtnPreas_0");
        HtmlPage page2 = c1.click();
        System.out.print(page2.asText());
        webClient.closeAllWindows();
//		System.out.print(pageAsText);
    }

    static final Integer MAX_DOWLAND_NUM = 100; // ??????
    static final String SCR_IMG_DIR = "c:/SCRIMG/"; // ??????
    static final String TAR_IMG_DIR = "c:/TARGET/"; // ????????
    static final String PAGE_URL = "http://www.sshcenter.info/site-sshcenter/signup.php"; // ??url
    static final String IMG_ID = "verifyimg"; // ?????ID

    public static double comparImg(BufferedImage scrImg, BufferedImage tarImg) {
        int totalValue = 0;
        int trueValue = 0;
        for (int y = 0; y < scrImg.getHeight(); y++) {
            for (int x = 0; x < scrImg.getWidth(); x++) {
                int expRGB = Tools.pixelConvert(scrImg.getRGB(x, y));
                int cmpRGB = Tools.pixelConvert(tarImg.getRGB(x, y));
//				if (expRGB != 16777215 || cmpRGB != 16777215) {
//					totalValue++;
//					if (expRGB == cmpRGB)
//						trueValue++;
//				}
                if (expRGB == cmpRGB) trueValue++;
            }
        }
        return trueValue * 1.0 / (scrImg.getHeight() * scrImg.getWidth());
    }

    public static void main(String arg[]) throws Exception {
        try {
            // saveImg(PAGE_URL,IMG_ID,SCR_IMG_DIR,MAX_DOWLAND_NUM.intValue());
//			processingImg(SCR_IMG_DIR, TAR_IMG_DIR);
            File file = new File(SCR_IMG_DIR + "0.jpg");
            // File file = new File(TAR_IMG_DIR+"251.jpg");
//			BufferedImage imge = ImageIO.read(file);
//			StringBuffer r = new StringBuffer();
//			StringBuffer g = new StringBuffer();
//			StringBuffer b = new StringBuffer();
//			for (int y = 0; y < imge.getHeight(); y++) {
//				for (int x = 0; x < imge.getWidth(); x++) {
//					r.append(" "
//							+ String.valueOf((imge.getRGB(x, y) >> 16) & 0xff));
//
//					g.append(" "
//							+ String.valueOf((imge.getRGB(x, y) >> 8) & 0xff));
//
//					b.append(" " + String.valueOf((imge.getRGB(x, y)) & 0xff));
//				}
//				r.append("\n");
//				g.append("\n");
//				b.append("\n");
//			}
//			System.out.println(r);
//			System.out.println(g);
//			System.out.println(b);
            xx x = new xx();
            x.homePage();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void coverTwo(BufferedImage imge) {
        int rgb = 0;
        StringBuffer buffer = new StringBuffer();
        for (int y = 0; y < imge.getHeight(); y++) {
            for (int x = 0; x < imge.getWidth(); x++) {
                rgb = imge.getRGB(x, y);
                int red = (rgb >> 16) & 0xff;

                int green = (rgb >> 8) & 0xff;

                int blue = (rgb) & 0xff;
                if (red < 125 || green < 125 || blue < 125) {
                    buffer.append(1);
                } else {
                    buffer.append(0);
                }
            }
            buffer.append("\n");
        }
        System.out.println(buffer);
    }

    public static void saveImg(String url, String imgId, String scrAdrees,
                               int maxDowland) throws Exception {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(url);
        HtmlImage verifyimg = (HtmlImage) page.getElementById(imgId);
        File imgFile = null;
        for (int i = 0; i < maxDowland; i++) {
            imgFile = new File(scrAdrees + String.valueOf(i) + ".jpg");
            System.out.print(String.valueOf(i) + ".jpg ???.");
            verifyimg.saveAs(imgFile);
            System.out.println(String.valueOf(i) + ".jpg ???.");
            verifyimg.click();
        }
    }

    public static void processingImg(String scrAdress, String targetAdress) {
        File scr = new File(scrAdress);
        File target = new File(targetAdress);
        BufferedImage bufImg = null;
        BufferedImage targetImg = null;
        File[] scrList = scr.listFiles();
        double percent = 0;
        double trueValue = 0;
        try {
            for (File scrImg : scrList) {
                if (!scrImg.getName().endsWith(".jpg"))
                    continue;
                bufImg = ImageIO.read(scrImg);
                System.out.print(bufImg.getWidth() / 4);
                System.out.print(bufImg.getHeight());

                bufImg = bufImg.getSubimage(0, 0, bufImg.getWidth() / 4, bufImg
                        .getHeight());
                coverTwo(bufImg);
                Filter.blackAndWhiteFilter(bufImg);
                coverTwo(bufImg);
                Filter.dotFilter(bufImg);
                File[] targetList = target.listFiles();
                coverTwo(bufImg);
                if (targetList.length == 0) {
                    bufImg.setRGB(3, 3, 3);
                    File file = new File(targetAdress + "null.jpg");
                    ImageIO.write(bufImg, "jpg", file);
                }
                for (File targetFile : targetList) {
                    trueValue = 0;
                    int totalValue = 0;
                    if (!targetFile.getName().endsWith(".jpg"))
                        continue;
                    targetImg = ImageIO.read(targetFile);
                    for (int y = 0; y < bufImg.getHeight(); y++) {
                        for (int x = 0; x < bufImg.getWidth(); x++) {
                            int expRGB = Tools
                                    .pixelConvert(bufImg.getRGB(x, y));
                            int cmpRGB = Tools.pixelConvert(targetImg.getRGB(x,
                                    y));
                            if (expRGB != 16777215 || cmpRGB != 16777215) {
                                totalValue++;
                                if (expRGB == cmpRGB)
                                    trueValue++;
                            }
                        }
                    }
                    percent = trueValue / totalValue;
                    if (0.1 < percent && percent < 0.9) {
                        // ImageIO.write(bufImg, "jpg", targetFile);
                        // }else{
                        while (targetFile.exists()) {
                            targetFile = new File(targetAdress
                                    + (int) (Math.random() * 10) + ".jpg");
                        }
                        ImageIO.write(bufImg, "jpg", targetFile);
                    } else if (percent > 0.9)
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("??????");
        }
    }
}
