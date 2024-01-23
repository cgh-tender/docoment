package cn.com.cgh.romantic.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDate;
import java.util.Objects;

public class DefaultDBFieldHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject)) {
            this.setFieldValByName("createTime", LocalDate.now(), metaObject);
            this.setFieldValByName("updateTime", LocalDate.now(), metaObject);

            //获取当前用户id
            if (Objects.nonNull(1L)) {
                this.setFieldValByName("createBy", 1L, metaObject);
                this.setFieldValByName("updateBy", 1L, metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject)) {
            this.setFieldValByName("updateTime", LocalDate.now(), metaObject);
            //获取当前用户id
            if (Objects.nonNull(2L)) {
                this.setFieldValByName("updateBy", 2L, metaObject);
            }
        }
    }
}
