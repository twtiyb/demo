package mq.test.testNatural;

import mq.util.BrokeUtil;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xuchun on 15/8/25.
 */
public class testSpeedPublisher {

    static int size = 200000;
    static Session session;
    static MessageProducer producerTopic;
    static MessageProducer producerQueue;
    static Topic topic;
    static Queue queue;
    static Connection connection;
    static String str = "[{'flag':'1','value':'8854c92e92404b188e63c4031db0eac9','label':'交换机(虚机)'},{'flag':'1','value':'3f367296c2174b7981342dc6fcb39d64','label':'防火墙'},{'flag':'1','value':'8a3e05eeedf54f8cbed37c6fb38c6385','label':'负载均衡'},{'flag':'1','value':'4f0ebc601dfc40ed854e08953f0cdce8','label':'其他设备'},{'flag':'1','value':'6','label':'路由器'},{'flag':'1','value':'4','label':'交换机'},{'flag':'1','value':'b216ca1af7ec49e6965bac19aadf66da','label':'服务器'},{'flag':'1','value':'7','label':'安全设备'},{'flag':'1','value':'cd8b768a300a4ce4811f5deff91ef700','label':'DWDM\\SDH'},{'flag':'1','value':'5','label':'防火墙(模块)'},{'flag':'1','value':'01748963956649e589a11c644d6c09b5','label':'机箱'}]";

    public static void init_connection() throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BrokeUtil.brokeUrl);
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        topic = session.createTopic("xuchun.testTopicSpeed");
        producerTopic = session.createProducer(topic);
//        producerTopic.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producerTopic.setDeliveryMode(DeliveryMode.PERSISTENT);

        queue = session.createQueue("xuchun.testQueueSpeed");
        producerQueue = session.createProducer(queue);
//        producerTopic.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producerQueue.setDeliveryMode(DeliveryMode.PERSISTENT);
    }

    public static void sendTopicMessage(String msg) {
        TextMessage message;
        try {
            message = session.createTextMessage();
            message.setText(str);
            producerTopic.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void sendQueueMessage(String msg) {
        TextMessage message;
        try {
            message = session.createTextMessage();
            message.setText(str);
            producerQueue.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void close() throws Exception {
        connection.close();
    }

    public static void main(String[] arg) throws Exception {
//        testQueue();testQueue();testQueue();testQueue();testQueue();testQueue();testQueue();testQueue();
        testTopic();testTopic();testTopic();testTopic();testTopic();testTopic();testTopic();testTopic();
    }

    public static void testQueue() throws Exception {
        init_connection();

        long start = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(10);
        final CountDownLatch cdl = new CountDownLatch(size);
        for (int a = 0; a < size; a++) {
//            es.execute(new Runnable() {
//                @Override
//                public void run() {
                    sendQueueMessage(str);
                    cdl.countDown();
//                }
//            });
        }
        cdl.await();
        es.shutdown();
        long time = System.currentTimeMillis() - start;
        System.out.println("queue");
        System.out.println("插入" + size + "条JSON，共消耗：" + (double) time / 1000 + " s");
        System.out.println("平均：" + size / ((double) time / 1000) + " 条/秒");
        close();
    }

    public static void testTopic() throws Exception {
        init_connection();

        long start = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(10);
        final CountDownLatch cdl = new CountDownLatch(size);
        for (int a = 0; a < size; a++) {
            es.execute(new Runnable() {
                public void run() {
                    sendTopicMessage(str);
                    cdl.countDown();
                }
            });
        }
        cdl.await();
        es.shutdown();
        long time = System.currentTimeMillis() - start;
        System.out.println("topic");
        System.out.println("插入" + size + "条JSON，共消耗：" + (double) time / 1000 + " s");
        System.out.println("平均：" + size / ((double) time / 1000) + " 条/秒");
        close();
    }
}