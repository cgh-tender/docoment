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
public class DeadLoginQueueConsumer extends DefaultConsumer {
    private ITbLoginLogService ibLoginLogService;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public DeadLoginQueueConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        try {
            if (ibLoginLogService == null){
                this.ibLoginLogService = Application.getBean(ITbLoginLogService.class);
            }
            String jsonString = new String(body, StandardCharsets.UTF_8);
            log.info("", jsonString);
            TbLoginLog loginLog =  JSONUtil.parse(jsonString).toBean(TbLoginLog.class);
            ibLoginLogService.save(loginLog);
        } catch (Exception e) {
            System.out.println("DeadLoginQueueConsumer handleDelivery error: " + e.getMessage());
            System.out.println("DeadLoginQueueConsumer handleDelivery error: " + body);
            getChannel().basicNack(envelope.getDeliveryTag(), false, true);
        }
        getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

}
