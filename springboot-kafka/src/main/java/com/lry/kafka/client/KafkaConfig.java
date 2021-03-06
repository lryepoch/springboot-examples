package com.lry.kafka.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author lryepoch
 * @date 2020/11/24 19:55
 * @description TODO 第二种方法：基于客户端实现
 * <p>
 * 总结：
 * 1、Spring为bean提供了两种初始化bean的方式，实现InitializingBean接口，实现afterPropertiesSet方法，或者在配置文件中通过init-method指定，两种方式可以同时使用。
 * 2、实现InitializingBean接口是直接调用afterPropertiesSet方法，比通过反射调用init-method指定的方法效率要高一点，但是init-method方式消除了对spring的依赖。
 * 3、如果调用afterPropertiesSet方法时出错，则不调用init-method指定的方法。
 */
@Slf4j
@Configuration
public class KafkaConfig implements InitializingBean {

    @Value("${kafka.broker.list}")
    public String brokerList;

    public static final String topic = "TOPIC_LIN_LIANG";

    public final String groupId = "group.01";

    //集群认证文件
//    static {
//        System.setProperty("java.security.auth.login.config", "E:\\IdeaProjects\\springboot-kafka\\src\\main\\kerberos\\jaas.conf");
//        System.setProperty("java.security.krb5.conf", "E:\\IdeaProjects\\springboot-kafka\\src\\main\\kerberos\\krb5.conf");
//    }

    /**
     * 消费者配置
     */
    public Properties customerConfigs() {
        Properties props = new Properties();
        //kafka集群的地址和端口
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        //消费者组id
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        //自动位移提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        //自动位移提交间隔时间
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100);
        //消费组失效超时时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        //位移丢失和位移越界后的恢复起始位置
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        //key反序列化
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //value反序列化
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //ACK有三种：0；1；-1
        props.put(ProducerConfig.ACKS_CONFIG, "1");
//        props.put("security.protocol", "SASL_PLAINTEXT");
//        props.put("sasl.kerberos.service.name", "kafka");

        return props;
    }

    /**
     * 生产者配置
     */
    public Properties producerConfigs() {
        Properties props = new Properties();
        //kafka集群的地址和端口
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        //20M 消息缓存
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 20000000);
        //生产者空间不足时，send()被阻塞的时间，默认60s
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 6000);
        //生产者重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        //指定ProducerBatch（消息累加器中BufferPool中的）可复用大小
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //生产者会在ProducerBatch被填满或者等待超过LINGER_MS_CONFIG时发送
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        //key序列化
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        //value序列化
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        //发送producer的一个辨识id
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");

//        props.put("security.protocol", "SASL_PLAINTEXT");
//        props.put("sasl.kerberos.service.name", "kafka");

        return props;
    }

    @Bean
    public Producer<Integer, Object> getKafkaProducer() {
        //KafkaProducer是线程安全的，可以在多个线程中共享单个实例
        return new KafkaProducer<>(producerConfigs());
    }

    @Override
    public void afterPropertiesSet() {
        log.info("这里是KafkaConfig类……");
    }
}
