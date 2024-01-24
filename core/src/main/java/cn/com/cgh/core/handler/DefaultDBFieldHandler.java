package cn.com.cgh.core.handler;

import cn.com.cgh.core.util.IdWork;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

@Slf4j
public class DefaultDBFieldHandler implements MetaObjectHandler {
    static {
        log.info("DefaultDBFieldHandler:已启动");
    }
    @Autowired
    private IdWork idWork;
    @Override
    public void insertFill(MetaObject metaObject) {
        // 当前登录用户信息
        if (Objects.nonNull(metaObject)) {
            this.setFieldValByName("id", idWork.nextId(), metaObject);
            this.setFieldValByName("createBy", 1L, metaObject);
            this.setFieldValByName("updateBy", 1L, metaObject);
            this.setFieldValByName("createTime", new Date(), metaObject);
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject)) {
            this.setFieldValByName("updateBy", 1L, metaObject);
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}
