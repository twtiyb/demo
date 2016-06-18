package io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuchun on 15/8/14.
 */
public class HttpUtil {
    public static HttpURLConnection getConnection(URL url, String method, String contentType) throws Exception {
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html,image/jpeg");
        conn.setRequestProperty("User-Agent", "ISCS-NET");
        conn.setRequestProperty("Content-Type", contentType);
        return conn;
    }


    public static InputStream getUrlData(String httpUrl) throws  Exception {
        URL url = new URL(httpUrl);
//        HttpURLConnection conn = HttpUtil.getConnection(url,"GET","application/x-www-form-urlencoded;charset=GBK");
        HttpURLConnection conn = HttpUtil.getConnection(url,"GET","application/x-www-form-urlencoded;charset=GBK");
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw  new Exception(conn.getResponseMessage());
        } else {
            return conn.getInputStream();
        }
    }

    public static void main(String[] args) {
//        try {
//            InputStream inputStream = HttpUtil.getUrlData("http://www.52haigo.com/data/upload/waybill/359356017755-100x150.jpg");
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//        try {
//            InputStream inputStream = HttpUtil.getUrlData("http://img01.taobaocdn.com/tfscom/TB1fE46JXXXXXXXXpXXSutbFXXX");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            InputStream inputStream = HttpUtil.getUrlData("http://www.52haigo.com/gateway/index.php?act=waybill_print&op=getfile&keyword=359544604874");
            ByteArrayOutputStream fileContent = new ByteArrayOutputStream();
            int n = 0 ;
            byte[] buffer = new byte[3000];
            while (-1 != (n = inputStream.read(buffer))) {
                fileContent.write(buffer,0,n);
            }
            System.out.print(fileContent.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
