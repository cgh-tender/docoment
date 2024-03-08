package cn.com.cgh.auth.config;

import cn.com.cgh.romantic.em.LoginStatus;
import cn.com.cgh.romantic.interfac.RedisMessageAdvice;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.util.IdWork;
import cn.com.cgh.romantic.util.SendQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.Topic;

import java.util.Date;

/**
 * @author cgh
 */
@Configuration
@Slf4j
public class LogOutMessage implements RedisMessageAdvice {
    @Autowired
    private SendQueue sendQueue;
    @Autowired
    private IdWork idWork;

    @Override
    public Topic getTopic() {
        return new PatternTopic("__keyevent@0__:expired");
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody());
        if (key.startsWith("jwt:")) {
            String[] users = key.split(":");
            Long tokenId = Long.valueOf(users[2]);
            TbLoginLog logOut = TbLoginLog.builder()
                    .loginStatus(LoginStatus.LOGOUT)
                    .logoutTime(new Date())
                    .build();
            logOut.setId(tokenId);
            sendQueue.doSendLoginQueue(
                    MsgPojo.builder()
                            .id(idWork.nextId())
                            .msg(logOut)
                            .build()
            );
            log.info("redis删除key:{}", new String(message.getBody()));
        }
    }
}
