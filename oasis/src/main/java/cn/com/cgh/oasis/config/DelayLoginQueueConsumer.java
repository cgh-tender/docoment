package cn.com.cgh.oasis.config;

import cn.com.cgh.oasis.log.service.ITbLoginLogService;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.util.Application;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author cgh
 */
@Slf4j
public class DelayLoginQueueConsumer extends DefaultConsumer {
    private ITbLoginLogService iLogService;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public DelayLoginQueueConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        try {
            if (iLogService == null){
                this.iLogService = Application.getBean(ITbLoginLogService.class);
            }
            String jsonString = new String(body, StandardCharsets.UTF_8);
            log.info("", jsonString);
            TbLoginLog loginLog = JSONUtil.parse(jsonString).toBean(TbLoginLog.class);
            iLogService.save(loginLog);
        } catch (Exception e) {
            e.fillInStackTrace();
            // 注意此处获取的源队列的channel
            getChannel().basicNack(envelope.getDeliveryTag(), false, true);
        }
        // 注意此处获取的源队列的channel
        getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

}
