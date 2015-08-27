package jms.mq.test.testMasterSavle;

import jms.mq.util.BrokeUtil;
import jms.mq.util.Consumer;

import javax.jms.JMSException;
import javax.jms.MessageListener;

/**
 * Created by xuchun on 15/8/24.
 */
public class MasterSavleConsumer {
    public static void main(String[] args) throws JMSException {
        Consumer consumer = BrokeUtil.getConsummer("xuchunCusumer");
        consumer.setTopicListener("xuchunTestLasting", new MessageListener() {
            @Override
            public void onMessage(javax.jms.Message message) {
                try {
                    System.out.println(message.getStringProperty("xuchun"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        },true);
    }
}
