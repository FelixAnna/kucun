package com.xiangyong.manager.core.mq.alimq;

import com.aliyun.openservices.ons.api.*;
import com.xiangyong.manager.core.mq.MessageProducer;
import com.xiangyong.manager.core.mq.alimq.annotation.EnableAlimqProducer;
import com.xiangyong.manager.core.mq.alimq.properties.AlimqProducerProperties;
import com.xiangyong.manager.core.serializer.Serializer;
import com.xiangyong.manager.core.serializer.SerializerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConditionalOnBean(annotation = EnableAlimqProducer.class)
@EnableConfigurationProperties(AlimqProducerProperties.class)
public class AliMessageProducerAutoConfiguration implements InitializingBean, DisposableBean {

    @Autowired
    private AlimqProducerProperties properties;

    private Producer producer;

    private static final Logger logger = LoggerFactory.getLogger(AliMessageProducerAutoConfiguration.class);

    @Bean
    public MessageProducer messageProducer() {
        Serializer<Object> serializer = SerializerFactory.getSerializer(properties.getSerializeType());
        return new AliMessageProducer(producer, serializer, properties.getTopic());
    }

    public void afterPropertiesSet() throws Exception {
        Properties mqProperties = new Properties();
        mqProperties.put("ProducerId", properties.getProducerId());
        mqProperties.put("AccessKey", properties.getAccessKeyId());
        mqProperties.put("SecretKey", properties.getAccessKeySecret());
        this.producer = ONSFactory.createProducer(mqProperties);
        this.start();
        logger.info("alimq producer started!");
    }

    public void start() {
        if (!producer.isStarted()) {
            this.producer.start();
        }
    }

    public void destroy() throws Exception {
        if (this.producer != null) {
            this.producer.shutdown();
        }
    }

    public void shutdown() {
        if (!producer.isClosed()) {
            this.producer.shutdown();
        }
    }

}
