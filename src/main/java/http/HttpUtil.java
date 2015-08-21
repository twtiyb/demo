package http;

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
        try {
            InputStream inputStream = HttpUtil.getUrlData("http://www.52haigo.com/data/upload/waybill/359356017755-100x150.jpg");
        } catch (Exception e) {
            e.printStackTrace();

        }
        try {
            InputStream inputStream = HttpUtil.getUrlData("http://img01.taobaocdn.com/tfscom/TB1fE46JXXXXXXXXpXXSutbFXXX");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
