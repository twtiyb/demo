package mq.util;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Publisher {

    protected static transient ConnectionFactory factory;
    protected transient Connection connection;
    protected transient Session session;
    protected transient MessageProducer producer;
    protected transient Destination destination;

    public Publisher() throws JMSException {
        factory = new ActiveMQConnectionFactory(BrokeUtil.brokeUrl);
        connection = factory.createConnection();
        try {
            connection.start();
        } catch (JMSException jmse) {
            connection.close();
            throw jmse;
        }
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);

    }

    public Publisher(String clientId) throws JMSException {
        factory = new ActiveMQConnectionFactory(BrokeUtil.brokeUrl);
        connection = factory.createConnection();
        connection.setClientID(clientId);
        try {
            connection.start();
        } catch (JMSException jmse) {
            connection.close();
            throw jmse;
        }
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);
    }


    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }


    public void sendTopicMessage(String TopicName, Object message) throws JMSException {
        if (this.destination == null) {
            this.destination = session.createTopic(TopicName);
        }

        producer.send(this.destination, (Message) message);
    }

    public void sendAueueMessage(String queueName, Object message) throws JMSException {
        if (this.destination == null) {
            this.destination = session.createQueue(queueName);
        }
        producer.send(destination, (Message) message);
    }
}
