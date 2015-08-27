package jms.mq.util;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {

    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;

    public Consumer() throws JMSException {
        factory = new ActiveMQConnectionFactory(BrokeUtil.brokeUrl);
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public Consumer(String clientId) throws JMSException {
        factory = new ActiveMQConnectionFactory(BrokeUtil.brokeUrl);
        connection = factory.createConnection();
        if(clientId != null && !"".equals(clientId)) {
            connection.setClientID(clientId);
        }
        connection.start();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }


    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }


    public Session getSession() {
        return session;
    }


    public void setTopicListener(String topicName ,MessageListener listener,boolean Durable) throws JMSException {
        Topic topic = this.getSession().createTopic(topicName);
        MessageConsumer messageConsumer = null;
        if (Durable){
            //持久订阅模式     必须要设置clientId,以便让broken知道你是谁。
            messageConsumer = this.getSession().createDurableSubscriber(topic, this.connection.getClientID());
        } else {
            messageConsumer = this.getSession().createConsumer(topic);
        }
        messageConsumer.setMessageListener(listener);
    }

    public void setQueueListener(String MessageListener ,MessageListener listener) throws JMSException {
        Destination  destination = this.getSession().createQueue(MessageListener);
        MessageConsumer messageConsumer = this.getSession().createConsumer(destination);
        messageConsumer.setMessageListener(listener);
    }


}