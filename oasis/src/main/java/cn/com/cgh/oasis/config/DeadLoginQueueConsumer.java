package cn.com.cgh.oasis.config;

import cn.com.cgh.oasis.log.service.ITbLoginLogService;
import cn.com.cgh.oasis.util.IpSearcher;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.util.Application;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author cgh
 */
@Slf4j
public class DeadLoginQueueConsumer extends DefaultConsumer {
    private ITbLoginLogService ibLoginLogService;
    private IpSearcher ipSearcher;

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
        String jsonString = new String(body, StandardCharsets.UTF_8);
        try {
            if (ibLoginLogService == null) {
                this.ibLoginLogService = Application.getBean(ITbLoginLogService.class);
            }
            if (ipSearcher == null) {
                this.ipSearcher = Application.getBean(IpSearcher.class);
            }
            log.info(jsonString);
            TbLoginLog loginLog = JSONUtil.parse(jsonString).toBean(TbLoginLog.class);
            if (loginLog.getUserId() == null) {
                loginLog.setUserId(0L);
            }
            if (loginLog.getClientIp() != null) {
                loginLog.setIpDetail(ipSearcher.search(loginLog.getClientIp()));
            }
            ibLoginLogService.saveOrUpdate(loginLog);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            getChannel().basicNack(envelope.getDeliveryTag(), false, true);
        }
        getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

}
