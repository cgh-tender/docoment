package cn.com.cgh.romantic.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author cgh
 */
@Slf4j
public class DefaultDBFieldHandler implements MetaObjectHandler {
    static {
        log.info("DefaultDBFieldHandler:已启动");
    }
    @Override
    public void insertFill(MetaObject metaObject) {
        // 当前登录用户信息
        if (Objects.isNull(metaObject.getValue("updateBy"))) {
            this.setFieldValByName("updateBy", 1L, metaObject);
        }
        if (Objects.isNull(metaObject.getValue("createTime"))) {
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        if (Objects.isNull(metaObject.getValue("createBy"))) {
            this.setFieldValByName("createBy", 1L, metaObject);
        }
        if (Objects.isNull(metaObject.getValue("updateTime"))) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (Objects.isNull(metaObject.getValue("updateBy"))) {
            this.setFieldValByName("updateBy", 1L, metaObject);
        }
        if (Objects.isNull(metaObject.getValue("updateTime"))) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
