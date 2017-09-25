package com.xiangyong.manager.core.mq.alimq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.xiangyong.manager.core.mq.Message;
import com.xiangyong.manager.core.mq.MessageChannel;
import com.xiangyong.manager.core.mq.MessageListener;
import com.xiangyong.manager.core.mq.alimq.annotation.EnableAlimqListener;
import com.xiangyong.manager.core.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnBean(annotation = EnableAlimqListener.class)
public class AliMessageListener implements com.aliyun.openservices.ons.api.MessageListener,ApplicationContextAware {

    private Consumer consumer;

    private Serializer<Message> serializer;

    private Map<String, List<MessageListener<Message>>> listenerMap;

    private final static Logger logger = LoggerFactory.getLogger(AliMessageListener.class);

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (listenerMap == null) {
            listenerMap = new HashMap<>();
        }
        Map<String, MessageListener> map = applicationContext.getBeansOfType(MessageListener.class);
        for (MessageListener listener : map.values()) {
            ParameterizedType type = (ParameterizedType) listener.getClass().getGenericInterfaces()[0];
            Class<Message> clazz = (Class<Message>) type.getActualTypeArguments()[0];
            String channel = getMessageChannel(clazz);
            List<MessageListener<Message>> listeners = listenerMap.get(channel);
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            listeners.add(listener);
            listenerMap.put(channel, listeners);
        }
    }

    public Action consume(com.aliyun.openservices.ons.api.Message onsMsg, ConsumeContext context) {
        try {
            Message message = serializer.deserialize(onsMsg.getBody());
            message.setKey(onsMsg.getKey());
            for (MessageListener<Message> listener : listenerMap.get(onsMsg.getTag())) {
                listener.onMessage(message);
            }
            return Action.CommitMessage;
        } catch (Exception e) {
            if (e instanceof ClassCastException) {
                logger.error("消息类型不匹配：" + e.getMessage());
                return Action.CommitMessage;
            } else {
                logger.error("消息处理异常：", e);
                return Action.ReconsumeLater;
            }
        }
    }

    /**
     * 根据消息体类型获取消息频道
     *
     * @param clazz 消息体类型
     * @return
     */
    private String getMessageChannel(Class<? extends com.xiangyong.manager.core.mq.Message> clazz) {
        MessageChannel channel = clazz.getAnnotation(MessageChannel.class);
        return channel == null ? clazz.getName() : channel.value();
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Serializer<Message> getSerializer() {
        return serializer;
    }

    public void setSerializer(Serializer<Message> serializer) {
        this.serializer = serializer;
    }

    public Map<String, List<MessageListener<Message>>> getListenerMap() {
        return listenerMap;
    }

    public void setListenerMap(Map<String, List<MessageListener<Message>>> listenerMap) {
        this.listenerMap = listenerMap;
    }
}