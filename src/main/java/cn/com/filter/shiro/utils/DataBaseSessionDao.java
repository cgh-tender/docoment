package cn.com.filter.shiro.utils;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class DataBaseSessionDao extends EnterpriseCacheSessionDAO {
    private Session session;
    public DataBaseSessionDao() {
        super();
    }

    @Override
    protected Serializable doCreate(Session session) {
        return super.doCreate(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return super.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
