package tools.smsSent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author xuchun
 */
public class smsSent {
    private int totalSent;
    private int faildSent;
    public static final String mobileNo = "18057028202";
    public static final String signStr = "18588885933";

    public static void main(String[] args) {
        smsSent sms = new smsSent();
        sms.startSent("src/tools/smsSent/urls.txt");
    }

    /**
     * @param filePath
     */
    public void startSent(String filePath) {
        File file = new File(filePath);
        if (file == null || file.isDirectory()) {
            System.out.print("输入的地址是空，或者是文件夹!");
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            String message = "";
            while ((line = reader.readLine()) != null) {
                line = line.replace(this.signStr, this.mobileNo);
                this.totalSent++;
                message = this.sentSms(line);
                if (message == null || message.startsWith("发送失败")) {
                    this.faildSent++;
                }
                System.out.println("总发送数:" + this.totalSent + " 失败数:" + faildSent);
                System.out.println(message + " 发送地址:" + line);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
        }
    }

    /**
     * @param urlStr
     * @return
     */
    public String sentSms(String urlStr) {
        String result = "发送失败";
        URL url = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                System.out.println(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
            result = lines;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }
}
