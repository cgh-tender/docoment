package cn.com.cgh.romantic.handler;

import cn.com.cgh.romantic.util.IdWork;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

/**
 * @author cgh
 */
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
        if (Objects.isNull(metaObject.getValue("updateBy"))) {
            this.setFieldValByName("updateBy", 1L, metaObject);
        }
        if (Objects.isNull(metaObject.getValue("createTime"))) {
            this.setFieldValByName("createTime", new Date(), metaObject);
        }
        if (Objects.isNull(metaObject.getValue("createBy"))) {
            this.setFieldValByName("createBy", 1L, metaObject);
        }
        if (Objects.isNull(metaObject.getValue("updateTime"))) {
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (Objects.isNull(metaObject.getValue("updateBy"))) {
            this.setFieldValByName("updateBy", 1L, metaObject);
        }
        if (Objects.isNull(metaObject.getValue("updateTime"))) {
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}
