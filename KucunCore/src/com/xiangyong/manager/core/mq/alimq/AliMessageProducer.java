package com.xiangyong.manager.core.mq.alimq;

import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.xiangyong.manager.core.mq.Message;
import com.xiangyong.manager.core.mq.MessageChannel;
import com.xiangyong.manager.core.mq.MessageProducer;
import com.xiangyong.manager.core.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliMessageProducer implements MessageProducer {

    private Producer producer;

    private Serializer<Object> serializer;

    private String topic;

    public final static long MSG_MAX_STORE_TIME = 39L * 24L * 60L * 60L * 1000L;

    private static final Logger logger = LoggerFactory.getLogger(AliMessageProducer.class);

    public AliMessageProducer(Producer producer, Serializer<Object> serializer, String topic) {
        this.producer = producer;
        this.serializer = serializer;
        this.topic = topic;
    }

    @Override
    public void send(Message message){
        com.aliyun.openservices.ons.api.Message onsMsg = createONSMessage(message);
        producer.send(onsMsg);
    }

    @Override
    public void sendOneway(Message message){
        com.aliyun.openservices.ons.api.Message onsMsg = createONSMessage(message);
        producer.sendOneway(onsMsg);
    }

    @Override
    public void sendAsync(Message message){
        com.aliyun.openservices.ons.api.Message onsMsg = createONSMessage(message);
        producer.sendAsync(onsMsg, new SendCallback() {

            public void onSuccess(SendResult sendResult) {
                logger.error("发送消息[{}] 成功",sendResult.getMessageId());
            }

            public void onException(OnExceptionContext context) {
                logger.error("发送消息[" + context.getMessageId() +"] 失败" , context.getException());
            }
        });
    }

    /**
     * 转化为ONS消息
     * @param message
     * @return
     */
    private com.aliyun.openservices.ons.api.Message createONSMessage(com.xiangyong.manager.core.mq.Message message){
        Long deliverTime = message.getDeliverTime();
        String channel = getMessageChannel(message.getClass());
        if(deliverTime != null){
            // 判断是否
            long currMillis = System.currentTimeMillis();
            if(deliverTime - currMillis > MSG_MAX_STORE_TIME){
                deliverTime = currMillis + MSG_MAX_STORE_TIME;
            }else{
                message.setDeliverTime(null); //设置为null,不跟随序列化
            }
        }
        String key = message.getKey();
        message.setKey(null); //设置为null,不跟随序列化
        com.aliyun.openservices.ons.api.Message onsMsg = new com.aliyun.openservices.ons.api.Message(topic, channel, key, serializer.serialize(message));
        if(deliverTime != null){
            onsMsg.setStartDeliverTime(deliverTime);
        }
        return onsMsg;
    }

    /**
     * 根据消息体类型获取消息频道
     * @param clazz 消息体类型
     * @return
     */
    private String getMessageChannel(Class<? extends com.xiangyong.manager.core.mq.Message> clazz){
        MessageChannel channel = clazz.getAnnotation(MessageChannel.class);
        return channel == null ? clazz.getName() : channel.value();
    }
}
