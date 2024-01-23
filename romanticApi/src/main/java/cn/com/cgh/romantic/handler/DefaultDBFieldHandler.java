package cn.com.cgh.romantic.handler;

import cn.com.cgh.core.util.IdWork;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
public class DefaultDBFieldHandler implements MetaObjectHandler {
    private final IdWork idWork;
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
