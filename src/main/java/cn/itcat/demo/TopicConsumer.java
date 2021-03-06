package cn.itcat.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class TopicConsumer {
    public static void main(String[] args) throws JMSException, IOException {
        // 1.创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
        // 2.根据连接工厂创建连接
        Connection connection = connectionFactory.createConnection();
        // 3.启动连接
        connection.start();
        // 4.获取session（会话对象） 参数1：是否启动事务 参数2：消息确认方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.创建消息队列对象
        Topic topic = session.createTopic("test-topic");
        // 6.创建消息消费者对象
        MessageConsumer consumer = session.createConsumer(topic);
        // 7.设置监听
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("提取消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 8.等待键盘输入
        System.in.read();
        // 9.关闭资源
        consumer.close();
        session.close();
        connection.close();

    }
}
