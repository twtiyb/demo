package mq.test.testNatural;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import mq.util.BrokeUtil;
import mq.util.Consumer;

public class testSpeedConsumer {

	public static void main(String[] args) throws JMSException {
		Consumer consumer = BrokeUtil.getConsummer(null);
		consumer.setTopicListener("xuchun.testTopicSpeed",
				new MessageListener() {
					public void onMessage(Message message) {
						try {
							System.out.println(((TextMessage) message)
									.getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}, true);

		consumer.setQueueListener("xuchun.testQueueSpeed",
				new MessageListener() {
					public void onMessage(Message message) {
						try {
							System.out.println(((TextMessage) message)
									.getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				});
	}
}
