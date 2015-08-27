package jms.mq.test.testMasterSavle;

import jms.mq.util.BrokeUtil;
import jms.mq.util.Publisher;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQMessage;

import javax.jms.JMSException;
import java.util.Date;

/**
 * Created by xuchun on 15/8/24.
 */
public class MasterSavlePublisher {
    public static void main(String[] args) throws JMSException, InterruptedException {
        Publisher publisher = BrokeUtil.getPublisher("xuchunPublisher");
        Message message = new ActiveMQMessage();
        int i = 0 ;
        while (true) {
            message.setStringProperty("xuchun",new Date() + " xuchun" + i);
            publisher.sendTopicMessage("xuchunTestLasting", message);
            System.out.println(new Date() + " xuchun"+i);
            i++;
            Thread.currentThread().sleep(1000);
        }
    }
}
