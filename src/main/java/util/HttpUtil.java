package util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpUtil {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        HttpClient http = new HttpClient();
        GetMethod get = new GetMethod("http://www.dlog.cn/js/prototype.js");
        try {
            get.addRequestHeader("accept-encoding", "gzip,deflate");
            get.addRequestHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; Alexa Toolbar; Maxthon 2.0)");
            int er = http.executeMethod(get);
            if (er == 200) {
                System.out.println(get.getResponseContentLength());
                String html = get.getResponseBodyAsString();
                System.out.println(html);
                System.out.println(html.getBytes().length);
            }
        } finally {
            get.releaseConnection();
        }
    }
}
