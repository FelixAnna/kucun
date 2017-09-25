package com.xiangyong.manager.core.mq.alimq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.xiangyong.manager.core.mq.*;
import com.xiangyong.manager.core.mq.alimq.annotation.EnableAlimqListener;
import com.xiangyong.manager.core.mq.alimq.properties.AlimqListenerProperties;
import com.xiangyong.manager.core.serializer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConditionalOnBean(annotation = EnableAlimqListener.class)
@EnableConfigurationProperties(AlimqListenerProperties.class)
public class AliMessageListenerAutoConfiguration implements InitializingBean, DisposableBean, MessageListenerContainer {

    private final static Logger logger = LoggerFactory.getLogger(AliMessageListenerAutoConfiguration.class);

    @Autowired
    private AlimqListenerProperties properties;

    @Autowired
    private AliMessageListener aliMessageListener;

    private Consumer consumer;

    public void afterPropertiesSet() throws Exception {
        Properties mqProperties = new Properties();
        mqProperties.put("ConsumerId", properties.getConsumerId());
        mqProperties.put("AccessKey", properties.getAccessKeyId());
        mqProperties.put("SecretKey", properties.getAccessKeySecret());
        mqProperties.put("ConsumeThreadNums", properties.getConsumeThreads());

        Serializer<Message> serializer = SerializerFactory.getSerializer(properties.getSerializeType());
        aliMessageListener.setSerializer(serializer);

        this.consumer = ONSFactory.createConsumer(mqProperties);
        StringBuffer subExp = new StringBuffer();
        for (String channel : properties.getChannelIds()) {
            subExp.append(" || ").append(channel);
        }
        this.consumer.subscribe(properties.getTopic(), subExp.delete(0, 4).toString(), aliMessageListener);
        this.consumer.start();
        logger.info("alimq consumer started!");
    }

    public void start() {
        if (!consumer.isStarted()) {
            this.consumer.start();
        }
    }

    public void destroy() throws Exception {
        this.shutdown();
    }

    public void shutdown() {
        if (!consumer.isClosed()) {
            consumer.shutdown();
        }
    }

}
