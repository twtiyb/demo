package log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;

/**
 * @author 崔素强
 * @version 1.0
 * @说明 日志打印测试
 */
public class Test2 {
    /**
     * 日志记录对象
     */
    static {
        PropertyConfigurator.configure("src/log/log4j.properties");
    }

    private static Log log = LogFactory.getLog(Test2.class);

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        log.debug("Test2");
        log.warn("Test2");
        log.info("Test2");
        log.error("Test2");
    }
}