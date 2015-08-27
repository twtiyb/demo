package jms.mq.util;

import javax.jms.JMSException;

/**
 * Created by xuchun on 15/8/24.
 */
public class BrokeUtil {
     //家里虚拟机
   //  public static String brokeUrl = "failover:(tcp://192.168.1.28:61611,tcp://192.168.1.39:61611)";
     //公司217 非沙盒模式
     public static String brokeUrl = "failover:(tcp://192.168.6.217:61611,tcp://192.168.6.217:61612)";

     public static Consumer getConsummer(String clientId) throws JMSException {
          return new Consumer(clientId);
     }

     public static Publisher getPublisher(String clientId) throws JMSException {
          return new Publisher(clientId);
     }
}
