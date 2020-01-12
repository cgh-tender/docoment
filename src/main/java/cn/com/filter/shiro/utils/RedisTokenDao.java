package cn.com.filter.shiro.utils;

import cn.com.SpringContextUtil;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Log4j
public class RedisTokenDao extends RedisSessionDao {
    private String token;
    public RedisTokenDao() {
        super();
    }

    @Override
    protected Serializable doCreate(Session session) {
        return super.doCreate(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.info("RedisTokenDao doReadSession");
        return super.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        log.info("RedisTokenDao doUpdate");
    }

    @Override
    protected void doDelete(Session session) {
        log.info("RedisTokenDao doDelete");
        super.doDelete(session);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
