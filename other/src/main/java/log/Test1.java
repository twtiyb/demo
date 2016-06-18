package log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author 崔素强
 * @version 1.0
 * @说明 日志打印测试
 */
public class Test1 {
    /**
     * 日志记录对象
     */
    static {
        PropertyConfigurator.configure("src/log/log4j.properties");
    }

    private static Log log1 = LogFactory.getLog(Test1.class);
    private static Log log2 = LogFactory.getLog(Test2.class);

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        log1.debug("Test1");
        log1.warn("Test1");
        log1.info("Test1");
        log1.error("Test1");
        log2.debug("Test1");
        log2.warn("Test1");
        log2.info("Test1");
        log2.error("Test1");
    }
}