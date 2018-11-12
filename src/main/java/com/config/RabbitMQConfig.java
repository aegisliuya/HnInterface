package com.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by zms on 2016/7/11.
 */
@Configuration
@ImportResource("classpath:properties-config.xml")
//@ComponentScan(value = "com.engine.logic.component.listener")
@EnableRabbit
public class RabbitMQConfig {

    /**
     * RabbitMQ集群环境地址
     */
    @Value("${addresses}")
    private String addresses;

//    @Value("${mq_host}")
//    private String host;

    /**
     * RabbitMQ端口号
     */
//    @Value("${mq_port}")
//    private int port;
    /**
     * RabbitMQ用户名
     */
    @Value("${mq_username}")
    private String username;
    /**
     * RabbitMQ密码
     */
    @Value("${mq_password}")
    private String password;
    /**
     * RabbitMQ虚拟主机名称
     */
    @Value("${mq_virtualHost}")
    private String virtualHost;

    @Bean
    public CachingConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        //断开重连
        factory.getRabbitConnectionFactory().setAutomaticRecoveryEnabled(true);
        //每1秒重试一次
//        factory.getRabbitConnectionFactory().setNetworkRecoveryInterval(1);
        factory.setAddresses(addresses);
//        factory.setHost(host);
//        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setPublisherConfirms(true);
        factory.setPublisherReturns(true);
        factory.setConnectionTimeout(20000);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() throws Exception {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate template() throws Exception {
        RabbitTemplate template = new RabbitTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setExchange("a");
        template.setEncoding("UTF-8");
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(RabbitMQConfig.class);
//        RabbitTemplate template = context.getBean(RabbitTemplate.class);
//        template.setMandatory(true);
//        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                if (ack) {
//                    System.out.println("确认成功");
//                } else {
//                    //处理丢失的消息（nack）
//                    System.out.println("确认失败");
//                }
//            }
//        });
//        template.setQueue("ah_jcy_total_index");
//        template.setRoutingKey("ah_jcy_total_index");
//        City o = new City();
//        o.setId((long)10000000);
//        o.setName("TestZMS");
//        while (true){
//            System.out.println("发送数据");
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                template.convertAndSend(mapper.writeValueAsString(o));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
