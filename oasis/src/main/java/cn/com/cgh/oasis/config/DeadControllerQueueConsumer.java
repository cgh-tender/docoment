package cn.com.cgh.oasis.config;

import cn.com.cgh.oasis.log.service.ITbControllerLogService;
import cn.com.cgh.oasis.util.IpSearcher;
import cn.com.cgh.romantic.pojo.oasis.TbControllerLog;
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
public class DeadControllerQueueConsumer extends DefaultConsumer {
    private ITbControllerLogService ibControllerLogService;
    private IpSearcher ipSearcher;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public DeadControllerQueueConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        try {
            if (ibControllerLogService == null){
                this.ibControllerLogService = Application.getBean(ITbControllerLogService.class);
            }
            if (ipSearcher == null) {
                this.ipSearcher = Application.getBean(IpSearcher.class);
            }
            String jsonString = new String(body, StandardCharsets.UTF_8);
            log.info(jsonString);
            TbControllerLog controllerLog = JSONUtil.parse(jsonString).toBean(TbControllerLog.class);
            if (controllerLog.getClientIp() != null) {
                controllerLog.setIpDetail(ipSearcher.search(controllerLog.getClientIp()));
            }
            ibControllerLogService.saveOrUpdate(controllerLog);
        } catch (Exception e) {
            System.out.println("DeadControllerQueueConsumer handleDelivery error: " + e.getMessage());
            System.out.println("DeadControllerQueueConsumer handleDelivery error: " + body);
            getChannel().basicNack(envelope.getDeliveryTag(), false, true);
        }
        getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

}
